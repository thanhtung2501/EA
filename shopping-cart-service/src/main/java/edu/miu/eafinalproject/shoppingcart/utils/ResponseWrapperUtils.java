package edu.miu.eafinalproject.shoppingcart.utils;

import edu.miu.eafinalproject.shoppingcart.data.ResponseWrapper;
import org.springframework.stereotype.Component;

@Component
public class ResponseWrapperUtils {
    public ResponseWrapper getResponseObject(int responseCode, String message, Object response) {
        return ResponseWrapper.builder()
                .statusCode(responseCode)
                .message(message)
                .response(response)
                .build();
    }
}
