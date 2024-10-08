package com.accenture.next.basketapi.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.accenture.next.basketapi.client.request.OrderServiceRequest;
import com.accenture.next.basketapi.client.response.OrderServiceResponse;

import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class OrderServiceClient {

  @Value("${orderservice.uri}")
  private @NotNull String uri;

  @Autowired
  @Qualifier("orderservice")
  private WebClient webClient;

  public Mono<OrderServiceResponse> createOrder(OrderServiceRequest orderServiceRequest) {
    log.info("Calling Order Serive endpoint for basketId : "+orderServiceRequest.getBasketId().toString());
    return webClient.post()
        .uri(uri)
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(orderServiceRequest), OrderServiceRequest.class)
        .retrieve()
        .onStatus(HttpStatusCode::isError, response -> {
          log.error("Error from Order service {}", response.statusCode());
          return response.createException().flatMap(Mono::error);
        })
        .bodyToMono(OrderServiceResponse.class)
        .doOnError(throwable -> log.error("Error in getting Order service response : {}", throwable.getMessage()))
        .doOnSuccess(orderResponse -> log.info("Received Order service response : {}", orderResponse.getBasketId()));
  }

}
