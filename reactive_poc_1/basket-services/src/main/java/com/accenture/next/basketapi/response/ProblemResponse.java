package com.accenture.next.basketapi.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ServerWebExchange;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import lombok.NonNull;

public final class ProblemResponse {

  private ProblemResponse() {
  }

  public static Problem buildProblem(ServerWebExchange request, StatusType status, String details) {
    return Problem.builder().withTitle(status.getReasonPhrase()).withStatus(status).withDetail(details).build();
  }

  public static ResponseEntity<Problem> internalServerError(ServerWebExchange request, String msg) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(buildProblem(request, Status.INTERNAL_SERVER_ERROR, msg));
  }

  public static ResponseEntity<Problem> badRequest(ServerWebExchange request, String msg) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(buildProblem(request, Status.BAD_REQUEST, msg));
  }

  public static ResponseEntity<Problem> unprocessableEntity(ServerWebExchange request, String msg) {
    return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(buildProblem(request, Status.UNPROCESSABLE_ENTITY, msg));
  }

  public static ResponseEntity<Problem> notFound(ServerWebExchange request, String msg) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(buildProblem(request, Status.NOT_FOUND, msg));
  }

  public static ResponseEntity<Problem> problemResponseEntity(@NonNull ServerWebExchange request,
      @NonNull StatusType status, @NonNull String msg) {
    return ResponseEntity.status(HttpStatus.valueOf(status.getStatusCode()))
        .contentType(MediaType.APPLICATION_PROBLEM_JSON).body(buildProblem(request, status, msg));
  }

  public static ResponseEntity<Problem> conflict(ServerWebExchange request, String msg) {
    return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_PROBLEM_JSON)
        .body(buildProblem(request, Status.CONFLICT, msg));
  }
}
