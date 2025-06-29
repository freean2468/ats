FROM eclipse-temurin:24-jdk-alpine
WORKDIR /app
COPY build/libs/demo-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xms512m", "-Xmx512m", "-jar", "app.jar"]
