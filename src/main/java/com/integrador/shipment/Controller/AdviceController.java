package com.integrador.shipment.Controller;

import com.integrador.shipment.DTO.Exception.ErrorDTO;
import com.integrador.shipment.Exception.AuthorizationDenied;
import com.integrador.shipment.Exception.ClientEx.ClientNotFoundException;
import com.integrador.shipment.Exception.EmployeeEx.EmployeeNotFoundException;
import com.integrador.shipment.Exception.ShipmentEx.FieldsMandatoryException;
import com.integrador.shipment.Exception.ShipmentEx.InvalidShipmentStateException;
import com.integrador.shipment.Exception.ShipmentEx.ShipmentNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(value = ClientNotFoundException.class)
    public ResponseEntity<ErrorDTO> clientNotFoundException(ClientNotFoundException ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<ErrorDTO> employeeNotFoundException(EmployeeNotFoundException ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = FieldsMandatoryException.class)
    public ResponseEntity<ErrorDTO> fieldsMandatoryException(FieldsMandatoryException ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = ShipmentNotFound.class)
    public ResponseEntity<ErrorDTO> shipmentNotFound(ShipmentNotFound ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(value = AuthorizationDenied.class)
    public ResponseEntity<ErrorDTO> authorizationDenied(AuthorizationDenied ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(value = InvalidShipmentStateException.class)
    public ResponseEntity<ErrorDTO> invalidShipmentStateException(InvalidShipmentStateException ex){
        ErrorDTO error = ErrorDTO.builder().code(String.valueOf(ex.getStatus().value())).messaje(ex.getMessage()).build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
