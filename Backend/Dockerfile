# Use an official OpenJDK 21 image as a base for building
FROM amazoncorretto:21 AS build

# Set working directory
WORKDIR /app

# Copy only the build-related files first to leverage caching
COPY gradle/wrapper gradle/wrapper
COPY gradlew ./
RUN chmod +x gradlew

# Download dependencies to cache them
RUN ./gradlew dependencies --no-daemon

# Copy the rest of the source code
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# Use a minimal runtime image for production
FROM amazoncorretto:21-alpine

# Set working directory
WORKDIR /app

# Copy only the built JAR file from the previous stage
COPY --from=build /app/build/libs/*.jar app.jar

# Create a non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Expose the application's port
EXPOSE 8080

# Set environment variables (can be overridden in Kubernetes)
ENV JAVA_OPTS="-Xms256m -Xmx512m"

# Run the application with proper signal handling
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
