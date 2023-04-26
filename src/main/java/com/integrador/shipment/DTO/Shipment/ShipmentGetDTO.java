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
public class ShipmentGetDTO {
    private Integer dniClientDTO;
    private String nameClientDTO;
    private String cityOriginDTO;
    private String cityReceiverDTO;
    private String addressDestinationDTO;
    private String nameReceiverDTO;
    private String phoneReceiverDTO;
    private Double valueDeclaredPackageDTO;
    private Double weightDTO;
    private Double valueShipmentDTO;
    private StateShipment stateShipmentDTO;
}
