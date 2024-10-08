package com.accenture.next.basketapi.basketapi.resources.request;

import static com.accenture.next.basketapi.constant.ErrorConstants.REQUIRED_FIELD_CODE;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BasketPostOrderRequest {

  @NotBlank(message = REQUIRED_FIELD_CODE)
  private String orderNumber;

}
