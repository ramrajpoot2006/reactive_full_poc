package com.accenture.next.orderapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.next.orderapi.response.OrderResponse;
import com.accenture.next.orderapi.service.OrderService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("order-service")
public class OrderController {

  @Autowired
  private OrderService orderService;

  @PostMapping(value = "/createOrder", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<OrderResponse>> createOrder(
      @Valid @RequestBody BasketPostOrderRequest basketOrderRequest) {
    return orderService.createOrder(basketOrderRequest)
        .doFirst(
            () -> log.info("Request received to create order {}", basketOrderRequest.getBasketId()))
        .map(orderResponse -> ResponseEntity.status(HttpStatus.CREATED).body(orderResponse))
        .doOnSuccess(id -> log.info("Order created for basketId successfully : {}",
            basketOrderRequest.getBasketId()))
        .doOnError(
            throwable -> log.error("Error occurred during order creation for basketId {} : {}",
                basketOrderRequest.getBasketId(), throwable.getMessage()));
  }

}
