package com.integrador.shipment.Exception;

import org.springframework.data.crossstore.ChangeSetPersister;

public class EmployeeNotFoundException extends ChangeSetPersister.NotFoundException {
    private String message;
    public EmployeeNotFoundException(String message){
        super();
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
