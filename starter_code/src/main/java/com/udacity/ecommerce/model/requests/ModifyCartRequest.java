package com.udacity.ecommerce.model.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyCartRequest {

	@JsonProperty
	private long itemId;

	@JsonProperty
	private String username;

	@JsonProperty
	private int quantity;

}
