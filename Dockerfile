# Use a base image with JDK and Maven pre-installed
FROM maven:3.8.4-openjdk-17-slim

# Install bash if not present
RUN apt-get update && apt-get install -y bash

# Set the working directory in the container
WORKDIR /app

# Copy the whole project
COPY . /app

# Run Maven with tests
RUN mvn clean install

# Command to run your application when the container starts
CMD ["java", "-jar", "target/*-jar-with-dependencies.jar", "src/main/java/com/bt/logs/sessions.log"]
