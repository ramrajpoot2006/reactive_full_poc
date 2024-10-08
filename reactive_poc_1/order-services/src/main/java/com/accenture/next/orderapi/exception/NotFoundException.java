package com.accenture.next.orderapi.exception;

import lombok.Getter;

@Getter
public class NotFoundException extends BasketApiException {

  public NotFoundException(Throwable cause, String messageCode, String... args) {
    super(cause, messageCode, args);
  }
}
