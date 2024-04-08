# Makefile

# Copyright(c) 2023, Santu Das, Corp. All rights reserved.
#

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# Make for App Management
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

VENV_DIR := env
PYTHON := $(VENV_DIR)/bin/python
PIP := $(VENV_DIR)/bin/pip

.PHONY: setup format lint test clean

# Default target
all: setup format lint test

# Create a virtual environment and install dependencies
setup:
	@echo -e '\n\nInstalling App Dependencies.............................!\n'
	npm install
	@echo -e '\nApp environment setup has been successfully completed...!\n'

# Format and Lint code test
code-test:
	@echo -e '\n---------------------------------------------------------'
	@echo -e 'Lint Testing !!'
	@echo -e '---------------------------------------------------------'
	npm run lint
	@echo -e '\n---------------------------------------------------------'
	@echo -e 'Format Testing !!'
	@echo -e '---------------------------------------------------------'
	npm run format
	@echo -e '--------------------------[EOT]--------------------------'


# Run unit testing with formatting and lint
test:
	@echo -e '\n---------------------------------------------------------'
	@echo -e 'Lint Testing !!'
	@echo -e '---------------------------------------------------------'
	npm run lint
	@echo -e '\n---------------------------------------------------------'
	@echo -e 'Format Testing !!'
	@echo -e '---------------------------------------------------------'
	npm run format
	@echo -e '\n---------------------------------------------------------'
	@echo -e 'Unit Testing !!'
	@echo -e '---------------------------------------------------------'
	npm run test
	@echo -e '--------------------------[EOT]--------------------------'

# Start the app
run:
	npm run dev

# Clean up virtual environment and generated files
clean:
	rm -rf node_modules

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# Make for Bump Version
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

# Bump app patch version
bump-patch:
	$(PYTHON) -m bumpversion patch

# Bump app minor version
bump-minor:
	$(PYTHON) -m bumpversion minor

# Bump app major version
bump-major:
	$(PYTHON) -m bumpversion major

# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
# Make for Docker Management
# +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

JSON_FILE = package.json
CONTAINER_NAME = $(shell jq -r .name $(JSON_FILE))
VERSION = $(shell jq -r .version $(JSON_FILE))
APP_PORT = 3000# $(shell jq -r .APP_PORT $(JSON_FILE))
DOCKER_HUB_USER = 192.168.56.12:8084# $(shell jq -r .DOCKER_HUB_USER $(JSON_FILE))
DOCKER_IMAGE_NAME = ${DOCKER_HUB_USER}/${CONTAINER_NAME}:${VERSION}

# Build app docker image
docker-build:
	docker build . -t ${DOCKER_IMAGE_NAME}

# Push app docker image to docker-hub 
docker-push:
	docker image push ${DOCKER_IMAGE_NAME}

# Run docker app container
docker-run:
	docker run -d --name $(CONTAINER_NAME) -p ${APP_PORT}:${APP_PORT} $(DOCKER_IMAGE_NAME)

# Get bash of app container
docker-bash:
	docker exec -it $(CONTAINER_NAME) bash

# Start docker app container
docker-start:
	docker start $(CONTAINER_NAME)

# Stop docker app container
docker-stop:
	docker stop $(CONTAINER_NAME)

# Remove docker app container
docker-rm:
	docker rm -f $(CONTAINER_NAME)

# Remove app docker image
docker-rmi:
	docker rmi -f $(DOCKER_IMAGE_NAME)

# Stop and Remove app docker container
docker-clean: docker-stop docker-rm docker-rmi

.PHONY: docker-build docker-run docker-bash docker-start docker-stop docker-rm docker-rmi docker-clean