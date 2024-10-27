# PlayArena 

## Project Overview

This project is a **Java application** for managing video game tournaments. It implements a platform to organize and track various gaming events, handling the registration and management of players, teams, and tournaments. The project was developed to practice the fundamental concepts of **Spring Core** and follows a layered architecture.

### Key Features

- **Player Management**: Register, update, delete, and view players.
- **Team Management**: Create, update, add/remove players, and view teams.
- **Tournament Management**: Create, update, manage teams, and view tournaments.
- **Tournament Duration Calculation**: Implemented using two methods for basic and advanced calculations.

### Technologies Used

- **Java 8**: Core language features, including Stream API, Lambda expressions, and Java Time API.
- **Spring Core**: Used for IoC (Inversion of Control) and DI (Dependency Injection) via XML configuration.
- **JPA & Hibernate**: For database access and management using H2 as the in-memory database.
- **JUnit & Mockito**: For unit and integration testing.
- **Maven**: Dependency management.
- **JaCoCo**: Code coverage measurement.
- **Logger**: Used for logging throughout the application.

### Project Structure

- **Model Layer**: Contains JPA entities like Player, Team, Tournament, and Game.
- **Repository Layer**: Manages data access using Hibernate.
- **Service Layer**: Business logic for managing players, teams, and tournaments.
- **Console Menu**: Provides a simple console interface for interacting with the application.

### Tournament Duration Calculation

- **Basic Calculation**: `(Number of teams × Average match duration) + (Break time between matches)`
- **Advanced Calculation**: `(Number of teams × Average match duration × Game difficulty) + (Break time between matches) + (Opening/Closing ceremony time)`

## How to Clone and Run the Project

### Prerequisites

- Java 8
- Maven
- Git

### Clone the Repository

```bash
git clone https://github.com/bachiriy/PlayArena.git
cd PlayArena 
```

### Configure and Run the App
```bash
   cd app
   mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
   mvn clean package 
   java -cp $(cat classpath.txt):target/ctarget/playarena-1.0-SNAPSHOT.jar com.playarena.app.App
```
- or simpley run the script:
```bash
   ./run.sh
```
> **Note:** You have to change the path to the cloned project app before so you can run the script without erros:
```bash
# Full path to the cloned project app
PROJECT_DIR="/cloned_location/PlayArena/app"
...
```

> **Note:** Also notice that you must run the H2 tcp server for the app operations with database, can be run with the command:
```bash
   cd ~/.m2/repository/com/h2database/h2/1.4.200/
   java -cp h2-1.4.200.jar org.h2.tools.Server -tcp
```
## See JaCoCo Report by opening `app/target/site/jacoco/index.html` in your browser.
- example:
```bash
   firefox app/target/site/jacoco/index.html
``` 
 
### Jira
https://mohammedelbachiri945.atlassian.net/jira/software/projects/PLAYAR/boards/35

--- 
enjoy ^^
