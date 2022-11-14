FROM openjdk:18-jdk-alpine3.14
EXPOSE 8080
COPY /build/libs/demo-1.0.0.jar personia.jar
ENTRYPOINT ["java", "-jar", "personia.jar"]
