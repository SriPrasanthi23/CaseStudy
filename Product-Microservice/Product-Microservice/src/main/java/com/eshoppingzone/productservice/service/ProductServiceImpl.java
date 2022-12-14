package com.eshoppingzone.productservice.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshoppingzone.exception.CategoryNotFoundException;
import com.eshoppingzone.exception.ProductAlreadyExistsException;
import com.eshoppingzone.exception.ProductNotFoundException;
import com.eshoppingzone.exception.ProductTypeNotExistsException;
import com.eshoppingzone.productservice.entity.Product;

import com.eshoppingzone.productservice.repository.ProductRepository;


@Service
public class ProductServiceImpl implements ProductService {

	
	Logger logger= LoggerFactory.getLogger(ProductServiceImpl.class);

	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Autowired
	private SequenceGeneratorService seqService;
	
	
	//adding new product
	@Override
	public Product addProduct(Product product) throws ProductAlreadyExistsException {
		if(productRepository.existsById(product.getProductId())) {
			
			throw new ProductAlreadyExistsException("productId"+product.getProductId()+" already exists enter another product id");
		}
			
		product.setProductId(seqService.getSequenceNum(Product.sequenceName));
		
		return productRepository.save(product);
	}
	
	// list all existing product
	@Override
	public List<Product> getAllProducts() {

		return productRepository.findAll();
	}
	
	// get product by productId
	@Override
	public Product getProductById(int productId) throws ProductNotFoundException{
		
		
		return productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException("ProductId:"+productId+" not found"));
	}

	//get product by product Name
	@Override
	public List<Product> getProductByName(String productName) throws ProductNotFoundException {
		
		Product product;
		if(productRepository.findByProductName(productName).isEmpty()) {
			logger.error("product not found  in find by product name");
			throw new ProductNotFoundException("productName:"+productName+" Product not found");
		}
		else {
			//Lproduct=productRepository.findByProductName(productName).getProductName();
			return productRepository.findByProductName(productName);
		}
		
	}
	
	// update existing  product by its productId
	@Override
	public Product updateProducts(int productId,Product product) throws ProductNotFoundException {
		
		Product	updatedProduct=productRepository.findById(productId).orElseThrow(()-> new ProductNotFoundException("ProductId"+productId+ " Product not found"));
		
		updatedProduct.setCategory(product.getCategory());
		updatedProduct.setDescription(product.getDescription());
		updatedProduct.setImage(product.getImage());
		updatedProduct.setPrice(product.getPrice());
		updatedProduct.setProductId(product.getProductId());
		updatedProduct.setProductName(product.getProductName());
		updatedProduct.setProductType(product.getProductType());
		updatedProduct.setRating(product.getRating());
		updatedProduct.setReview(product.getReview());
		updatedProduct.setSpecification(product.getSpecification());
		
		final Product finalUpdatedProduct= productRepository.save(updatedProduct);
		

		return finalUpdatedProduct;
	}
	
	//delete product by productId
	@Override
	public Map<String,Boolean> deleteProductById(int productId) throws ProductNotFoundException {
		
		Product product=productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException());
		
		productRepository.delete(product);
		
		
		
		Map<String, Boolean> response = new HashMap<>();
		
		response.put("Deleted", Boolean.TRUE);
		return response;
	}
	
	//get all products by category
	@Override
	public List<Product> getProductByCategory(String category)  throws CategoryNotFoundException{

		List<Product> product;
		if(productRepository.findByCategory(category).isEmpty()) {
			throw new CategoryNotFoundException("ProductCategory:"+category+ " Category not found");
		}
		else {
			product=productRepository.findByCategory(category);
		}
		
		return product;
	}
	
	//get all product by productType
	@Override
	public List<Product> getProductByType(String productType) throws ProductTypeNotExistsException {
		List<Product> product;
		if(productRepository.findByProductType(productType).isEmpty()) {
			throw new ProductTypeNotExistsException("ProductType:"+productType+ " ProductType Not exsists");
		}
		else {
			product=productRepository.findByProductType(productType);
		}
		
		return product;
	}

}