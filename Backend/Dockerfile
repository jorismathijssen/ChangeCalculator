# Use an official OpenJDK 21 image as a base for building
FROM openjdk:21-jdk-slim AS build

WORKDIR /app

# Copy source code
COPY . .

# Grant execute permissions to Gradlew
RUN chmod +x gradlew

# Build the application inside the container
RUN ./gradlew build --no-daemon

# Use a smaller final image
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
