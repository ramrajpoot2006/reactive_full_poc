package com.accenture.next.orderapi.constant;

public final class ErrorConstants {

  public static final String API_MESSAGE_KEY = "basket.api.code";
  public static final String DOT_STRING = ".";

  // Error codes
  public static final String CUSTOMER_NOT_FOUND_CODE = "4041";
  public static final String INVALID_FIELD_CODE = "4221";
  public static final String INTERNAL_ERROR_CODE = "5000";
  
  public static final String REQUIRED_FIELD_CODE = "4001";


  private ErrorConstants() {
    throw new IllegalStateException("Class constants, should not be called.");
  }
}
