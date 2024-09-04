package com.akshay.product.handler;

import java.util.HashMap;

public record ErrorResponse(
        HashMap<String, String> errors
) {
}
