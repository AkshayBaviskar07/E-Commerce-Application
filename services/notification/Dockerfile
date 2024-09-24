FROM openjdk:17
LABEL authors="Akshay"

WORKDIR /app
COPY ["target/notification.jar", "notification.jar"]
EXPOSE 8090

ENTRYPOINT ["java", "-jar", "notofication.jar"]