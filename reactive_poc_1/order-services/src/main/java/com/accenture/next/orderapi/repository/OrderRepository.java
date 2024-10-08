package com.accenture.next.orderapi.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.next.orderapi.entity.Order;

import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
  
  Mono<Order> getOrderByBasketId(UUID basketId);

}
