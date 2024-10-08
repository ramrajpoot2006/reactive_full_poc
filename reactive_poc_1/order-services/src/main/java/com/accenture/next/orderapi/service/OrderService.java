package com.accenture.next.orderapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.basketapi.resources.request.BasketPostOrderRequest;
import com.accenture.next.orderapi.converter.OrderConverter;
import com.accenture.next.orderapi.repository.OrderRepository;
import com.accenture.next.orderapi.response.OrderResponse;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class OrderService {

  @Autowired
  private OrderConverter orderConverter;
  @Autowired
  private OrderRepository orderRepository;

  /*
   * public OrderService(OrderRepository orderRepository, OrderConverter
   * orderConverter) { this.orderConverter = orderConverter; }
   */

  public Mono<OrderResponse> createOrder(BasketPostOrderRequest basketOrderRequest) {
    //orderRepository.getOrderByBasketId(basketOrderRequest.getBasketId())
    return orderRepository.save(orderConverter.apply(basketOrderRequest))
        .flatMap(orderConverter::prepareOrderResponse);
  }

}
