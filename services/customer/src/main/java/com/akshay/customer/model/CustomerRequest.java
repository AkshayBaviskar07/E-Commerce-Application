package com.akshay.customer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CustomerRequest(
        Integer id,

        @NotNull(message = "First name is required")
        String firstName,

        @NotNull(message = "Last name is required")
        String lastName,

        @NotNull(message = "Email is required")
        @Email(message = "Email address is not valid")
        String email,

        List<Address> address
) {
}
