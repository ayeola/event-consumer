package com.saltech.event_consumer.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.saltech.event_consumer.event.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@SuppressWarnings("Unchecked")
public class EventProducer {

    private KafkaTemplate<String, String> kafkaTemplate;
    private String createEvent;


    public EventProducer(KafkaTemplate<String, String> kafkaTemplate,
                         @Value("${bulk_customer_topic}") String createEvent) {
        this.kafkaTemplate = kafkaTemplate;
        this.createEvent = createEvent;
    }

    public void send(Customer customer) {

        log.info("KafkaEvent:{}", customer);
        Gson gson = new GsonBuilder().create();
        String message = gson.toJson(customer);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate
                .send(createEvent, message);
    }

}
