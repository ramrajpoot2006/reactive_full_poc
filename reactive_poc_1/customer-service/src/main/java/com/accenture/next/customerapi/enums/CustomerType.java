package com.accenture.next.customerapi.enums;

import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CustomerType {
  Premium("Premium"),
  Corporate("Corporate"),
  Gold("Gold");
  
  private String value;

  @JsonCreator
  public static CustomerType fromValue(String text) {
    return Stream.of(CustomerType.values())
        .filter(targetEnum -> targetEnum.value.equals(text))
        .findFirst()
        .orElse(null);
  }

  @JsonValue
  public static CustomerType fromName(String text) {
    return Stream.of(CustomerType.values())
        .filter(targetEnum -> targetEnum.name().equals(text))
        .findFirst()
        .orElse(null);
  }

  @JsonValue
  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value;
  }

}
