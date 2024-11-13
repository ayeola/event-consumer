package com.saltech.event_consumer.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "customer")
@Data
@AllArgsConstructor
public class Customer {

    @Id private String referenceId;
    @Indexed(unique = true)
    private String nin;
    @Indexed(unique = true)
    private String bvn;
    @Indexed(unique = true)
    private String msisdn;
    private String firstName;
    private String lastName;
    private String dob;
    private String address;
    @Indexed(unique = true)
    private String email;
    private Date createdDate;
    private String gender;
    private String status;
    private String createdBy;

}
