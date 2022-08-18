package com.barclays.cardservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.barclays.cardservice.entity.Customer;

/**
 * CustomerRepository - Interface for customer table in database
 * @author Ved
 *
 */
public interface CustomerRepository extends CrudRepository<Customer, Integer> {
	
}
