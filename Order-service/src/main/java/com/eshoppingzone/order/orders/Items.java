package com.eshoppingzone.order.orders;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Items
{
	@NotEmpty
	private String productName;
	@NotEmpty
	@Min(0)
	private double price;
	@Min(1)
	private int quantity;
}
