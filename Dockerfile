# Use an official Maven image as a build stage
FROM maven:3.8.4-openjdk-11-slim AS build
WORKDIR /app
COPY . .
# Run tests
RUN mvn test
# Build the application
RUN mvn -B package --file pom.xml

# Use AdoptOpenJDK as a runtime image
FROM adoptopenjdk/openjdk11:jdk-11.0.12_7-alpine
WORKDIR /app
# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar
# Expose port 8080
EXPOSE 8080
# Run the JAR file
CMD ["java", "-jar", "app.jar"]
