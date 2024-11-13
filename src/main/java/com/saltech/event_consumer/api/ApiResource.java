package com.saltech.event_consumer.api;

import com.saltech.event_consumer.constants.UriConstant;
import com.saltech.event_consumer.event.Customer;
import com.saltech.event_consumer.response.ApiResponse;
import com.saltech.event_consumer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@Slf4j
@RestController
@RequestMapping("/api/v1/consumer/customer")
public class ApiResource {

    @Autowired
    private CustomerService customerService;


    @PostMapping(path = UriConstant.create_customer, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> createCustomer(@RequestBody Customer customer) {
        log.info("CustomerProfile:{}", customer);
        return customerService.onboardCustomer(customer);
    }

    @GetMapping(path = UriConstant.get_customer, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> getCustomer(
            @RequestParam("nin") String nin) {
        return customerService.findCustomerByNin(nin);
    }

    @DeleteMapping(path = UriConstant.deleteCustomer, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> deleteCustomer(
            @RequestParam("referenceId") String referenceId) {
        return customerService.deleteCustomer(referenceId);
    }

    @GetMapping(path = UriConstant.findAll, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findAll() {
        return customerService.findAllCustomer();
    }

    @GetMapping(path = UriConstant.findByDateRange, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse> findByDateRange(
            @RequestParam("startKey") String startKey,@RequestParam("endKey") String endKey) throws ParseException {
        return customerService.findByDateRange(startKey,endKey);
    }
}
