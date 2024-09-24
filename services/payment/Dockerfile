FROM openjdk:17
LABEL authors="Akshay"

WORKDIR /app
COPY ["target/payment.jar", "payment.jar"]
EXPOSE 8085

ENTRYPOINT ["java", "-jar", "payment.jar"]