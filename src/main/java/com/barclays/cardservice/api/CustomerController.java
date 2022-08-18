package com.barclays.cardservice.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.cardservice.constants.SystemConstants;
import com.barclays.cardservice.dto.CardDTO;
import com.barclays.cardservice.dto.CustomerDTO;
import com.barclays.cardservice.exception.BarclaysException;
import com.barclays.cardservice.service.CardCustomerService;

/**
 * CustomerController - Rest api for customer details related routes
 * @author Ved
 * 
 */
@RestController
@RequestMapping("/api")
public class CustomerController {
	
	@Autowired
	CardCustomerService cardCustomerService;
	
	/**
	 * getCustomer - Get all details of customer including card details
	 * @param customerId - Id of customer
	 * @return Customer Details json
	 * @throws BarclaysException
	 */
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomer(@PathVariable Integer customerId) throws BarclaysException {
		CustomerDTO customer = cardCustomerService.getCustomerDetails(customerId);
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	
	/**
	 * addCustomer - Create new customer
	 * @param customer - customer data
	 * @return ID of newly created customer
	 * @throws BarclaysException
	 */
	@PostMapping("/customer/new")
	public ResponseEntity<Integer> addCustomer(@RequestBody CustomerDTO customer) throws BarclaysException {
		Integer id = cardCustomerService.addCustomer(customer);
		return new ResponseEntity<>(id, HttpStatus.OK);
	}
	
	/**
	 * issueCard - Issues new card to a user
	 * @param customerId - Customer id
	 * @param card - Details for new card
	 * @return Http response
	 * @throws BarclaysException
	 */
	@PostMapping("/customer/{customerId}/newcard")
	public ResponseEntity<String> issueCard(@PathVariable Integer customerId, @RequestBody CardDTO card) throws BarclaysException {
		cardCustomerService.issueCardToExistingCustomer(customerId, card);
		return new ResponseEntity<>(SystemConstants.CARD_ISSUSED_SUCCESS_RESPONSE, HttpStatus.OK);
	}
	
	/**
	 * deleteCustomer - Delete customer data along with their card data
	 * @param customerId - Customer id
	 * @return Http Response
	 * @throws BarclaysException
	 */
	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws BarclaysException {
		cardCustomerService.deleteCustomer(customerId);
		return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
	}
	
	/**
	 * deleteCustomerCards - Delete customer cards
	 * @param customerId - Customer id
	 * @param cardIds - List of card ids to be deleted
	 * @return Http response
	 * @throws BarclaysException
	 */
	@DeleteMapping("/customer/{customerId}/cards")
	public ResponseEntity<String> deleteCustomerCards(@PathVariable Integer customerId, @RequestBody List<Integer> cardIds) throws BarclaysException {
		cardCustomerService.deleteCardOfExistingCustomer(customerId, cardIds);
		return new ResponseEntity<>("Customer deleted successfully", HttpStatus.OK);
	}
}
