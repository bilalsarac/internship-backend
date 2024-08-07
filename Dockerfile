FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY target/mobile-0.0.1-SNAPSHOT.jar /app/mobile.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/mobile.jar"]