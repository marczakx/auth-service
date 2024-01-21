FROM openjdk:22-ea-21-slim-bullseye
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

