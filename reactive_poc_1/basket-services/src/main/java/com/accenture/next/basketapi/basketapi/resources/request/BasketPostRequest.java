package com.accenture.next.basketapi.basketapi.resources.request;

import static com.accenture.next.basketapi.constant.ErrorConstants.REQUIRED_FIELD_CODE;

import com.accenture.next.basketapi.util.JsonObjectDeserializer;
import com.accenture.next.basketapi.util.JsonObjectSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Builder(toBuilder = true)
@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class BasketPostRequest {
  
  @NotBlank(message = REQUIRED_FIELD_CODE)
	private String orderDate;
	@NotBlank(message = REQUIRED_FIELD_CODE)
	private String resourceState;
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json productItem;
	private String locale;
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json customerInformation;
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json billingAddress;
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json paymentInstruments;

}

 