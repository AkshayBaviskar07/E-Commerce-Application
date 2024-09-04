package com.akshay.customer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "customer_tbl")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> address;

    public void addAddress(Address address) {
        address.setCustomer(this);
        this.address.add(address);
    }

    public void removeAddress(Address address) {
        address.setCustomer(null);
        this.address.remove(address);
    }
}
