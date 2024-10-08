package com.accenture.next.customerapi.exception;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ServerWebExchange;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.violations.Violation;

import com.accenture.next.customerapi.constant.ErrorConstants;
import com.accenture.next.customerapi.resources.response.ProblemResponse;
import com.accenture.next.customerapi.util.MessageHelper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
/**
 * https://www.baeldung.com/problem-spring-web
 * https://stackoverflow.com/questions/21262378/spring-boot-web-set-fail-on-unknown-properties-to-false-in-jackson
 * @author ram.prakash.singh
 *
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler implements ProblemHandling {
  private final MessageHelper messageHelper;

  public CustomExceptionHandler(MessageHelper messageHelper) {
    this.messageHelper = messageHelper;
  }

  @ExceptionHandler(Exception.class)
  public Mono<ResponseEntity<Problem>> handleCustomException(Exception e, final ServerWebExchange request) {
    log.error("{} status code {} ", e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    String errDetails = messageHelper.buildMessage(ErrorConstants.INTERNAL_ERROR_CODE);
    return Mono.just(ProblemResponse.internalServerError(request, errDetails));
  }

  @Override
  public Mono<ResponseEntity<Problem>> newConstraintViolationProblem(final Throwable throwable,
      final Collection<Violation> stream, final ServerWebExchange request) {
    log.error("{} status code {}", throwable.getMessage(), HttpStatus.BAD_REQUEST);
    List<Violation> violations = stream.stream()
        .sorted(Comparator.comparing(Violation::getField).thenComparing(Violation::getMessage))
        .collect(Collectors.toList());
        var violation = violations.stream().findFirst().orElse(null);
      return getProblemResponse(violation, request);
  }

  private Mono<ResponseEntity<Problem>> getProblemResponse(Violation violation, ServerWebExchange request) {
    String errDetails;
    String field = violation.getField();
    String errorCode = violation.getMessage();
    errDetails = messageHelper.buildMessage(errorCode, field);
      return Mono.just(ProblemResponse.unprocessableEntity(request, errDetails));
    }
  
  @ExceptionHandler(NotFoundException.class)
  public Mono<ResponseEntity<Problem>> handleNotFoundException(NotFoundException e, ServerWebExchange request) {
    String errDetails = messageHelper.buildMessage(e.getMessageCode(), e.getArgs());
    return Mono.just(ProblemResponse.notFound(request, errDetails));
  }
}
