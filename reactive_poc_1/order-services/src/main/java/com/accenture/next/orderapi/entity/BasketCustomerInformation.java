package com.accenture.next.orderapi.entity;

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


@Table("basket_customer_information")
public class BasketCustomerInformation {
	@Id
	@Column("customer_id")
	private String customerId;
	@Column("last_name")
	private String lastName;
	@Column("first_name")
	private String firstName;
	@Column("email")
	private String email;

	

}
