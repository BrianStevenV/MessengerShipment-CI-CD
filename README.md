# MenssengerServiceShipment

# Localhost:8084

# Problem

A business needs a software that manages all the business logic, for this you need customers to interact with the application to be able to generate services with the company, you need to have a record and control of employees and allow the management of processes relating to the services offered by the company, you need a package management to have control over shipments and inventory, in addition to having a more reliable delivery system, and you need to be able to manage shipments to finish with your business logic.

# Description

This repository is a microservice about application MessengerService, in this microservice, you will find all management about Shipment, When somebody want to use this
application, the Client must register in the system his package to send, in this microservice, the Employees can check and update the information about the package and the shipment.
and shipment.
#Technologies
The project was built in: 

-Java
-SpringBoot
-SpringJPA
-SpringSecurity
-MySQL
-JUnit
-Swagger
-CI/CD

# Funcionalities

[POST] Create: Register a shipment in the database.
This service need a Client registered in the Database and input a Weight about package.
RequestBody:
{
        "dni": 123,
        "originCity":"Neiva",
        "receiverCity":"Medellin",
        "destinationDirection":"Carrera Java",
        "namePersonReceiver":"Mateo",
        "phonePersonReceiver":"310456789",
        "valueShipment":10000,
        "weight":3
}

[GET] Get Shipment: A Client can consult their information in the database.
Param: The param need a client registered in the database: {"dni":123}

[PUT] Update: An Employee can update the state of shipmnet.
Param: The param need an Employee registered in the database: {"dni":789}
RequestBody:
{
    "numberGuide":"XLA3478S",
     "stateShipment": "RECEIVED",
     "dniEmployee":"789"
}

[GET] Get Shipment filtered by State: You can search for shipments that are in a certain state.
Param: The param need the number guide of Shipment and DNI Employee.

![shipmentmsservice](https://user-images.githubusercontent.com/119947948/234174721-0bda9048-761f-4840-a2c6-f9037431f655.png)

