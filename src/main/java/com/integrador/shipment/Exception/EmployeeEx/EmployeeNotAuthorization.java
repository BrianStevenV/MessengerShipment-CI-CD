package com.integrador.shipment.Exception.EmployeeEx;

public class EmployeeNotAuthorization extends SecurityException{
    public EmployeeNotAuthorization(String message){
        super(message);
    }
}
