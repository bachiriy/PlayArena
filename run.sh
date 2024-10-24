#!/bin/bash

# Full path to the cloned project app
PROJECT_DIR="/home/usr/Documents/GitHub/PlayArena/app"
JAR_PATH="$PROJECT_DIR/target/playarena-1.0-SNAPSHOT.jar"
CLASSPATH_FILE="$PROJECT_DIR/classpath.txt"

clean_project() {
    [ -f "$CLASSPATH_FILE" ] && rm "$CLASSPATH_FILE"
    mvn clean
}

build_project() {
    echo "Building the project..."
    cd "$PROJECT_DIR" || exit
    clean_project
    mvn package || { echo "Build failed. Exiting."; exit 1; }
    echo "Build successful."
}

run_app() {
    echo "Running app..."
    mvn dependency:build-classpath -Dmdep.outputFile="$CLASSPATH_FILE"
    java -cp "$(cat "$CLASSPATH_FILE")":"$JAR_PATH" com.playarena.app.App
}

build_project
run_app

