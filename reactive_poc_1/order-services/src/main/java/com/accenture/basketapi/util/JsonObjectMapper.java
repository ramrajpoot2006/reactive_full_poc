package com.accenture.basketapi.util;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.accenture.next.orderapi.constant.ErrorConstants;
import com.accenture.next.orderapi.exception.BasketApiException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.r2dbc.postgresql.codec.Json;

@Component
public class JsonObjectMapper {

  private final ObjectMapper objectMapper;

  public JsonObjectMapper(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public Map<String, String> jsonStringToObject(Json json) {
    try {
      return objectMapper.readValue(json.asString(), Map.class);
    } catch (JsonProcessingException e) {
      throw new BasketApiException(e, ErrorConstants.INTERNAL_ERROR_CODE);
    }
  }

  public <T> Json toJson(T data) {
    try {
      return Json.of(objectMapper.writeValueAsString(data));
    } catch (JsonProcessingException e) {
      throw new BasketApiException(e, ErrorConstants.INTERNAL_ERROR_CODE);
    }
  }

}
