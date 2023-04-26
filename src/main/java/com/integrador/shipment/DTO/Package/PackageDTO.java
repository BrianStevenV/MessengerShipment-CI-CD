package com.integrador.shipment.DTO.Package;

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
@Table(name = "Package")
public class PackageDTO implements Serializable {
    @Id
    @Column(name = "Id_Package")
    private String id;
    @Column(name = "Type_Package")
    private TypePackageDTO typePackageDTO;
    @Column(name = "Weight")
    private Double weight;
    @Column(name = "Declared_Value")
    private Double declaredValue;
}
