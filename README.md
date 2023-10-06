## Pizza Order Demo Application

This is a small showcase that demonstrates Camunda forms being used at a start event to kick off an order process as well as generating a PDF document and sending it via email.

### Prerequisites to build / run the project
 - Java 17 installed
 - Maven installed
 - Access to a Camunda SaaS cluster


### How to build the project
This project is a straightforward Springboot application. In order to run it locally, please follow these steps:

1. Clone the repo (or download it as zip file)
2. Import it into an IDE of your choice
3. Create a client for Zeebe via Console in your SaaS environment
4. Copy/paste src/main/resources/application.properties.example to application.properties
5. Enter the client credentials from the generated client
6. Enter the data of your preferred email provider (tested with https://mailtrap.io)
7. run ```mvn clean install```
8. start the application by either:
   1. run the BitApplication from within your IDE
   2. ```mvn clean spring-boot:run```
   3. cd ```target``` followed by ```java -jar bit-0.0.1-SNAPSHOT.jar```


