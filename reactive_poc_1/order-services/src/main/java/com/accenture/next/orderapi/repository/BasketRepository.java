package com.accenture.next.orderapi.repository;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.accenture.next.orderapi.entity.Basket;
import com.accenture.next.orderapi.entity.BasketEntity;

import reactor.core.publisher.Mono;

@Repository
@Transactional(readOnly = true)
public class BasketRepository {

  private final R2dbcEntityTemplate entityTemplate;

  public BasketRepository(R2dbcEntityTemplate entityTemplate) {
    this.entityTemplate = entityTemplate;
  }

  public Mono<Basket> findByBasketId(String basketId) {
    return entityTemplate.selectOne(Query.query(Criteria.where("basket_id").is(basketId)), BasketEntity.class)
        .map(BasketEntity::getBasket);
  }

 
}
