package edu.miu.eafinalproject.shoppingcart.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseWrapper {
    private int statusCode;
    private String message;
    private Object response;
}
