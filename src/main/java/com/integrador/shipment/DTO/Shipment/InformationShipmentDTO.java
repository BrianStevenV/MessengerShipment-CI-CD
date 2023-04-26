package com.integrador.shipment.DTO.Shipment;

import com.integrador.shipment.Module.StateShipment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
public class InformationShipmentDTO {
    private String guideNumber;
    private StateShipment stateShipment;
}
