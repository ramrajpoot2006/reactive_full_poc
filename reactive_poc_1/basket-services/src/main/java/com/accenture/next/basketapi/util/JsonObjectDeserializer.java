package com.accenture.next.basketapi.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import io.r2dbc.postgresql.codec.Json;

public class JsonObjectDeserializer extends JsonDeserializer<Json> {

  @Override
  public Json deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    var value = ctxt.readTree(p);
    return Json.of(value.toString());
  }

}
