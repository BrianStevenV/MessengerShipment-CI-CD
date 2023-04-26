package com.integrador.shipment.DTO.Shipment;

import com.integrador.shipment.Module.StateShipment;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class ShipmentGetFilterDTO {
    private Integer dniClient;
    private String cityOrigin;
    private String cityDestination;
    private String addressDestination;
    private String nameReceiver;
    private String phoneReceiver;
    private Double valueDeclaredPackage;
    private Double weight;
    private Double valueShipment;
    private StateShipment stateShipment;
    private String numberGuide;
}
