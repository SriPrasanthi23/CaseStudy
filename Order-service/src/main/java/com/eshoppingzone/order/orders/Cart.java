package com.eshoppingzone.order.orders;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="cart")
public class Cart 
{

	
	@Transient
	public  static final String sequenceName="CartSequence";
@Id	
private int id;
private List<Items> items;
private double totalPrice;



 
}
