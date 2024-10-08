package com.accenture.next.basketapi.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.next.basketapi.entity.Order;

import reactor.core.publisher.Mono;

@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, Integer> {
  
  Mono<Order> getOrderByBasketId(UUID basketId);

}
