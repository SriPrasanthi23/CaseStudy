package com.eshoppingzone.productservice.resource;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.eshoppingzone.exception.ProductNotFoundException;
import com.eshoppingzone.productservice.entity.AuthenticationRequest;
import com.eshoppingzone.productservice.entity.AuthenticationResponse;
import com.eshoppingzone.productservice.entity.JwtUtil;
import com.eshoppingzone.productservice.entity.Product;
import com.eshoppingzone.productservice.entity.User1;
import com.eshoppingzone.productservice.repository.UserRepository;
import com.eshoppingzone.productservice.service.MyUserDetailsService;
import com.eshoppingzone.productservice.service.ProductService;
import com.eshoppingzone.productservice.service.ProductServiceImpl;

@RestController
@RequestMapping("/product")
public class ProductResource {

	@Autowired
	private ProductService productServiceImpl;
	
	
	Logger logger= LoggerFactory.getLogger(ProductResource.class);

	ProductResource() {
	}

	@PostMapping("/add")
	public ResponseEntity<Product> addProducts(  @RequestBody Product product) {
		
		logger.warn("product"+product);
		Product addedUser= productServiceImpl.addProduct(product);
		
		return new ResponseEntity<>(addedUser,HttpStatus.CREATED);
		
	}

	@GetMapping("/allProducts")
	public List<Product> getAllProducts() {
	 List<Product> pro=productServiceImpl.getAllProducts();
		return pro;
		
	}

	@GetMapping("/findById/{productId}")
	public ResponseEntity<Product> getProductById(@PathVariable int productId) {
		return new ResponseEntity<>	(productServiceImpl.getProductById(productId),HttpStatus.OK);			
		
	}

	@GetMapping("/findByType/{productType}")
	public ResponseEntity<List<Product>> getProductByType(@PathVariable String productType) {
		
		return new ResponseEntity<>(productServiceImpl.getProductByType(productType),HttpStatus.OK);
	}

	@GetMapping("/findByName/{productName}")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName) {
		
		return new ResponseEntity<>(productServiceImpl.getProductByName(productName),HttpStatus.OK);
	}

	@GetMapping("/findByCategory/{category}")
	public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
		
		return new ResponseEntity<>(productServiceImpl.getProductByCategory(category),HttpStatus.OK);
	}

	@PutMapping("/update/{productId}")
	public ResponseEntity<Product> updateProduct(@PathVariable int productId, @Valid @RequestBody Product product) throws ProductNotFoundException {
		;
		return  ResponseEntity.ok(productServiceImpl.updateProducts(productId,product));
	}

	@DeleteMapping("/delete/{productId}")
	public Map<String,Boolean> deleteProduct(@PathVariable int productId)throws ProductNotFoundException {
		
		return productServiceImpl.deleteProductById(productId);
	}
	@Autowired
	private  AuthenticationManager authenticationManager;
	@Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtTokenUtil;
    @Autowired
    private UserRepository repo;


	
	@PostMapping("/reg")
    private ResponseEntity<?> subscribe(@RequestBody AuthenticationRequest request)
    {

        String username = request.getUsername();
        String password = request.getPassword();

 

        User1 model = new User1();
        model.setUsername(username);
        model.setPassword(password);

 

        try {
            repo.save(model);
        }
        catch (Exception e) {
            return ResponseEntity.ok(new AuthenticationResponse("Error while subsribing the user with username " + username));
        }
            return ResponseEntity.ok(new AuthenticationResponse("client subscribed with username " + username));
    }
    @RequestMapping(value="/authenticate", method=RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try
        {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
    }
        catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

 

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }
}
