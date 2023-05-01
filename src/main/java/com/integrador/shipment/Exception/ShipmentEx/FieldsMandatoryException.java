package com.integrador.shipment.Exception.ShipmentEx;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class FieldsMandatoryException extends IllegalArgumentException{
    private String message;
    private HttpStatus status;
    public FieldsMandatoryException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
