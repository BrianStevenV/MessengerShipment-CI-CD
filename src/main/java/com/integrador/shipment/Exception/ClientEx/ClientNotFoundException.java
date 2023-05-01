package com.integrador.shipment.Exception.ClientEx;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ClientNotFoundException extends EntityNotFoundException {
    private String message;
    private HttpStatus status;
    public ClientNotFoundException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
