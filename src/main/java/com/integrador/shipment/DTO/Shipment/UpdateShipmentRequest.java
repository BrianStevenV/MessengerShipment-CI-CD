package com.integrador.shipment.DTO.Shipment;

import com.integrador.shipment.Module.StateShipment;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateShipmentRequest {
    private String numberGuide;
    private StateShipment stateShipment;
    private Integer dniEmployee;

    public boolean isValidShipmentState(StateShipment state) {
        try {
            StateShipment.valueOf(state.name());
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

}


