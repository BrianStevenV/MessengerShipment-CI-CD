package com.integrador.shipment.DTO.Client;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Client")
public class ClientDTO implements Serializable {
    @Id
    @Column(name = "Dni_Client")
    @JsonProperty("dni")
    private Integer dni;
    @Column(name = "Name_Client")
    private String nameClient;
    @Column(name = "LastName_Client")
    private String lastNameClient;
    @Column(name = "Phone_Client")
    private String phoneClient;
    @Column(name = "Email_Client")
    private String emailClient;
    @Column(name = "ResidencyAddress_Client")
    private String residencyAddressClient;
    @Column(name = "CityLocation_Client")
    private String cityLocationClient;

    public ClientDTO(Integer dni) {
        this.dni = dni;
    }
}
