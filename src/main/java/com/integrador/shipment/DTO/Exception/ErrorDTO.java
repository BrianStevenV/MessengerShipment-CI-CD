package com.integrador.shipment.DTO.Exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String code;
    private String messaje;
}
