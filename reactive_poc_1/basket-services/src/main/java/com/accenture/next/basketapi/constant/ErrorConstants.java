package com.accenture.next.basketapi.constant;

public final class ErrorConstants {

  public static final String API_MESSAGE_KEY = "basket.api.code";
  public static final String DOT_STRING = ".";

  // Error codes
  public static final String BASKET_NOT_FOUND_CODE = "4041";
  public static final String INVALID_FIELD_CODE = "4221";
  public static final String REQUEST_BODY_MISSING_CODE = "4002";
  public static final String INVALID_JSON_CODE = "4003";
  public static final String INTERNAL_ERROR_CODE = "5000";
  
  public static final String REQUIRED_FIELD_CODE = "4001";
  
  public static final String REQUEST_BODY_MISSING = "body is missing";
  public static final String JSON_DECODING_ERROR = "JSON decoding error";


  private ErrorConstants() {
    throw new IllegalStateException("Class constants, should not be called.");
  }
}
