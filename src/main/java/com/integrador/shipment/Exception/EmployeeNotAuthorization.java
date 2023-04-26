package com.integrador.shipment.Exception;

public class EmployeeNotAuthorization extends SecurityException{
    public EmployeeNotAuthorization(String message){
        super(message);
    }
}
