package com.accenture.next.basketapi.controller;

import static com.accenture.next.basketapi.constant.ErrorConstants.INVALID_FIELD_CODE;
import static com.accenture.next.basketapi.constant.ErrorConstants.REQUIRED_FIELD_CODE;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.next.basketapi.basketapi.resources.request.BasketPatchRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostRequest;
import com.accenture.next.basketapi.response.BasketResponse;
import com.accenture.next.basketapi.response.OrderResponse;
import com.accenture.next.basketapi.service.BasketService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("basket-service")
@Validated
public class BasketController {

  public static final String UUID_PATTERN = "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}";
  @Autowired
  private BasketService basketService;

  @GetMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BasketResponse>> getBasket(
      @PathVariable @NotBlank(message = REQUIRED_FIELD_CODE) 
      @Pattern(regexp = UUID_PATTERN, message = INVALID_FIELD_CODE) String basketId) {
    return basketService.getBasket(basketId)
        .doFirst(() -> log.info("Request received to get basket for id : {}", basketId))
        .map(basketResponse -> ResponseEntity.ok().body(basketResponse))
        .doOnSuccess(request -> log.info("Basket retrived successfully : {}",basketId))
         .doOnError(throwable -> log.error("Error in retriving basket : {}",basketId));
  }

  @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BasketResponse>> createBasket(
      @Valid @RequestBody BasketPostRequest basketPostRequest) {
    return basketService.createBasket(basketPostRequest)
        .doFirst(() -> log.info("Request received to create the basket"))
        .map(basketResponse -> ResponseEntity.status(HttpStatus.CREATED).body(basketResponse))
        .doOnSuccess(request -> log.info("Basket created successfully."))
        .doOnError(throwable -> log.error("Error in creating basket."));
  }
  
  @PatchMapping(value = "/{basketId}/orders", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<BasketResponse>> updateBasket(
      @PathVariable @NotBlank(message = REQUIRED_FIELD_CODE) 
      @Pattern(regexp = UUID_PATTERN, message = INVALID_FIELD_CODE) String basketId,
      @Valid @RequestBody BasketPatchRequest basketPatchRequest) {
    return basketService.updateBasket(basketPatchRequest, basketId)
        .doFirst(() -> log.info("Request received to update the basket {}", basketId))
        .map(orderResponse -> ResponseEntity.status(HttpStatus.CREATED).body(orderResponse))
        .doOnSuccess(request -> log.info("Basket updated successfully : {}", basketId))
        .doOnError(throwable -> log.error("Error in updating basket : {}", basketId));
  }

  @PostMapping(value = "/{basketId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Mono<ResponseEntity<OrderResponse>> createOrder(
      @PathVariable @NotBlank(message = REQUIRED_FIELD_CODE) 
      @Pattern(regexp = UUID_PATTERN, message = INVALID_FIELD_CODE) String basketId,
      @Valid @RequestBody BasketPostOrderRequest basketOrderRequest) {
    return basketService.createOrder(basketOrderRequest, UUID.fromString(basketId))
        .doFirst(() -> log.info("Request received to create order for basketId {}", basketId))
        .map(orderResponse -> ResponseEntity.status(HttpStatus.CREATED).body(orderResponse))
        .doOnSuccess(request -> log.info("Order created successfully : {}", basketId))
        .doOnError(throwable -> log.error("Error in creating order : {}", basketId));
  }

}
