package com.accenture.next.customerapi.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends ShippingApiException {

  public NotFoundException(Throwable cause, String messageCode, String... args) {
    super(cause, messageCode, args);
  }
}
