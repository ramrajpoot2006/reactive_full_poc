package com.accenture.next.basketapi.response;

import java.util.UUID;

import com.accenture.next.basketapi.util.JsonObjectDeserializer;
import com.accenture.next.basketapi.util.JsonObjectSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BasketResponse {

	private UUID basketId;
	private String orderNo;
	private String orderDate;
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
