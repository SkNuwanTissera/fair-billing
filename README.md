[![Java CI with Maven](https://github.com/SkNuwanTissera/fair-billing/actions/workflows/maven.yml/badge.svg)](https://github.com/SkNuwanTissera/fair-billing/actions/workflows/maven.yml)
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
    mvn clean install
    ```
    This will create a JAR file in the target directory.

    ```bash
    ls target

3. Run the program with the path to the log file as a command-line argument:

    ```bash
    java -jar target/fair-billing.jar /path/to/logfile.log
    ```

   Replace `/path/to/logfile.txt` with the path to your log file.

4. View the generated log file (fairbilling.log) in root showing user names, session counts, and total durations.

## Docker

Pre-requisites: Docker installed on your machine/vm.

To run the Java application in a Docker container, follow these steps:

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

## Example
### Sample log file (Input)
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
### Sample log output (Output)
A log file (fairbilling.log) will be generated in the root directory showing the following output:
```
2024-05-16 23:05:27,225 INFO c.b.u.FairBillingUtils [main] ALICE99 4 240
2024-05-16 23:05:27,230 INFO c.b.u.FairBillingUtils [main] CHARLIE 3 37
```
The output shows the user name, session count, and total duration for each user. It also logs any lines that are ignored due to incorrect formatting.
```
2024-05-16 23:06:18,706 ERROR c.b.u.FairBillingUtils [main] Ignoring line: 14:02:05 CHARLIE Enddddddd
2024-05-16 23:06:18,712 ERROR c.b.u.FairBillingUtils [main] Ignoring line: 14:02:34 ALICE@99 End
2024-05-16 23:06:18,712 ERROR c.b.u.FairBillingUtils [main] Ignoring line: 14:03:33 ALICE!99 Start
2024-05-16 23:06:18,712 ERROR c.b.u.FairBillingUtils [main] Ignoring line: 14:03:35 ALICE$99 End
```

