package com.saltech.event_consumer.service.impl;

import com.mongodb.client.result.DeleteResult;
import com.saltech.event_consumer.enums.Status;
import com.saltech.event_consumer.event.Customer;
import com.saltech.event_consumer.producer.EventProducer;
import com.saltech.event_consumer.repository.CustomerRepository;
import com.saltech.event_consumer.response.ApiResponse;
import com.saltech.event_consumer.response.BaseResponse;
import com.saltech.event_consumer.response.ResponseBuilder;
import com.saltech.event_consumer.response.ResponseConstants;
import com.saltech.event_consumer.service.CustomerService;
import com.saltech.event_consumer.service.HttpHeader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;

@Service
@Slf4j
public class CustomerServiceImpl extends HttpHeader implements CustomerService {

    @Autowired
    private ResponseBuilder responseBuilder;
    @Autowired
    private CustomerRepository repository;
    @Autowired
    private EventProducer producer;

    @Autowired
    private MongoOperations mongoOperations;
    @Override
    public void createCustomer(Customer customer) {
        customer.setCreatedDate(new Date());
        repository.save(customer);
    }

    public ResponseEntity<ApiResponse> onboardCustomer(Customer customer) {
        customer.setCreatedDate(new Date());
        BaseResponse response = new BaseResponse();
        try {
            customer.setStatus(Status.INITIALIZED.name());
            customer.setCreatedBy("System");
            Customer newCustomer = repository.insert(customer);
            if (customer!=null){
                producer.send(newCustomer);
                response.setStatusMassage("Success");
                response.setStatus("00");
                return responseBuilder.buildResponse(getHeader(),
                        HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, response);
            }else {
                response.setStatusMassage("Failed");
                response.setStatus("400");
                return responseBuilder.buildResponse(getHeader(),
                        HttpStatus.BAD_REQUEST.value(), ResponseConstants.FAILURE_MESSAGE, response);
            }
        }catch (Exception ex){
            response.setStatusMassage("Duplicate record");
            response.setStatus("400");
            if (ex.getMessage().contains("duplicate key error")){
                return responseBuilder.buildResponse(getHeader(),
                        HttpStatus.BAD_REQUEST.value(), ResponseConstants.FAILURE_MESSAGE, response);
            }
            else {
                return responseBuilder.buildResponse(getHeader(),
                        HttpStatus.BAD_REQUEST.value(), ResponseConstants.FAILURE_MESSAGE, ex.getMessage());
            }

        }


    }

    @Override
    public ResponseEntity<ApiResponse> findCustomerByNin(String nin) {
        Customer customer = repository.findCustomerByNin(nin);
        return responseBuilder.buildResponse(getHeader(),
                HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, customer);
    }

    public ResponseEntity<ApiResponse> deleteCustomer(String referenceId){
        Optional<Customer> optional = repository.findById(referenceId);
        BaseResponse response = new BaseResponse();
        if (!optional.isEmpty()){
            Customer customer = optional.get();
            log.info("Customer Found:{}",customer);
            repository.delete(optional.get());
            response.setStatusMassage(customer.getReferenceId() + " successfully deleted");
            response.setStatus("00");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, response);

        }else {
            response.setStatusMassage("Customer Not Found");
            response.setStatus("404");
            return responseBuilder.buildResponse(getHeader(),
                    HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, response);
        }

    }

    @Override
    public ResponseEntity<ApiResponse> findAllCustomer() {
        List<Customer> customers = repository.findAll();

        return responseBuilder.buildResponse(getHeader(),
                HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, customers);

    }

    public ResponseEntity<ApiResponse> findByDateRange(String startKey,String endKey) throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));

        Date fromDate = formatter.parse(startKey);
        Date toDate = formatter.parse(endKey);
        Query query = Query.query(Criteria.where("createdDate")
               .gte(fromDate)
                .lt(toDate));
        List<Customer> customers = mongoOperations.find(query,Customer.class);

        return responseBuilder.buildResponse(getHeader(),
                HttpStatus.OK.value(), ResponseConstants.HTTP_DEFAULT_RESPONSE_MESSAGE, customers);
    }
}
