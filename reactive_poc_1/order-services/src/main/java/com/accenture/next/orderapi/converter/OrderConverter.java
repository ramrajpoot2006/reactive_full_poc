package com.accenture.next.orderapi.converter;

import org.springframework.stereotype.Component;

import com.accenture.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.basketapi.resources.request.BasketPostRequest;
import com.accenture.next.orderapi.entity.Basket;
import com.accenture.next.orderapi.entity.Order;
import com.accenture.next.orderapi.response.BasketResponse;
import com.accenture.next.orderapi.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class OrderConverter {
  
  public Mono<BasketResponse> buildBasketResponse(Basket basket) {
    return Mono.just(basket).doFirst(() -> log.info("Converting basket to Basket response"))
        .map(this::buildbasketResponse);
  }

  public Basket apply(BasketPostRequest basketPostRequest) {
    return Basket.builder()
        .orderNo(basketPostRequest.getOrderNo())
        .orderDate(basketPostRequest.getOrderDate())
        .resourceState(basketPostRequest.getResourceState())
        .eventId(basketPostRequest.getEventId())
        .productItem(basketPostRequest.getProductItem())
        .locale(basketPostRequest.getLocale())
        .customerInformation(basketPostRequest.getCustomerInformation())
        .billingAddress(basketPostRequest.getBillingAddress())
        .paymentInstruments(basketPostRequest.getPaymentInstruments()).build();
  }

  public Order apply(Basket basket) {
    return Order.builder().basketId(basket.getBasketId()).orderStatus("order created").build();
  }
  
  public Order apply(BasketPostOrderRequest basketOrderRequest) {
    return Order.builder()
        .basketId(basketOrderRequest.getBasketId())
        .orderNumber(basketOrderRequest.getOrderNumber())
        .orderStatus(basketOrderRequest.getOrderStatus())
        .build();
  }

  public BasketResponse buildbasketResponse(Basket basket) {
    return BasketResponse.builder().basketId(basket.getBasketId()).orderNo(basket.getOrderNo())
        .orderDate(basket.getOrderDate()).resourceState(basket.getResourceState())
        .eventId(basket.getEventId()).productItem(basket.getProductItem())
        .locale(basket.getLocale()).customerInformation(basket.getCustomerInformation())
        .billingAddress(basket.getBillingAddress())
        .paymentInstruments(basket.getPaymentInstruments()).build();
  }

  public Mono<BasketResponse> prepareBasket(Basket basket, Order order) {
    return Mono.just( BasketResponse.builder()
        .basketId(basket.getBasketId())
        .orderNo(basket.getOrderNo())
        .orderStatus(order.getOrderStatus())
        .orderDate(basket.getOrderDate())
        .resourceState(basket.getResourceState())
        .eventId(basket.getEventId())
        .productItem(basket.getProductItem())
        .locale(basket.getLocale())
        .customerInformation(basket.getCustomerInformation())
        .billingAddress(basket.getBillingAddress())
        .paymentInstruments(basket.getPaymentInstruments())
        .build());
  }
  
  public Mono<OrderResponse> prepareOrderResponse(Order order) {
    return Mono.just(OrderResponse.builder()
        .basketId(order.getBasketId())
        .orderNumber(order.getOrderNumber())
        .orderStatus(order.getOrderStatus()).build());
  }

}
