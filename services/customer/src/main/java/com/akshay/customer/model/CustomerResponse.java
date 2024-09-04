package com.akshay.customer.model;


import java.util.List;

public record CustomerResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        List<Address> address
) {
}
