FROM openjdk:17
LABEL authors="Akshay"

WORKDIR /app
COPY ["target/gateway.jar", "gateway.jar"]
EXPOSE 9000

ENTRYPOINT ["java", "-jar", "gateway.jar"]