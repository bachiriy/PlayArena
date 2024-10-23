#!/bin/bash

PROJECT_DIR="/home/exshy/Documents/GitHub/PlayArena/app/"

JAR_PATH="/home/exshy/Documents/GitHub/PlayArena/app/target/app-1.0-SNAPSHOT.jar"

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
    clear && cd "$PROJECT_DIR" ; /usr/bin/env /usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -cp /tmp/cp_b3xylhpdieot8yir5rmhmyqyw.jar com.playarena.app.App
    echo "End app."
}
build_project
run_app
