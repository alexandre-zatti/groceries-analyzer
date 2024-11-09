# Build the application
FROM eclipse-temurin:21-jdk-alpine AS builder

# Set the working directory inside the container
WORKDIR /app

# Copy only the necessary files to build the application
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Set executable permission for the Maven wrapper script
RUN chmod +x ./mvnw

# Build the Spring Boot application
RUN ./mvnw clean package -DskipTests

# Create the runtime image
FROM eclipse-temurin:21-jre-alpine

# Set a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring

# Create a directory for the app and switch to the new user
USER spring:spring
WORKDIR /home/spring

# Copy the built application JAR from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the default Spring Boot port
EXPOSE 8089

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
