FROM openjdk:11-jre-slim
MAINTAINER "Milko Zlatev mhz1g18@soton.ac.uk"
WORKDIR /app

COPY ./target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

EXPOSE 8080