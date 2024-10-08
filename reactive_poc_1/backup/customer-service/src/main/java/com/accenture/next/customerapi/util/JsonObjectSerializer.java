package com.accenture.next.customerapi.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import io.r2dbc.postgresql.codec.Json;

public class JsonObjectSerializer extends JsonSerializer<Json> {

  @Override
  public void serialize(Json value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    var text = value.asString();
    JsonFactory factory = new JsonFactory();
    JsonParser parser = factory.createParser(text);
    var node = gen.getCodec().readTree(parser);
    serializers.defaultSerializeValue(node, gen);
  }

}
