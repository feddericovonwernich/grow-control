#!/bin/bash

# Create the directory if it doesn't exist
mkdir -p ~/Workspace/grow-control

# Navigate into the directory
cd ~/Workspace/grow-control

# Pull the latest changes from the repository
echo "Pulling the latest changes from the repository..."
git pull

# Build the project with Gradle
echo "Building the project with Gradle..."
./gradlew clean build

# Function to wait for pong response
waitForPong() {
    local timeout=180
    local elapsed=0
    local interval=5

    echo "Waiting for 'pong' response from the server..."

    while [ $elapsed -lt $timeout ]; do
        # Use curl to check for 'pong' response
        response=$(curl -s localhost:9080/ping)

        if [ "$response" == "pong!" ]; then
            echo "Received 'pong' response from the server."
            return 0
        else
            sleep $interval
            ((elapsed+=interval))
        fi
    done

    echo "Timeout waiting for 'pong' response from the server."
    exit 1
}

# Function to wait for server to stop responding to ping
waitForPingStop() {
    local timeout=180
    local elapsed=0
    local interval=5

    echo "Waiting for the server to stop responding to ping..."

    while [ $elapsed -lt $timeout ]; do
        # Use curl to check the ping endpoint
        if ! curl -s localhost:9080/ping; then
            echo "Server has stopped responding to ping."
            return 0
        else
            echo ""
            sleep $interval
            ((elapsed+=interval))
        fi
    done

    echo "Timeout waiting for server to stop responding to ping. The server might still be running."
    exit 1
}

# Function to check if a Docker service is running
check_service_running() {
    local service_name="$1"
    local service_id=$(docker-compose ps -q $service_name)

    if [ -z "$service_id" ]; then
        echo "The $service_name service is not running."
        return 1
    fi

    local service_status=$(docker inspect -f '{{.State.Running}}' $service_id)

    if [ "$service_status" = "true" ]; then
        echo "The $service_name service is already running."
        return 0
    else
        echo "The $service_name service is not running."
        return 1
    fi
}

# Your service name as defined in docker-compose.yml
service_name="gc-app"
db_service="gc_mysql"

echo "OPENIA_API_KEY: $OPENIA_API_KEY"

# Check if a screen session named 'gcappscreen' already exists
if screen -list | grep -q "gcappscreen"; then
    echo "The screen session 'gcappscreen' already exists. Stopping any running processes..."

    # Stop and remove just the specified service
    echo "Stopping and removing the $service_name service..."
    docker-compose stop $service_name
    docker-compose rm -f $service_name

    # Try removing the image as well, should always be called like this.
    docker rmi grow-control_gc-app

    # Check that its not running.
    waitForPingStop

    # Check if the gc_mysql service is running, and start it if it's not
    if ! check_service_running $db_service; then
        echo "Starting the $db_service service..."
        docker-compose up -d $db_service
    fi

    # Start up the services defined in your docker-compose.yml file
    # Recreate the specified service without starting dependencies
    echo "Recreating and starting the $service_name service in detached mode..."
    OPENIA_API_KEY="$OPENIA_API_KEY" docker-compose up -d --no-deps --force-recreate $service_name

    echo "Attached to 'gcappscreen' and updated the gc-app service..."
else
    # Start a new detached screen session named 'gcappscreen' and run 'docker-compose up'
    echo "Starting a new screen session named 'gcappscreen' and running 'docker-compose up'"
    screen -dmS gcappscreen bash -c "OPENIA_API_KEY=$OPENIA_API_KEY docker-compose up -d; exec bash"
fi

echo "The server should now be up and running in a screen session named 'gcappscreen', with containers recreated."
echo "Use 'screen -r gcappscreen' to attach to the session."

waitForPong