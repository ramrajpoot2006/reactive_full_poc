package com.accenture.next.customerapi.entity;

import java.time.LocalDateTime;

import com.accenture.next.customerapi.util.JsonObjectDeserializer;
import com.accenture.next.customerapi.util.JsonObjectSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
import lombok.Builder;
import lombok.Getter;

@Builder(toBuilder = true)
@Getter
@Table("customer_configuration")
public class Customer {

  @Id
  private Integer id;

  private String name;

  @Column("customer_type")
  private String customerType;

  @Column("customer_code")
  private String customerCode;

  private Boolean enabled;
  
  @JsonSerialize(using = JsonObjectSerializer.class)
  @JsonDeserialize(using = JsonObjectDeserializer.class)
  private Json products;

  @Column("created_by")
  private String createdBy;

  @Column("created_date")
  private LocalDateTime createdDate;

  @Column("modified_by")
  private String modifiedBy;

  @Column("modified_date")
  private LocalDateTime modifiedDate;

}
