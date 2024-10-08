package com.accenture.next.orderapi.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("billing_address")
public class BillingAddress {
	@Id
	@Column("billing_address_id")
	private UUID billingAddressId;
	@Column("street_address")
	private String streetAddress;
	@Column("city")
	private String city;
	@Column("state")
	private String state;
	@Column("zip_code")
	private int zipcode;
	

}
