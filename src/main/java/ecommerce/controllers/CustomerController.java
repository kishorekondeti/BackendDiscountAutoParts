package ecommerce.controllers;

import java.security.Principal;

import ecommerce.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ecommerce.models.CustomerModel;
import ecommerce.services.CustomerService;

@RestController
public class CustomerController {
	@Autowired private CustomerService customerService;

	@Autowired private OrderRepository orderRepository;

	@PostMapping("CustomerRegistration")
	public String  customerRegistration(@RequestBody CustomerModel customerModel,Principal principal) {
		return customerService.CustomerRegistration(customerModel,principal);
	}

	@GetMapping("getCustomerOrderCount")
	public Long countByCustomerModelEmailAndStatus(@RequestParam("email") String email, @RequestParam("status") String status){
		return orderRepository.countByCustomerModelEmailAndStatus(email,status);
	}


}
