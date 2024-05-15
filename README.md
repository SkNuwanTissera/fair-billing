# Fair Billing

Fair Billing is a Java program designed to calculate the total duration and number of sessions for users based on session start and end times provided in a log file. It is particularly useful for hosted application providers who charge based on session duration.

## Features

- Parses session start and end times from a log file.
- Calculates the total duration and number of sessions for each user.
- Handles cases where session start or end times are missing or incomplete.
- Produces a report showing user names, session counts, and total durations.

## Requirements

- Java 8 or higher
- Maven for building the project
- Log file containing session start and end times (in the format HH:MM:SS)

## Usage

1. Navigate to the project directory:

    ```bash
    cd fair-billing
    ```

2. Build the project and run tests using Maven:

    ```bash
    mvn package
    mvn test
    ```

3. Run the program with the path to the log file as a command-line argument:

    ```bash
    java -jar target/fair-billing.jar /path/to/logfile.txt
    ```

   Replace `/path/to/logfile.txt` with the path to your log file.

4. View the generated log file (fairbilling.log) in root showing user names, session counts, and total durations.
 
## Example

Refer to the example log file below:
```
14:02:03 ALICE99 Start
14:02:05 CHARLIE End
14:02:34 ALICE99 End
14:02:58 ALICE99 Start
14:03:02 CHARLIE Start
14:03:33 ALICE99 Start
14:03:35 ALICE99 End
14:03:37 CHARLIE End
14:04:05 ALICE99 End
14:04:23 ALICE99 End
14:04:41 CHARLIE Start
```

## Docker

Build the Docker image using the following command in the same directory as the Dockerfile:
```
docker build -t <image-name> .
```
Replace <image-name> with the desired name for your Docker image.

Run the Docker container using the following command:
```
docker run -p 8080:8080 <image-name>
```
Replace <image-name> with the name you specified in earlier step.

This will start your Java application within a Docker container, making it easy to deploy and run in any environment that supports Docker.