FROM openjdk:17
LABEL authors="Akshay"

WORKDIR /app
COPY ["target/product.jar", "product.jar"]
EXPOSE 8083

ENTRYPOINT ["java", "-jar", "product.jar"]