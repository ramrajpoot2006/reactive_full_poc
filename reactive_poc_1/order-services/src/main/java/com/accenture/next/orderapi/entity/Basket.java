package com.accenture.next.orderapi.entity;


import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import com.accenture.basketapi.util.JsonObjectDeserializer;
import com.accenture.basketapi.util.JsonObjectSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("basket")
public class Basket {

	@Id
	@Column("basket_id")
	private UUID basketId;
	@Column("order_no")
	private String orderNo;
	@Column("order_date")
	private LocalDateTime orderDate;
	@Column("resource_state")
	private String resourceState;
	@Column("event_id")
	private String eventId;
	@Column("product_items")
	//private List<ProductItem> productItems;
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json productItem;
	@Column("locale")
	private String locale;
	@Column("customer_information")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json customerInformation;
	@Column("billing_address")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json billingAddress;
	@Column("payment_instrument")
	@JsonSerialize(using = JsonObjectSerializer.class)
	@JsonDeserialize(using = JsonObjectDeserializer.class)
	private Json paymentInstruments;
	public UUID getBasketId() {
		// TODO Auto-generated method stub
		return basketId;
	}
	

}
