package com.eshoppingzone;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.eshoppingzone.exception.ProductAlreadyExistsException;
import com.eshoppingzone.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.repository.ProductRepository;
import com.eshoppingzone.productservice.service.ProductService;
import com.eshoppingzone.productservice.service.ProductServiceImpl;
import com.eshoppingzone.productservice.service.SequenceGeneratorService;

@SpringBootTest
class ProductMicroserviceApplicationTests {

	  private ProductRepository productRepository;
	  private SequenceGeneratorService seqService;
	  
	  private ProductServiceImpl productServiceImpl;
	  
 @BeforeEach
 void setUp() {
	 productRepository=mock(ProductRepository.class);
	 seqService=new SequenceGeneratorService();
	 productServiceImpl=new ProductServiceImpl();
	 
 }
 
 @Test
	void ProductById() {
		Product product=new Product();
		product.setProductName("Slim fit");
		product.setCategory("Clothes");
		product.setProductType("Jeans");
 }
		
   
	
 
 
	@Test
	void addProduct() {
		Product product=new Product();
		product.setProductId(1);
		
	assertThrows(ProductAlreadyExistsException.class,()->productServiceImpl.addProduct(product));
		
	}
	
	@Test
	void listAllProducts() {
		when(productRepository.findAll()).thenReturn(List.of(new Product(), new Product()));
	}
	
}