# Kafka JSON Serialization/Deserialization
This is Maven based Java application for both producing/consuming json payloads of Kafka events. This application is 
dockerized and configured with docker-compose to make it easy to run. 
## Project Structure
* kafka-json-serialization: The parent Maven project
* json-consumer: Project's module which consumes Kafka events in topic "order-topic".
* json-producer: Project's module which produces Kafka events with JSON payload in topic "order-topic".
## Main Classes
* EventPayload: The base class for events' payloads.
* OrderPayload: represents the order's payloads.
* Event: A generic class to represent any event read from a topic. This class either have corresponding events' value 
or any exception has throws.
* OrderPayloadSerializer: A JSON serializer to serialize events' value.
* OrderPayloadDeserializer: A JSON deserializer to deserialize event's value to object representations.
## Requirements
You need to install Java 16, Maven, and docker-compose. 
## How to run
Run "mvn clean package" in the project's root directory
run "docker-compose up -d"
run "docker-compose logs consumer-service" and "docker-compose logs producer-service" to see how they communicate! 
  