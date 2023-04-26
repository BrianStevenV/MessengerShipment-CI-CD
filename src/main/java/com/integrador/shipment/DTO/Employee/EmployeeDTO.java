package com.integrador.shipment.DTO.Employee;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer dni;
    private String nameEmployee;
    private String lastNameEmployee;
    private String phoneEmployee;
    private String emailEmployee;
    private String residencyAddressEmployee;
    private String cityLocationEmployee;
    private String lengthServiceEmployee;
    private String rhEmployee;
    private TypeEmployeeDTO typeEmployee;

    public EmployeeDTO(Integer dni, TypeEmployeeDTO typeEmployee){
        this.dni = dni;
        this.typeEmployee = typeEmployee;
    }
}
