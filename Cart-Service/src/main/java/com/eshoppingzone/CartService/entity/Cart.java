package com.eshoppingzone.CartService.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Cart")
//store cart in db
public class Cart {
	
	
	@Id
	private int cartId;
	
	private double totalPrice;

	private List<Items> items;

	
}