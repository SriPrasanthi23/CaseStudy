package com.eshoppingzone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.eshoppingzone.exception.CategoryNotFoundException;
import com.eshoppingzone.exception.ProductAlreadyExistsException;
import com.eshoppingzone.exception.ProductNotFoundException;
import com.eshoppingzone.exception.ProductTypeNotExistsException;
import com.eshoppingzone.productservice.entity.ErrorResponse;
import com.eshoppingzone.productservice.entity.LoggerResponse;
import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.repository.ProductRepository;
import com.eshoppingzone.productservice.resource.ProductResource;
import com.eshoppingzone.productservice.service.ProductService;

@SpringBootTest
public class ProductControllerTest {

	@Autowired
	private ProductService productService;
	
	@MockBean
	private ProductRepository productRepository;
	@MockBean
	ProductResource productResource;
	@Test
	public void getAllProductTest()
	{
		when(productRepository.findAll()).thenReturn(Stream.of(new Product(1,"Story","Matlida","Book","4.2","good","",800.00,"","")).collect(Collectors.toList()));
		assertEquals(1,productService.getAllProducts().size());
	}
	@Test
	public void addProductTest()
	{
		Product product=new Product(2,"Story","Forest","Book","4.5","good","",900.00,"","");
		when(productRepository.save(product)).thenReturn(product);
		assertEquals(product,productService.addProduct(product));
	}
   @Test
   public void addProductException()throws ProductAlreadyExistsException
   {
	   Product product=new Product(2,"Story","Forest","Book","4.5","good","",900.00,"","");
	   when(productRepository.save(product)).thenReturn(product);
	   if(productRepository.existsById(2))
	   {
		   throw new ProductAlreadyExistsException("Product already exists");
	   }
   }
	@Test
	public void updateProduct_test() {

		Product p3 = new Product();
		p3.setProductId(10);
		p3.setProductName("Hello");
		p3.setProductType("Cloth");
		p3.setCategory("Female");
		p3.setDescription("Good");
		p3.setImage("img1");
		p3.setPrice(120);
		p3.setRating("3.4");
		p3.setReview("good");
		p3.setSpecification("no");

		Product p4 = new Product();
		p4.setProductId(10);
		p4.setProductName("Hi");
		p4.setProductType("Clothes");
		p4.setCategory("Female");
		p4.setDescription("Good");
		p4.setImage("img3");
		p4.setPrice(1200);
		p4.setRating("4.4");
		p4.setReview("good");
		p4.setSpecification("yes");

		Optional<Product> update = Optional.of(p3);
		when(productRepository.findById(10)).thenReturn(update);
		when(productRepository.save(p4)).thenReturn(p4);
		assertEquals(p4, productService.updateProducts(p4.getProductId(),p4));
	}
	@Test
	public void updateProductException_test() {

		Product p3 = new Product();
		p3.setProductId(10);
		p3.setProductName("Hello");
		p3.setProductType("Cloth");
		p3.setCategory("Female");
		p3.setDescription("Good");
		p3.setImage("img1");
		p3.setPrice(120);
		p3.setRating("3.4");
		p3.setReview("good");
		p3.setSpecification("no");
		
		Optional<Product> byid = Optional.of(p3);
		when(productRepository.findById(10)).thenReturn(byid);
		//assertEquals(p1, productService.getProductById(11));
		assertThrows(ProductNotFoundException.class,()->{
			productService.getProductById(11);
		});

	}

	@Test
	public void getProductById_test() {
		Product p1 = new Product();
		p1.setProductId(10);
		p1.setProductName("Hello");
		p1.setProductType("Cloth");
		p1.setCategory("Female");
		p1.setDescription("Good");
		p1.setImage("img1");
		p1.setPrice(120);
		p1.setRating("3.4");
		p1.setReview("good");
		p1.setSpecification("no");

		Optional<Product> byid = Optional.of(p1);
		when(productRepository.findById(10)).thenReturn(byid);
		assertEquals(p1, productService.getProductById(10));

	}
	@Test
	public void getProductByIdException_test() {
		Product p1 = new Product();
		p1.setProductId(10);
		p1.setProductName("Hello");
		p1.setProductType("Cloth");
		p1.setCategory("Female");
		p1.setDescription("Good");
		p1.setImage("img1");
		p1.setPrice(120);
		p1.setRating("3.4");
		p1.setReview("good");
		p1.setSpecification("no");

		Optional<Product> byid = Optional.of(p1);
		when(productRepository.findById(10)).thenReturn(byid);
		//assertEquals(p1, productService.getProductById(11));
		assertThrows(ProductNotFoundException.class,()->{
			productService.getProductById(11);
		});

	}
	
	@Test
	public void getProductByCategory_test() {

		Product p7 = new Product();
		p7.setProductId(10);
		p7.setProductName("Hello");
		p7.setProductType("Cloth");
		p7.setCategory("Female");
		p7.setDescription("Good");
		p7.setImage("img1");
		p7.setPrice(120);
		p7.setRating("3.4");
		p7.setReview("good");
		p7.setSpecification("no");

		List<Product> bycategory = List.of(p7);
		when(productRepository.findByCategory("Female")).thenReturn(bycategory);
		assertEquals(bycategory, productService.getProductByCategory("Female"));

	}
	@Test
	public void getProductByCategoryException_test() {

		Product p7 = new Product();
		p7.setProductId(10);
		p7.setProductName("Hello");
		p7.setProductType("Cloth");
		p7.setCategory("Female");
		p7.setDescription("Good");
		p7.setImage("img1");
		p7.setPrice(120);
		p7.setRating("3.4");
		p7.setReview("good");
		p7.setSpecification("no");

		List<Product> bycategory = List.of(p7);
		when(productRepository.findByCategory("Female")).thenReturn(bycategory);
		assertThrows(CategoryNotFoundException.class,()->{
			productService.getProductByCategory("male");
		});

	}

	@Test
	public void getProductByType_test() {

		Product p8 = new Product();
		p8.setProductId(10);
		p8.setProductName("Hello");
		p8.setProductType("Cloth");
		p8.setCategory("Female");
		p8.setDescription("Good");
		p8.setImage("img1");
		p8.setPrice(120);
		p8.setRating("3.4");
		p8.setReview("good");
		p8.setSpecification("no");

		List<Product> bytype = List.of(p8);
		when(productRepository.findByProductType("Cloth")).thenReturn(bytype);
		assertEquals(bytype, productService.getProductByType("Cloth"));

	}
	@Test
	public void getProductByTypeException_test() {

		Product p8 = new Product();
		p8.setProductId(10);
		p8.setProductName("Hello");
		p8.setProductType("Cloth");
		p8.setCategory("Female");
		p8.setDescription("Good");
		p8.setImage("img1");
		p8.setPrice(120);
		p8.setRating("3.4");
		p8.setReview("good");
		p8.setSpecification("no");

		List<Product> bytype = List.of(p8);
		when(productRepository.findByProductType("Cloth")).thenReturn(bytype);
		assertThrows(ProductTypeNotExistsException.class,()->{
			productService.getProductByType("saree");
		});

	}
	@Test
	public void getProductByName_test() {
		Product p6 = new Product();
		p6.setProductId(10);
		p6.setProductName("Hello");
		p6.setProductType("Cloth");
		p6.setCategory("Female");
		p6.setDescription("Good");
		p6.setImage("img1");
		p6.setPrice(120);
		p6.setRating("3.4");
		p6.setReview("good");
		p6.setSpecification("no");
		
      List<Product> product=List.of(p6);
		when(productRepository.findByProductName("Hello")).thenReturn(product);
		assertEquals("hello", productService.getProductByName("Hello"));

	}
	@Test
	public void getProductByNameException_test() {
		Product p6 = new Product();
		p6.setProductId(10);
		p6.setProductName("Hello");
		p6.setProductType("Cloth");
		p6.setCategory("Female");
		p6.setDescription("Good");
		p6.setImage("img1");
		p6.setPrice(120);
		p6.setRating("3.4");
		p6.setReview("good");
		p6.setSpecification("no");

		List<Product> byname = List.of(p6);
		when(productRepository.findByProductName("Hello")).thenReturn(byname);
		assertThrows(ProductNotFoundException.class,()->{
			productService.getProductByName("hii");
		});

	}
	
	@Test
	public void deleteProduct_test() {

		Product p5 = new Product();
		p5.setProductId(10);
		p5.setProductName("Hello");
		p5.setProductType("Cloth");
		p5.setCategory("Female");
		p5.setDescription("Good");
		p5.setImage("img1");
		p5.setPrice(120);
		p5.setRating("3.4");
		p5.setReview("good");
		p5.setSpecification("no");

		Optional<Product> delete = Optional.of(p5);
		when(productRepository.findById(10)).thenReturn(delete);
		assertEquals("Deleted successfully", productService.deleteProductById(10));
	}
   @Test
   public void errorResponse_test() {
	ErrorResponse e = new ErrorResponse();

	e.setMessage("NOT NULL");
	e.setStatusmessage(null);
	e.setDatetime(LocalDateTime.now());

}

@Test
public void errorResponses_test() {

	ErrorResponse e1 = new ErrorResponse(HttpStatus.OK, "NOT NULL", LocalDateTime.now());
	e1.setMessage("NOT NULL");
	e1.setStatusmessage(HttpStatus.OK);
	e1.setDatetime(LocalDateTime.now());

}

@Test
public void LoggerResponse_test() {
	LoggerResponse l = new LoggerResponse();
	l.setMessage("NOT FOUND");

}

@Test
public void LoggerResponses_test() {
	LoggerResponse l = new LoggerResponse("Not Found");
	l.setMessage("NOt Null");
	l.getMessage();
}
@Test
public void productConstructor_test() {
	Product p4 = new Product(1, "Clothing", "Jeans", "Aparel", "3.3", "Good", "img", 200.0, "cotton", "all");

	p4.setProductId(14);
	p4.setProductName("Pen");
	p4.setProductType("Sationary");
	p4.setCategory("Book");
	p4.setDescription("Point pen");
	p4.setPrice(5.0);
	p4.setRating("3.3");
	p4.setImage("img2.jpg");
	p4.setReview("Good");
	p4.setSpecification("for all");
}

@Test
public void productDefaultConstructor_test() {
	
	Product p4 = new Product();
	p4.setProductId(14);
	p4.setProductName("Pen");
	p4.setProductType("Sationary");
	p4.setCategory("Book");
	p4.setDescription("Point pen");
	p4.setPrice(5.0);
	p4.setRating("3.3");
	p4.setImage("img2.jpg");
	p4.setReview("Good");
	p4.setSpecification("for all");
}

}

