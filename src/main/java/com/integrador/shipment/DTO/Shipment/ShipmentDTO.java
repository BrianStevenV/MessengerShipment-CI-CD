package com.integrador.shipment.DTO.Shipment;

import com.integrador.shipment.DTO.Client.ClientDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ShipmentDTO {
    private ClientDTO dni;
    private String originCity;
    private String receiverCity;
    private String destinationDirection;
    private String namePersonReceiver;
    private String phonePersonReceiver;
    //private String valueShipment;
    private Double valueShipment;
    private Double weight;


}
