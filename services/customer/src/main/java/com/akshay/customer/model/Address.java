package com.akshay.customer.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Validated
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String street;
    private String houseNumber;
    private String zipCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
