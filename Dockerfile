# Use a base image with JDK and Maven pre-installed
FROM maven:3.8.4-openjdk-11-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build the project and run tests
RUN mvn -B clean package

# Set the entry point to your application's main class
ENTRYPOINT ["java", "-cp", "target/classes:target/dependency/*", "com.bt.FairBilling"]