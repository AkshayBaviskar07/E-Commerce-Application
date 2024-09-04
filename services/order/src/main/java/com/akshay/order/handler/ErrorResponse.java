package com.akshay.order.handler;

import java.util.HashMap;

public record ErrorResponse(
        HashMap<String, String> errors
) {
}
