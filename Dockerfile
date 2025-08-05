# ===== Stage 1: Build Stage =====
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy Maven files first (cache dependencies)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy application source
COPY src ./src

# Build Spring Boot application
RUN mvn clean package -DskipTests

# ===== Stage 2: Runtime Stage =====
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copy built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Pass Git commit SHA as an environment variable
ARG GIT_COMMIT_SHA=dev-build
ENV GIT_COMMIT_SHA=$GIT_COMMIT_SHA

# Expose application port
EXPOSE 8080

# Run Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
