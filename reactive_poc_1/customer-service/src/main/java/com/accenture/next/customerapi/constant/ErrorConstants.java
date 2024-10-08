package com.accenture.next.customerapi.constant;

public final class ErrorConstants {

  public static final String API_MESSAGE_KEY = "customer.api.code";
  public static final String DOT_STRING = ".";

  // Error codes
  public static final String CUSTOMER_NOT_FOUND_CODE = "4041";
  public static final String INVALID_FIELD_CODE = "4221";
  public static final String INTERNAL_ERROR_CODE = "5000";


  private ErrorConstants() {
    throw new IllegalStateException("Class constants, should not be called.");
  }
}
