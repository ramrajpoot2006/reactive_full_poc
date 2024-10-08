package com.accenture.next.basketapi.service;

import static com.accenture.next.basketapi.constant.ErrorConstants.BASKET_NOT_FOUND_CODE;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import com.accenture.next.basketapi.basketapi.resources.request.BasketPatchRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostRequest;
import com.accenture.next.basketapi.client.OrderServiceClient;
import com.accenture.next.basketapi.converter.BasketConverter;
import com.accenture.next.basketapi.exception.NotFoundException;
import com.accenture.next.basketapi.repository.BasketReadRepository;
import com.accenture.next.basketapi.response.BasketResponse;
import com.accenture.next.basketapi.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class BasketService {

  private BasketReadRepository basketRepository;
  private BasketConverter basketConverter;
  private OrderServiceClient orderServiceClient;

  public BasketService(BasketReadRepository basketRepository, BasketConverter basketConverter,
      OrderServiceClient orderServiceClient) {
    this.basketConverter = basketConverter;
    this.basketRepository = basketRepository;
    this.orderServiceClient = orderServiceClient;
  }

  public Mono<BasketResponse> getBasket(String basketId) {

    return basketRepository.findByBasketId(UUID.fromString(basketId))
        .doFirst(() -> log.info("Processing get basket basketid : {}", basketId))
        .switchIfEmpty(
            Mono.error(new NotFoundException(new HttpClientErrorException(HttpStatus.NOT_FOUND),
                BASKET_NOT_FOUND_CODE, basketId.toString())))
        .flatMap(basketConverter::buildBasketResponse);
  }

  public Mono<BasketResponse> createBasket(BasketPostRequest basketPostRequest) {

    return basketRepository.save(basketConverter.apply(basketPostRequest))
        .flatMap(basketConverter::prepareBasket);
  }

  public Mono<BasketResponse> updateBasket(BasketPatchRequest basketPatchRequest, String basketId) {

    return basketRepository.findByBasketId(UUID.fromString(basketId))
        .doFirst(() -> log.debug("Updating basket for basketId : {}", basketId))
        .switchIfEmpty(
            Mono.error(new NotFoundException(new HttpClientErrorException(HttpStatus.NOT_FOUND),
                BASKET_NOT_FOUND_CODE, basketId.toString())))
        .flatMap(
            basketRes -> basketRepository.save(basketConverter.apply(basketPatchRequest, basketRes))
                .flatMap(basketConverter::prepareBasket));
  }

  public Mono<OrderResponse> createOrder(BasketPostOrderRequest basketOrderRequest, UUID basketId) {

    return basketRepository.findByBasketId(basketId)
        .doFirst(() -> log.debug("Processing get order for basketId : {}", basketId))
        .switchIfEmpty(
            Mono.error(new NotFoundException(new HttpClientErrorException(HttpStatus.NOT_FOUND),
                BASKET_NOT_FOUND_CODE, basketId.toString())))
        .flatMap(basketRes -> orderServiceClient
            .createOrder(basketConverter.apply(basketOrderRequest, basketRes)))
        .flatMap(basketConverter::prepareOrderResponse);
  }

}
