package com.chinakrit.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.chinakrit.dto.request.CustomerRequest;
import com.chinakrit.entity.Customer;
import com.chinakrit.repository.CustomerRepository;

@Service
@RestController
@RequestMapping("/customers")
public class CustomerController {
	@Autowired
	private CustomerRepository customerRepository;
	
	@PostMapping()
	public Customer createCustomer(@RequestBody CustomerRequest request) {
		Customer customer = new Customer();
		customer.setFirstName(request.getFirstName());
		customer.setLastName(request.getLastName());
		customer.setCustomerDate(request.getCustomerDate());
		customer.setIsVip(request.getIsVip());
		customer.setStatusCode(request.getStatusCode());
		
		return customerRepository.save(customer);
	}
	
	@GetMapping("/{customerId}")
	public Customer getCustomer(@PathVariable Long customerId) {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
		}
		return customer.get();
	}
	
	@PatchMapping("/{customerId}")
	public Customer updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest request) {
		Customer customer = getCustomer(customerId);
		if (request.getFirstName() != null)
			customer.setFirstName(request.getFirstName());
		if (request.getLastName() != null)
			customer.setLastName(request.getLastName());
		if (request.getCustomerDate() != null)
			customer.setCustomerDate(request.getCustomerDate());
		if (request.getIsVip() != null)
			customer.setIsVip(request.getIsVip());
		if (request.getStatusCode() != null)
			customer.setStatusCode(request.getStatusCode());
		
		return customerRepository.save(customer);
	}
	
	@DeleteMapping("/{customerId}")
	public Customer deleteCustomer(@PathVariable Long customerId) {
		Customer customer = getCustomer(customerId);
		customerRepository.delete(customer);
		
		return customer;
	}
}
