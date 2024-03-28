def gv

pipeline {
    agent { label 'ubuntu-agent' }

    stages {

        stage('Script Initialization') { 
            steps { 
                // Load groove script here
                script { 
                    gv = load "script.groovy"
                }
            }
        }

        stage('Load Env Variables') {
            steps {
                // Use the shared script to load version information from version.json
                script {
                    def appEnv = gv.loadAppEnv()

                    // Set environment variables
                    env.VERSION = appEnv.VERSION
                    env.APP_NAME = appEnv.NAME
                    env.APP_PORT = appEnv.APP_PORT
                    env.GIT_REPO_URL = appEnv.GIT_REPO_URL
                    env.GIT_CREDENTIALS_ID = appEnv.GIT_CREDENTIALS_ID
                    env.DOCKER_IMAGE_NAME = "${appEnv.DOCKER_HUB_USER}/${appEnv.NAME}:${appEnv.VERSION}"
                    env.DOCKER_HUB_REGISTRY_URL = appEnv.DOCKER_HUB_REGISTRY_URL
                    env.DOCKER_HUB_CREDENTIALS_ID = appEnv.DOCKER_HUB_CREDENTIALS_ID
                    env.PROD_SERVER_PORT = appEnv.PROD_SERVER_PORT
                    env.MASTER_SERVER_CRED_ID = appEnv.MASTER_SERVER_CRED_ID
                    env.PROD_SERVER_USER = appEnv.PROD_SERVER_USER
                    env.PROD_SERVER_HOST = appEnv.PROD_SERVER_HOST
                }
            }
        }

        stage('Install Dependencies') {
            steps {
                // Install Python and required dependencies
                script {
                    gv.installDependencies()
                }
            }
        }
        stage('Black - Code Formatting') {
            steps {
                // Run Black for code formatting
                script {
                    gv.codeFormatting()
                }
            }
        }

        stage('Flake8 - Code Linting') {
            steps {
                // Run Flake8 for code linting
                script {
                    gv.codeLinting()
                }
            }
        }

        stage('Unit Tests') {
            steps {
                // Run your Python unit tests here
                script {
                    gv.unitTest()
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                // Build the Docker image
                script {
                    gv.buildApp()
                }
            }
        }

        stage('Test Docker Image') {
            steps {
                // Run tests on the Docker image
                script {
                    gv.testApp()
                }
            }
        }

        stage('Push Image to Docker Hub') {
            steps {
                // Push the Docker image to Docker Hub
                script {
                    gv.pushApp()
                }
            }
        }

        stage('Deploy to Production') {
            steps {
                // Deploy the Docker image to the remote production server using Ansible
                script {
                    gv.deployApp()
                }
            }
        }
    }

    post {
        always {
            // Clean up steps if necessary
            script {
                gv.cleanEnv()
            }
        }
    }
}
