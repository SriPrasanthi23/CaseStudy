package com.eshoppingzone.order.orders.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.eshoppingzone.order.orders.Address;
import com.eshoppingzone.order.orders.Cart;
import com.eshoppingzone.order.orders.Orders;
import com.razorpay.RazorpayException;

//interface for order service layer
@Service
public interface OrderService {
	
	List<Orders> getAllOrders();
	void placeOrder(Cart cart,String mode,String fullName);
	String changeStatus(String status ,int orderId);
	void deleteOrder(int orderId);
	List<Orders> getOrderByCustomerId(int customerId);
	void storeAddress(Address address);
	List<Address> getAddressByCustomerId(int customerId);
	List<Address> getAllAddress();
	Orders getOrderById(int orderId);
	String onlinePayment(Cart cart) throws RazorpayException;
	List<Orders> findOrderByFullName(String fullName);
	

}
