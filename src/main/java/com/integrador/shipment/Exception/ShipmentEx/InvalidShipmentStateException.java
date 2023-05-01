package com.integrador.shipment.Exception.ShipmentEx;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class InvalidShipmentStateException extends IllegalArgumentException{
    private String message;
    private HttpStatus status;
    public InvalidShipmentStateException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
