package com.chinakrit.repository;

import org.springframework.data.repository.CrudRepository;

import com.chinakrit.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
