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
@Table("payyment_instrument")
public class PaymentInstrument {
	@Id
	@Column("payment_instument_id")
	private String paymentInstrumentId;
	@Column("payment_method_id")
	private String paymentMethodId;
	@Column("payment_method_name")
	private String paymentMethodName;
	@Column("amount")
	private Double amount;
	@Column("payment_status")
	private String paymentStatus;
	@Column("payment_provider_authorizationcode")
	private String paymentProviderAuthorizationCode;
	@Column("payment_provider_Transaction_Id")
	private String paymentProviderTransactionId;

}
