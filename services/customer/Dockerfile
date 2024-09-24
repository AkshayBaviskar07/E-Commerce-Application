FROM openjdk:17

WORKDIR /app
COPY ["target/customer.jar", "customer.jar"]
EXPOSE 8082

ENTRYPOINT ["java", "-jar", "customer-service.jar"]