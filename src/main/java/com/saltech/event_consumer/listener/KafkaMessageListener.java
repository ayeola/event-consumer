package com.saltech.event_consumer.listener;

import com.google.gson.Gson;
import com.saltech.event_consumer.event.Customer;
import com.saltech.event_consumer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaMessageListener {

    @Autowired
    private CustomerService customerService;

    @KafkaListener(topics = "create_event_topic",groupId = "customer_group")
    public void messageLister(String message){
        log.info("Message:{}",message);
        Gson gson = new Gson();
        Customer customer = gson.fromJson(message,Customer.class);
        customerService.createCustomer(customer);

    }
}
