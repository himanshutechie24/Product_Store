# --- Build Stage ---
FROM maven:3.9.10-eclipse-temurin-21 AS build

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the app
COPY src ./src
RUN mvn clean package -DskipTests


# --- Runtime Stage ---
FROM eclipse-temurin:21-jre-jammy

# Set working directory
WORKDIR /app

# Copy the jar from the build stage
COPY --from=build /app/target/ecommerce-0.0.1-SNAPSHOT.jar .

# Expose app port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "/app/ecommerce-0.0.1-SNAPSHOT.jar"]
