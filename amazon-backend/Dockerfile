# Stage 1: Build Spring Boot jar
FROM gradle:8-jdk17-alpine AS builder

WORKDIR /app
COPY . .

# Build the Spring Boot jar without running tests
RUN ./gradlew bootJar -x test --no-daemon

# Stage 2: Run the jar in a lightweight JRE image
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy Spring Boot boot jar from builder
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the port
EXPOSE 8080

# Run the jar
CMD ["java", "-jar", "app.jar"]

