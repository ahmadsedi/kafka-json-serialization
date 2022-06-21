FROM adoptopenjdk:16_36-jre-hotspot
ADD ./target/kafka-json-deserialization-1.0-SNAPSHOT-jar-with-dependencies.jar app.jar
ENTRYPOINT ["java","-classpath","./app.jar", "com.ahmadsedighi.kafka.receiver.ConsumerRunner"]