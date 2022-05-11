FROM openjdk:18.0.1.1-jdk-slim-buster

WORKDIR /app

COPY target/*.jar /app/api.jar

EXPOSE 8080

CMD ["java", "-jar", "api.jar"]