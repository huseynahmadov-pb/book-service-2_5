FROM eclipse-temurin:21.0.2_13-jre-alpine
WORKDIR /app
COPY ./build/libs/book-service_2_5-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
