package com.akshay.customer.model;

import java.util.HashMap;

public record ErrorResponse(
        HashMap<String, String> errors
) {
}
