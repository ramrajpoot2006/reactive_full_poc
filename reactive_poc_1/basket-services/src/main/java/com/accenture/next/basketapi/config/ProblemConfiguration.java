package com.accenture.next.basketapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebExceptionHandler;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.spring.webflux.advice.ProblemExceptionHandler;
import org.zalando.problem.spring.webflux.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ProblemConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .registerModules(new ProblemModule().withStackTraces(false),
            new ConstraintViolationProblemModule(),
            new JavaTimeModule());
  }

  @Bean
  @Order(-2)
  /* The handler must have precedence over WebFluxResponseStatusExceptionHandler
   and Spring Boot's ErrorWebExceptionHandler */
  public WebExceptionHandler problemExceptionHandler(ObjectMapper mapper,
                                                     ProblemHandling problemHandling) {
    return new ProblemExceptionHandler(mapper, problemHandling);
  }

}
