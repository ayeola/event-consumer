package com.saltech.event_consumer.repository;

import com.saltech.event_consumer.event.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<Customer,String> {

    Customer findCustomerByNin(String nin);
}
