package com.integrador.shipment.Service;

import com.integrador.shipment.DTO.Client.ClientDTO;
import com.integrador.shipment.DTO.Employee.EmployeeDTO;
import com.integrador.shipment.DTO.Employee.TypeEmployeeDTO;
import com.integrador.shipment.DTO.Package.PackageDTO;
import com.integrador.shipment.DTO.Shipment.*;
import com.integrador.shipment.Exception.AuthorizationDenied;
import com.integrador.shipment.Exception.ClientEx.ClientNotFoundException;
import com.integrador.shipment.Exception.EmployeeEx.EmployeeNotFoundException;
import com.integrador.shipment.Exception.ShipmentEx.FieldsMandatoryException;
import com.integrador.shipment.Exception.ShipmentEx.InvalidShipmentStateException;
import com.integrador.shipment.Exception.ShipmentEx.ShipmentNotFound;
import com.integrador.shipment.Module.Shipment;
import com.integrador.shipment.Module.StateShipment;
import com.integrador.shipment.Repository.ShipmentRepository;
import com.integrador.shipment.RestTemplate.RestTemplateShipment;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class ShipmentService {
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private RestTemplateShipment restTemplateShipment;

    private final Map<String, String> employeeTypeMap = new HashMap<>();

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private List<ShipmentGetFilterDTO> shipmentGetFilterDTOList = new ArrayList<>();

    private static String generateRandomAlphanumeric(int length) {
        return IntStream.range(0, length)
                .map(i -> random.nextInt(CHARACTERS.length()))
                .mapToObj(CHARACTERS::charAt)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private ClientDTO getClientDataBase(Integer dni) throws ClientNotFoundException {
        try {
            String url = "http://localhost:8081/api/v1/client/" + dni;
            String authStr = "admin:password123";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);

            // create request
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<ClientDTO> response = new RestTemplate().exchange(url, HttpMethod.GET,request, ClientDTO.class);

            if(response.getStatusCode() != HttpStatus.OK || response.getBody() == null){
                throw new ClientNotFoundException(HttpStatus.NOT_FOUND,"Client with dni: " + dni + " should be registered in the database for can send a package");
            }

            return response.getBody();
        } catch (RestClientException e) {
            throw new ClientNotFoundException(HttpStatus.NOT_FOUND,"Client with dni: " + dni + " should be registered in the database for can send a package");
        }
    }


    private PackageDTO createPackageShipment(Double weight){
        String url = "http://localhost:8082/api/v1/package?weight=" + weight;
        String authStr = "admin:password123";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

        // create headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + base64Creds);

        // create request
        HttpEntity<PackageDTO> request = new HttpEntity<>(headers);
        PackageDTO response = restTemplateShipment.postForObject(url, request, PackageDTO.class);
        return response;
    }
    public InformationShipmentDTO createShipment(@RequestBody ShipmentDTO shipmentDTO){
        try{
            if(anyFieldIsNull(shipmentDTO)){
                throw new FieldsMandatoryException(HttpStatus.BAD_REQUEST, "The following fields are required: " + missingFields(shipmentDTO));
            }
            ClientDTO idClient = getClientDataBase(shipmentDTO.getDni().getDni());
            PackageDTO pack = createPackageShipment(shipmentDTO.getWeight());
            String idShipment = generateRandomAlphanumeric(10);
            Shipment shipment = new Shipment(
                    idShipment,
                    idClient,
                    shipmentDTO.getOriginCity(),
                    shipmentDTO.getReceiverCity(),
                    shipmentDTO.getDestinationDirection(),
                    shipmentDTO.getNamePersonReceiver(),
                    shipmentDTO.getPhonePersonReceiver(),
                    null,
                    StateShipment.RECEIVED,
                    pack.getDeclaredValue(),
                    pack
            );
            shipmentRepository.save(shipment);
            InformationShipmentDTO informationShipmentDTO = new InformationShipmentDTO(shipment.getGuideNumber(), shipment.getStateShipment());
            return informationShipmentDTO;
        }   catch(FieldsMandatoryException e){
            throw new FieldsMandatoryException(HttpStatus.BAD_REQUEST, "The following fields are required: " + missingFields(shipmentDTO));
        }

    }

    private EmployeeDTO getEmployeeDataBase(Integer dni) {
        try{
            String url = "http://localhost:8080/api/v1/employee/" + dni;
            String authStr = "admin:password123";
            String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Basic " + base64Creds);

            // create request
            HttpEntity request = new HttpEntity(headers);
            ResponseEntity<EmployeeDTO> response = new RestTemplate().exchange(url, HttpMethod.GET, request, EmployeeDTO.class);

            if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
                throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND,"Employee with dni: " + dni + " doesn't found.");
            }

            return response.getBody();
        }   catch (RestClientException e){
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND, "Employee with dni: " + dni + " doesn't found.");
        }

    }


    public ShipmentGetDTO getById(String numberGuide) {
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(numberGuide);
        if (shipmentOptional.isPresent()) {
            Shipment shipment = shipmentOptional.get();
            ShipmentGetDTO shipmentGetDTO = new ShipmentGetDTO(
                    shipment.getClient().getDni(),
                    shipment.getClient().getNameClient(),
                    shipment.getOriginCity(),
                    shipment.getReceiverCity(),
                    shipment.getDestinationDirection(),
                    shipment.getNamePersonReceiver(),
                    shipment.getPhonePersonReceiver(),
                    shipment.getPack().getDeclaredValue(),
                    shipment.getPack().getWeight(),
                    shipment.getValueShipment(),
                    shipment.getStateShipment()
            );
            return shipmentGetDTO;
        } else {
            throw new ShipmentNotFound(HttpStatus.NOT_FOUND, "Shipment not found: " + numberGuide);
        }
    }

    public InformationShipmentDTO updateShipment(UpdateShipmentRequest updateShipmentRequest) throws AuthorizationDenied, EmployeeNotFoundException, ShipmentNotFound, InvalidShipmentStateException {
        EmployeeDTO dni = getEmployeeDataBase(updateShipmentRequest.getDniEmployee());
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(updateShipmentRequest.getNumberGuide());
        if (!updateShipmentRequest.isValidShipmentState(updateShipmentRequest.getStateShipment())) {
            throw new InvalidShipmentStateException(HttpStatus.BAD_REQUEST,"Invalid shipment state.");
        }

        if (!shipmentOptional.isPresent()) {
            throw new ShipmentNotFound(HttpStatus.NOT_FOUND,"The shipment: " + updateShipmentRequest.getNumberGuide() + " doesn't found in the database.");
        }

        Shipment shipment = shipmentOptional.get();

        if (shipment.getStateShipment() == StateShipment.RECEIVED && (dni.getTypeEmployee() == TypeEmployeeDTO.COORDINATOR || dni.getTypeEmployee() == TypeEmployeeDTO.DELIVERYMAN)) {
            shipment.setStateShipment(StateShipment.ON_ROUTE);
            shipmentRepository.save(shipment);
            InformationShipmentDTO informationShipmentDTO = new InformationShipmentDTO(shipment.getGuideNumber(), shipment.getStateShipment());
            return informationShipmentDTO;
        }

        if (shipment.getStateShipment() == StateShipment.ON_ROUTE && (dni.getTypeEmployee() == TypeEmployeeDTO.COORDINATOR || dni.getTypeEmployee() == TypeEmployeeDTO.DELIVERYMAN)) {
            shipment.setStateShipment(StateShipment.DELIVERED);
            shipment.setTimeClientReceive(LocalDateTime.now());
            shipmentRepository.save(shipment);
            InformationShipmentDTO informationShipmentDTO = new InformationShipmentDTO(shipment.getGuideNumber(), shipment.getStateShipment());
            return informationShipmentDTO;
        }

        throw new AuthorizationDenied(HttpStatus.BAD_REQUEST,"The employee does not have permission to update the shipment status");
    }
    public List<ShipmentGetFilterDTO> getFilterDTOList(StateShipment stateShipment, Integer dniEmployee) throws EmployeeNotFoundException {
        try{
            shipmentGetFilterDTOList.clear();
            EmployeeDTO employeeDTO = getEmployeeDataBase(dniEmployee);
            StateShipment save = stateShipment;

            //poner validacion de state
            research().stream()
                    .filter(shipment -> shipment.getStateShipment() == stateShipment)
                    .forEach(shipment -> {
                        ShipmentGetFilterDTO shipmentGetFilterDTO = new ShipmentGetFilterDTO();
                        shipmentGetFilterDTO.setDniClient(shipment.getClient().getDni());
                        shipmentGetFilterDTO.setCityOrigin(shipment.getOriginCity());
                        shipmentGetFilterDTO.setCityDestination(shipment.getReceiverCity());
                        shipmentGetFilterDTO.setAddressDestination(shipment.getDestinationDirection());
                        shipmentGetFilterDTO.setNameReceiver(shipment.getNamePersonReceiver());
                        shipmentGetFilterDTO.setPhoneReceiver(shipment.getPhonePersonReceiver());
                        shipmentGetFilterDTO.setValueDeclaredPackage(shipment.getPack().getDeclaredValue());
                        shipmentGetFilterDTO.setWeight(shipment.getPack().getWeight());
                        shipmentGetFilterDTO.setValueShipment(shipment.getValueShipment());
                        shipmentGetFilterDTO.setStateShipment(shipment.getStateShipment());
                        shipmentGetFilterDTO.setNumberGuide(shipment.getGuideNumber());
                        shipmentGetFilterDTOList.add(shipmentGetFilterDTO);
                    });
            return shipmentGetFilterDTOList;
        } catch (EmployeeNotFoundException e){
            throw new EmployeeNotFoundException(HttpStatus.NOT_FOUND,"Employee with dni: " + dniEmployee + " doesn't found.");
        }
    }



    public List<Shipment> research(){
        List<Shipment> shipments = (List<Shipment>) shipmentRepository.findAll();
        if(shipments.isEmpty()){
            throw new RuntimeException("Shipments  is empty");
        }
        return shipments;
    }

    private boolean anyFieldIsNull(ShipmentDTO shipmentDTO){
        return shipmentDTO.getDni() == null ||
                !StringUtils.hasText(shipmentDTO.getOriginCity()) ||
                !StringUtils.hasText(shipmentDTO.getReceiverCity()) ||
                !StringUtils.hasText(shipmentDTO.getDestinationDirection()) ||
                !StringUtils.hasText(shipmentDTO.getNamePersonReceiver()) ||
                !StringUtils.hasText(shipmentDTO.getPhonePersonReceiver()) ||
                shipmentDTO.getValueShipment() == null ||
                shipmentDTO.getWeight() == null;
    }
    private String missingFields(ShipmentDTO shipmentDTO) {
        StringJoiner joiner = new StringJoiner(", ");
        if (shipmentDTO.getDni() == null) joiner.add("DNI");
        if (!StringUtils.hasText(shipmentDTO.getOriginCity())) joiner.add("Origin City");
        if (!StringUtils.hasText(shipmentDTO.getReceiverCity())) joiner.add("Receiver City");
        if (!StringUtils.hasText(shipmentDTO.getDestinationDirection())) joiner.add("Destination City");
        if (!StringUtils.hasText(shipmentDTO.getNamePersonReceiver())) joiner.add("Name of recipient person");
        if (!StringUtils.hasText(shipmentDTO.getPhonePersonReceiver())) joiner.add("Recipient's phone number");
        if (shipmentDTO.getValueShipment() == null) joiner.add("Value Shipment");
        if (shipmentDTO.getWeight() == null) joiner.add("Weight");
        return joiner.toString();
    }

}
