package com.accenture.next.basketapi.converter;

import org.springframework.stereotype.Component;

import com.accenture.next.basketapi.basketapi.resources.request.BasketPatchRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.next.basketapi.basketapi.resources.request.BasketPostRequest;
import com.accenture.next.basketapi.client.request.OrderServiceRequest;
import com.accenture.next.basketapi.client.response.OrderServiceResponse;
import com.accenture.next.basketapi.entity.Basket;
import com.accenture.next.basketapi.entity.Order;
import com.accenture.next.basketapi.response.BasketResponse;
import com.accenture.next.basketapi.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class BasketConverter {
  
  public Mono<BasketResponse> buildBasketResponse(Basket basket) {
    return Mono.just(basket).doFirst(() -> log.info("Converting basket to Basket response"))
        .map(this::buildbasketResponse);
  }

  public Basket apply(BasketPostRequest basketPostRequest) {
    return Basket.builder()
        .orderNo(String.valueOf((int)Math.floor(Math.random() * (10000000 - 50 + 1) + 50)))
        .orderDate(basketPostRequest.getOrderDate())
        .resourceState(basketPostRequest.getResourceState())
        .productItem(basketPostRequest.getProductItem())
        .locale(basketPostRequest.getLocale())
        .customerInformation(basketPostRequest.getCustomerInformation())
        .billingAddress(basketPostRequest.getBillingAddress())
        .paymentInstruments(basketPostRequest.getPaymentInstruments()).build();
  }
  
  public Basket apply(BasketPatchRequest basketPatchRequest, Basket basket) {
    return Basket.builder()
        .basketId(basket.getBasketId())
        .orderDate(basketPatchRequest.getOrderDate() == null ? basket.getOrderDate() : basketPatchRequest.getOrderDate())
        .orderNo(basket.getOrderNo())
        .resourceState(basketPatchRequest.getResourceState() == null ? basket.getResourceState() : basketPatchRequest.getResourceState())
        .productItem(basketPatchRequest.getProductItem() == null ? basket.getProductItem() : basketPatchRequest.getProductItem())
        .locale(basketPatchRequest.getLocale() == null ? basket.getLocale() : basketPatchRequest.getLocale())
        .customerInformation(basketPatchRequest.getCustomerInformation() == null ? basket.getCustomerInformation() : basketPatchRequest.getCustomerInformation())
        .billingAddress(basketPatchRequest.getBillingAddress() == null ? basket.getBillingAddress() : basketPatchRequest.getBillingAddress())
        .paymentInstruments(basketPatchRequest.getPaymentInstruments() == null ? basket.getPaymentInstruments() : basketPatchRequest.getPaymentInstruments()).build();
  }

  public Order apply(Basket basket) {
    return Order.builder().basketId(basket.getBasketId()).orderStatus("order created").build();
  }
  
  public OrderServiceRequest apply(BasketPostOrderRequest basketOrderRequest, Basket basket) {
    return OrderServiceRequest.builder()
        .basketId(basket.getBasketId())
        .orderNumber(basketOrderRequest.getOrderNumber())
        .orderStatus("Processed")
        .build();
  }

  public BasketResponse buildbasketResponse(Basket basket) {
    return BasketResponse.builder()
        .basketId(basket.getBasketId())
        .orderNo(basket.getOrderNo())
        .orderDate(basket.getOrderDate())
        .resourceState(basket.getResourceState())
        .productItem(basket.getProductItem())
        .locale(basket.getLocale())
        .customerInformation(basket.getCustomerInformation())
        .billingAddress(basket.getBillingAddress())
        .paymentInstruments(basket.getPaymentInstruments())
        .build();
  }

  public Mono<BasketResponse> prepareBasket(Basket basket) {
    return Mono.just( BasketResponse.builder()
        .basketId(basket.getBasketId())
        .orderNo(basket.getOrderNo())
        .orderDate(basket.getOrderDate())
        .resourceState(basket.getResourceState())
        .productItem(basket.getProductItem())
        .locale(basket.getLocale())
        .customerInformation(basket.getCustomerInformation())
        .billingAddress(basket.getBillingAddress())
        .paymentInstruments(basket.getPaymentInstruments())
        .build());
  }
  
  public Mono<OrderResponse> prepareOrderResponse(OrderServiceResponse orderServiceResponse) {
    return Mono.just(OrderResponse.builder()
        .basketId(orderServiceResponse.getBasketId())
        .orderNumber(orderServiceResponse.getOrderNumber())
        .orderStatus(orderServiceResponse.getOrderStatus())
        .build());
  }
  
  public Mono<OrderResponse> prepareOrderResponse(Order order) {
    return Mono.just(OrderResponse.builder()
        .basketId(order.getBasketId())
        .orderStatus(order.getOrderStatus()).build());
  }

}
