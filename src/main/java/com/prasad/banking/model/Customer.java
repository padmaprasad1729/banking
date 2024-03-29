package com.prasad.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "Customer")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Long uniqueNumber;

    private String customerId;

    private String firstName;

    private String lastName;

    @JoinColumn(name="address")
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    @JoinColumn(name="mobileNumber")
    @OneToOne(cascade = CascadeType.ALL)
    private MobileNumber mobileNumber;

    private String emailAddress;

    private CustomerStatus status;
}
