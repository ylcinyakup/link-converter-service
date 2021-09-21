FROM maven:3.8.2-jdk-11 AS build-env
ADD . ./link-converter-service
WORKDIR /link-converter-service
RUN mvn clean install -DskipTests

FROM openjdk:11-jre-slim
COPY --from=build-env ./link-converter-service/target/link-converter-service-0.0.1.jar app.jar
CMD java -jar app.jar
