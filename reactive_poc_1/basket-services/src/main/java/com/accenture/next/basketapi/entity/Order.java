package com.accenture.next.basketapi.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("basket_order")
public class Order {
  
  @Id
  @Column("id")
  private int id;
  
  @Column("basket_id")
  private UUID basketId;
  
  @Column("order_status")
  private String orderStatus;

}
