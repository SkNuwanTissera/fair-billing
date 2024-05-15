# Use a base image with JDK and Maven pre-installed
FROM maven:3.8.4-openjdk-11-slim

# Install bash if not present
RUN apt-get update && apt-get install -y bash

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Install application/test dependencies and build the project
RUN mvn -B clean install

# Run tests
RUN mvn test

# Set the entry point to your application's main class
ENTRYPOINT ["java", "-cp", "target/classes:target/dependency/*", "com.bt.FairBilling"]