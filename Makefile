# Set the name of your Spring Boot application
APP_NAME := spring-microservice-project

# Define the JAR file name
JAR_FILE := $(APP_NAME).jar

# Define the Docker image name
DOCKER_IMAGE_NAME := $(APP_NAME):v1.0

# Define the Docker Compose file name
DOCKER_COMPOSE_FILE := docker-compose.yml

# Define the default target
.DEFAULT_GOAL := help

# Build the Spring Boot application
build:
	mvn clean package

# Build the Docker image
docker-build:
	@docker build -t $(DOCKER_IMAGE_NAME) .

# Run the Spring Boot application using Docker
docker-run:
	@docker run -p 8080:8080 -d $(DOCKER_IMAGE_NAME)

# Stop and remove the Docker container
docker-stop:
	@docker stop $$(docker ps -q --filter ancestor=$(DOCKER_IMAGE_NAME))

# Clean up the Docker container and image
docker-clean:
	@docker rm $$(docker ps -a -q --filter ancestor=$(DOCKER_IMAGE_NAME)) || true
	@docker rmi -f $(DOCKER_IMAGE_NAME) || true

# Start the Spring Boot application and PostgreSQL using Docker Compose
docker-compose-up:
	@docker compose -f $(DOCKER_COMPOSE_FILE) up -d

# Stop and remove the Docker Compose services
docker-compose-down:
	@docker compose -f $(DOCKER_COMPOSE_FILE) down

# Clean up the built JAR file
clean:
	@mvn clean

# Display help information about the available targets
help:
	@echo "Available targets:"
	@echo "- build: Build the Spring Boot application"
	@echo "- docker-build: Build the Docker image"
	@echo "- docker-run: Run the Spring Boot application using Docker"
	@echo "- docker-stop: Stop and remove the Docker container"
	@echo "- docker-clean: Clean up the Docker container and image"
	@echo "- docker-compose-up: Start the Spring Boot application and PostgreSQL using Docker Compose"
	@echo "- docker-compose-down: Stop and remove the Docker Compose services"
	@echo "- clean: Clean up the built JAR file"
	@echo "- help: Display this help information"

.PHONY: build docker-build docker-run docker-stop docker-clean docker-compose-up docker-compose-down clean help


