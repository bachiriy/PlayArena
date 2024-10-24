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

### Run the App
```bash
   cd app
   mvn clean install
   java -cp target/app-1.0-SNAPSHOT.jar com.playarena.app.App
```
- or simpley change dir app path in run.sh and run:
```bash
   ./run.sh
```
> **Note:** if you don't have the classpath.txt included, you can't run the app. to generate it run the following command(in app/) and re run the app:
```bash
   mvn dependency:build-classpath -Dmdep.outputFile=classpath.txt
```
### Jira
https://mohammedelbachiri945.atlassian.net/jira/software/projects/PLAYAR/boards/35

--- 
enjoy ^^
