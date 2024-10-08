package com.accenture.next.customerapi.resources.response;

import com.accenture.next.customerapi.util.JsonObjectDeserializer;
import com.accenture.next.customerapi.util.JsonObjectSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CustomerResponse {
  
  private Integer id;
  private String name;
  private String customerType;
  private String customerCode;
  private Boolean enabled;
  @JsonSerialize(using = JsonObjectSerializer.class)
  @JsonDeserialize(using = JsonObjectDeserializer.class)
  private Json products;
  private String createdBy;
  private String createdDate;
  private String modifiedBy;
  private String modifiedDate;

}
