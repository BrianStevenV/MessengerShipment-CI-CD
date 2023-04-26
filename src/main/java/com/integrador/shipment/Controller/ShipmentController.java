package com.integrador.shipment.Controller;

import com.integrador.shipment.DTO.Shipment.*;
import com.integrador.shipment.Exception.EmployeeNotFoundException;
import com.integrador.shipment.Module.StateShipment;
import com.integrador.shipment.Service.ShipmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(value="---", description = "This is controller to access the service of Shipment")
public class ShipmentController {
    @Autowired
    private ShipmentService shipmentService;

    @ApiResponses(value={
            @ApiResponse( code = 201, message = "created Shipment success"),
            @ApiResponse( code = 404, message = "DNI Client is incorrect."),
            @ApiResponse( code = 500, message ="That's an internal error"),
    })
    @ApiOperation(value="Shipment", notes= "this create a shipment and save in the database", response = InformationShipmentDTO.class)
    @PreAuthorize("hasRole('WRITE')")
    @PostMapping("/shipment")
    @ResponseStatus(HttpStatus.CREATED)
    public InformationShipmentDTO register(@ApiParam(value = "Shipment object", required = true) @RequestBody ShipmentDTO shipmentDTO){ return shipmentService.createShipment(shipmentDTO);}

    @ApiResponses(value={
            @ApiResponse( code = 201, message = "Get Shipment success."),
            @ApiResponse( code = 404, message = "Number Guide is incorrect."),
            @ApiResponse( code = 500, message ="That's an internal error"),
    })
    @ApiOperation(value="Shipment", notes= "this Get a shipment from the database", response = ShipmentGetDTO.class)
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/shipment/{numberGuide}")//Servicio solo para los administradores.
    public ShipmentGetDTO getShipmentDTO(@ApiParam(value = "Number Guide of shipment", required = true)@PathVariable("numberGuide") String numberGuide){
        return shipmentService.getById(numberGuide);
    }

    @ApiResponses(value={
            @ApiResponse( code = 201, message = "update Shipment success."),
            @ApiResponse( code = 404, message = "Fields incorrect."),
            @ApiResponse( code = 500, message ="That's an internal error."),
    })
    @ApiOperation(value="Shipment", notes= "this update a shipment and save in the database", response = InformationShipmentDTO.class)
    @PreAuthorize("hasRole('WRITE')")
    @PutMapping("/shipment")
    public InformationShipmentDTO updateShipmentDTO(@ApiParam(value = "UpdateShipmentRequest object", required = true)@RequestBody UpdateShipmentRequest updateShipmentRequest){
        return shipmentService.updateShipment(updateShipmentRequest);
    }

    @ApiResponses(value={
            @ApiResponse( code = 201, message = "Filter Shipments success."),
            @ApiResponse( code = 404, message = "Fields incorrect."),
            @ApiResponse( code = 500, message ="That's an internal error."),
    })
    @ApiOperation(value="Shipment", notes= "filter depending on shipping status", response = InformationShipmentDTO.class)
    @PreAuthorize("hasRole('READ')")
    @GetMapping("/shipment/{stateShipment}/employee/{dniEmployee}")
    public List<ShipmentGetFilterDTO> getFilterState(@ApiParam(value = "StateShipment object", required = true)@PathVariable("stateShipment") StateShipment stateShipment, @ApiParam(value = "DNI Employee", required = true) @PathVariable("dniEmployee") Integer dniEmployee) throws EmployeeNotFoundException {
        return shipmentService.getFilterDTOList(stateShipment,dniEmployee);
    }
}
