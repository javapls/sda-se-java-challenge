# Coding Challenge Read Me
 
This read is compose for 3 chapters.

- [Local Configuration](#local-configuration)
- [Solution Explanation](#solution-explanation)

---

## Local Configuration

This project is composed of 3 microservices and a docker file.

The first step is to run the docker file that will run the Zookeeper and Kafka Locally. For this, you can run the command docker-compose up.

After the Kafka is running we need to start the Microservices:

- #### document-checker-facade
    - This Microservice will run on ```http://localhost:8081```.
    - We have a collection for local tests using <b>document-repository</b> microservice as a data source, you can check inside the folder <b>collection</b>. The collection contains requests with case tests but depends on the <b>document-repository</b> microservice.
  
- #### pdf-checker
  -  This Microservice is responsible for processing the kafka events.

- #### document-repository
  - This Microservice is a simple service to expose document simulating a external API. You can add other documents, for this just paste the document inside the folder resource.

---
## Solution Explanation

The solution is based on microservices, we have two: document-checker-facade and pdf-checker.

The application document-checker-facade has the responsibility to expose an endpoint POST <b>check-document</b> and expect a body. Inside the body is expected to receive the URL that the document needs to be recovered and processed. The application after receiving the request will create a Kafka Event, <b>CheckEvent</b>,and the <b>pdf-checker</b> will process the information. Before sending the event create a correlationId and this ID is passed by the Kafka header.

This is a unique ID that permits us to have async processing and track the entire event between the microservices.

The <b>pdf-checker</b> has the responsibility to consume the CheckEvent, and validate the Magic Byte and content inside, IBAN.

### Architecture

The Architecture uses some concepts of Clean Architecture. I chose this architecture due to the organization and I can guarantee the single responsibility principle. Every class has defined and specific goals. Using the layers concept is an easy way to simplify the maintenance, reduce code duplicity, and a good way to organize. 

These projects have this Organization:

- #### Adapters
  The Adapters have the responsibility of communication between other systems. In this folder, we have the Kafka Producer and Consumer, and Document for getting documents by URL.

- #### Domain
   In this layer we have our entities. 

- #### Service
  Services contain the business logic that will manipulate and process the data.

- #### Processor
  Processors have the responsibility to orchestrate the processing of a flow. If it is necessary to use one or more services, the processor will manage this entire flow.

- #### Controller
  The Controllers will expose our EndPoints, in other interpretations Clean Architecture Controllers are part of adapters, but in Spring Projects, it is common for the controllers to be separated.

### Unit Test
For unit tests, I used Mockito and JUnit. To organize the test I used the [AAA Pattern](https://medium.com/@pjbgf/title-testing-code-ocd-and-the-aaa-pattern-df453975ab80).
You can check at the <b>pdf-checker</b> microservice.