package com.accenture.next.basketapi.repository;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.accenture.next.basketapi.entity.Basket;

import reactor.core.publisher.Mono;

@Repository
public interface BasketReadRepository extends ReactiveCrudRepository<Basket, Integer> {

  Mono<Basket> findByBasketId(UUID basketId);
 

}