package com.integrador.shipment.Exception.EmployeeEx;

import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class EmployeeNotFoundException extends EntityNotFoundException {
    private String message;
    private HttpStatus status;
    public EmployeeNotFoundException(HttpStatus status, String message){
        super(message);
        this.status = status;
        this.message = message;
    }
}
