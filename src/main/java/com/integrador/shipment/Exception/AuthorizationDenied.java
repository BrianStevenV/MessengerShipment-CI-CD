package com.integrador.shipment.Exception;

public class AuthorizationDenied extends RuntimeException{
    public AuthorizationDenied(String message){
        super(message);
    }
}
