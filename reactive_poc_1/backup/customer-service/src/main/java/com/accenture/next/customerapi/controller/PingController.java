package com.accenture.next.customerapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class PingController {

  @GetMapping(value = "/ping", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Mono<String>> pingAction() {

    /** Hard-coded to not introduce a json library into the seed **/
    String jsonResponse = "{\n" +
        "    \"ping\": \"pong\"\n" +
        "}";

    Mono<String> output = Mono.just(jsonResponse);
    return new ResponseEntity<>(output, HttpStatus.OK);
  }
}
