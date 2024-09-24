# Stage 1: Run the application
FROM openjdk:17
# Set the working directory
WORKDIR /app
# Copy the JAR file from the build stage
COPY ["target/order.jar", "order.jar"]
# Expose port
EXPOSE 8084
# Run the application
ENTRYPOINT ["java", "-jar", "order.jar"]
