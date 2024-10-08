package com.accenture.next.basketapi.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class OrderResponse {

  private UUID basketId;
  private String orderNumber;
  private String orderStatus;

}
