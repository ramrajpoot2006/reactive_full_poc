package com.accenture.next.orderapi.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.accenture.basketapi.util.JsonObjectDeserializer;
import com.accenture.basketapi.util.JsonObjectSerializer;
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
	private String orderStatus;
	private LocalDateTime orderDate;
	private String resourceState;
	private String eventId;
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
