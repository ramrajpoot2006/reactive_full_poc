
/* * package com.accenture.basketapi.resources.request;
 * 
 * import lombok.AllArgsConstructor; import lombok.Builder; import
 * lombok.EqualsAndHashCode; import lombok.Getter; import
 * lombok.NoArgsConstructor; import lombok.ToString;
 * 
 * 
 * import com.accenture.next.entity.ProductItem;
 * 
 * import java.util.List;
 * 
 * @Builder
 * 
 * @Getter
 * 
 * @ToString
 * 
 * @EqualsAndHashCode
 * 
 * @AllArgsConstructor
 * 
 * @NoArgsConstructor public class BasketPostRequest {
 * 
 * public static final String REQUIRED_FIELD_CODE = "4001";
 * 
 * @Valid
 * 
 * @NotEmpty(message = REQUIRED_FIELD_CODE) private List<@Valid ProductItem>
 * productItems;
 * 
 * @Valid
 * 
 * @NotNull(message = REQUIRED_FIELD_CODE) private
 * BasketPostRequestBasketMetaData basketMetaData;
 * 
 * }*/

package com.accenture.basketapi.resources.request;

import java.time.LocalDateTime;

import com.accenture.basketapi.util.JsonObjectSerializer;
import com.accenture.basketapi.util.JsonObjectDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.r2dbc.postgresql.codec.Json;
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
	
	private String orderNo;
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

 