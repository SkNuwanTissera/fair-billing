# Use a base image with JDK and Maven pre-installed
FROM maven:3.8.4-openjdk-11-slim

# Install bash
RUN apt-get update && apt-get install -y bash && rm -rf /var/lib/apt/lists/*

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src ./src

# Build and run tests
RUN mvn -B clean package

# Default command to execute when the container starts
CMD ["bash"]
