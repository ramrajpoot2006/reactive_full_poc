package com.accenture.next.basketapi.exception;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import org.zalando.problem.Problem;
import org.zalando.problem.StatusType;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.violations.Violation;

import com.accenture.next.basketapi.constant.ErrorConstants;
import com.accenture.next.basketapi.response.ProblemResponse;
import com.accenture.next.basketapi.util.MessageHelper;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler implements ProblemHandling {
  
  private final MessageHelper messageHelper;

  public CustomExceptionHandler(MessageHelper messageHelper) {
    this.messageHelper = messageHelper;
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<Problem>> handleCustomException(Exception e, final ServerWebExchange request) {
    log.error("{} status code {} ", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e);
    String errDetails = messageHelper.buildMessage(ErrorConstants.INTERNAL_ERROR_CODE);
    return Mono.just(ProblemResponse.internalServerError(request, errDetails));
  }

  @ExceptionHandler
  public Mono<ResponseEntity<Problem>> handleResponseStatusException(final ResponseStatusException exception, final ServerWebExchange request) {
    if (exception instanceof ServerWebInputException) {
      return this.create(HttpStatus.valueOf(exception.getStatusCode().value()), new ServerWebInputException("invalid json"), request);
    } else {
      return this.create(HttpStatus.valueOf(exception.getStatusCode().value()), exception, request);
    }
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public Mono<ResponseEntity<Problem>> handleValidationException(
      ConstraintViolationException e, final ServerWebExchange request) {
    log.warn("{} error status code {}", e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, e);
    String errDetails = e.getMessage();
    if (!e.getConstraintViolations().isEmpty()) {
      ConstraintViolation violation = e.getConstraintViolations().iterator().next();
      String message = violation.getMessage();
      String wrongValue = violation.getInvalidValue() != null ?
          violation.getInvalidValue().toString() : null;
      if (Objects.nonNull(message)) {
        String field = ((PathImpl)violation.getPropertyPath()).getLeafNode().toString();
        errDetails = messageHelper.buildMessage(message, field, wrongValue);
      } else {
        errDetails = messageHelper.buildMessage(message, wrongValue);
      }
    }
    return Mono.just(ProblemResponse.unprocessableEntity(request, errDetails));
  }

  @Override
  public Mono<ResponseEntity<Problem>> newConstraintViolationProblem(final Throwable throwable,
      final Collection<Violation> stream, final ServerWebExchange request) {
    log.error("{} error status code {}", throwable.getMessage(), HttpStatus.BAD_REQUEST, throwable);
    String errDetails;
    List<Violation> violations = stream.stream()
        .sorted(Comparator.comparing(Violation::getField).thenComparing(Violation::getMessage))
        .collect(Collectors.toList());
    var violation = violations.stream().findFirst().orElse(null);
    if (Objects.nonNull(violation)) {
      return getProblemResponse(violation, request);
    } else {
      errDetails = messageHelper.buildMessage(ErrorConstants.INTERNAL_ERROR_CODE);
      return Mono.just(ProblemResponse.internalServerError(request, errDetails));
    }
  }

  private Mono<ResponseEntity<Problem>> getProblemResponse(Violation violation, ServerWebExchange request) {
    String errDetails;
    String field = violation.getField();
    String errorCode = violation.getMessage();
    errDetails = messageHelper.buildMessage(errorCode, field);
    if (errorCode.equals(ErrorConstants.INVALID_FIELD_CODE)) {
      return Mono.just(ProblemResponse.unprocessableEntity(request, errDetails));
    } else {
      return Mono.just(ProblemResponse.badRequest(request, errDetails));
    }
  }

  @ExceptionHandler(NotFoundException.class)
  public Mono<ResponseEntity<Problem>> handleNotFoundException(NotFoundException e, ServerWebExchange request) {
    String errDetails = messageHelper.buildMessage(e.getMessageCode(), e.getArgs());
    return Mono.just(ProblemResponse.notFound(request, errDetails));
  }

  @Override
  public Mono<ResponseEntity<Problem>> create(final StatusType status, final Throwable throwable,
      final ServerWebExchange request, final HttpHeaders headers) {
    log.error(throwable.getMessage());
    String[] message = throwable.getMessage().split(":");
    String errDetails = throwable.getMessage();
    if (message.length > 1) {
      if (throwable.getMessage().contains(ErrorConstants.REQUEST_BODY_MISSING)) {
        errDetails = messageHelper.buildMessage(ErrorConstants.REQUEST_BODY_MISSING_CODE);
      } else if (throwable.getMessage().contains(ErrorConstants.JSON_DECODING_ERROR)) {
        errDetails = messageHelper.buildMessage(ErrorConstants.INVALID_JSON_CODE);
      }
    }
    return Mono.just(ProblemResponse.problemResponseEntity(request, status, errDetails));
  }

  @ExceptionHandler(ValidationException.class)
  public Mono<ResponseEntity<Problem>> handleValidationException(ValidationException e, ServerWebExchange request) {
    log.error("{} status error code {}", e.getMessage(), e.getMessageCode(), e);
    String errDetails = messageHelper.buildMessage(e.getMessageCode(), e.getArgs());
    return Mono.just(ProblemResponse.unprocessableEntity(request, errDetails));
  }
  
}
