package com.accenture.next.customerapi.exception;

import java.util.Objects;

/**
 * This exception class will be base class for all exception in ShippingService.
 * So it will ensure that all exception will have messageCode as well as message
 * args.
 */
public class ShippingApiException extends RuntimeException {
  private final String messageCode;
  private final String[] args;

  public ShippingApiException(Throwable cause, String messageCode, String... args) {
    super(cause);
    this.messageCode = messageCode;
    this.args = Objects.isNull(args) ? null : args.clone();
  }

  public String getMessageCode() {
    return this.messageCode;
  }

  public String[] getArgs() {
    return this.args.clone();
  }

}
