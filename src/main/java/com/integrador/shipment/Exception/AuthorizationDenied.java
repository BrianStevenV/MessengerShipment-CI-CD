package com.integrador.shipment.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
public class AuthorizationDenied extends RuntimeException {
    private String message;
    private HttpStatus status;
    public AuthorizationDenied(HttpStatus status,String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
