#!/bin/bash

# change with the full path to the cloned project app
PROJECT_DIR="/home/username/Documents/GitHub/PlayArena/app"

JAR_PATH="$PROJECT_DIR/target/playarena-1.0-SNAPSHOT.jar"

build_project() {
    echo "Building the project..."
    cd "$PROJECT_DIR" || exit
    mvn clean package
    if [ $? -ne 0 ]; then
        echo "Build failed. Exiting."
        exit 1
    fi
    echo "Build successful."
}

run_app() {
    echo "Running app..."
    java -cp $(cat "$PROJECT_DIR/classpath.txt"):"$JAR_PATH" com.playarena.app.App
}

build_project
run_app

