package com.integrador.shipment.Exception.ShipmentEx;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ShipmentNotFound extends EntityNotFoundException {
    private String message;
    private HttpStatus status;

    public ShipmentNotFound(HttpStatus status,String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
