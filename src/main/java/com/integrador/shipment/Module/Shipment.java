package com.integrador.shipment.Module;

import com.integrador.shipment.DTO.Client.ClientDTO;
import com.integrador.shipment.DTO.Package.PackageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shipment")
@ApiModel(description ="this model represent the Shipment data.")
public class Shipment implements Serializable {
    @ApiModelProperty(value = "Number Guide", example ="XD123BN80")
    @Id
    @Column(name = "guide_Number")
    private String guideNumber;
    @ApiModelProperty(value = "Client Object -> referenced DNI", example ="234567")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Dni_Client", referencedColumnName = "Dni_Client")
    private ClientDTO client;
    @ApiModelProperty(value = "Origin City", example ="Madrid")
    @Column(name = "origin_City")
    private String originCity;
    @ApiModelProperty(value = "Receiver City", example ="Barcelona")
    @Column(name = "receiver_City")
    private String receiverCity;
    @ApiModelProperty(value = "Destination Direction", example ="Street 22")
    @Column(name = "destination_Direction")
    private String destinationDirection;
    @ApiModelProperty(value = "Name Person Receiver", example ="Angelo")
    @Column(name = "name_Person_Receiver")
    private String namePersonReceiver;
    @ApiModelProperty(value = "Phone Person  Receiver", example ="5003002001")
    @Column(name = "phone_Person_Receiver")
    private String phonePersonReceiver;
    @ApiModelProperty(value = "Time Client Receive Shipment", example ="12/03/2022::15:35")
    @Column(name = "time_Client_Receive")
    private LocalDateTime timeClientReceive;
    @ApiModelProperty(value = "State shipment", example ="RECEIVED")
    @Column(name = "state_Shipment")
    private StateShipment stateShipment;
    @ApiModelProperty(value = "Value Shipment", example ="50000.0")
    @Column(name = "value_Shipment")
    private Double valueShipment;
    @ApiModelProperty(value = "Package object -> referenced ID", example ="XDF567BN")
    @ManyToOne
    @JoinColumn(name = "Id_Package", referencedColumnName = "Id_Package")
    private PackageDTO pack;
}
