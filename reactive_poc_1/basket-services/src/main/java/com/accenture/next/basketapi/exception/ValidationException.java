package com.accenture.next.basketapi.exception;

import lombok.Getter;

@Getter
public class ValidationException extends BasketApiException {
  
  private static final long serialVersionUID = 1L;

  public ValidationException(Throwable cause, String messageCode, String... args) {
    super(cause, messageCode, args);
  }
}
