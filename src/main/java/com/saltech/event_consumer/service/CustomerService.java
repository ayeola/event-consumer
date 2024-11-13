package com.saltech.event_consumer.service;

import com.saltech.event_consumer.event.Customer;
import com.saltech.event_consumer.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface CustomerService {

    void createCustomer(Customer customer);

    ResponseEntity<ApiResponse> onboardCustomer(Customer customer);
    ResponseEntity<ApiResponse> findCustomerByNin(String nin);
    ResponseEntity<ApiResponse> findAllCustomer();

    ResponseEntity<ApiResponse> deleteCustomer(String referenceId);
    ResponseEntity<ApiResponse> findByDateRange(String startKey,String endKey) throws ParseException;
}
