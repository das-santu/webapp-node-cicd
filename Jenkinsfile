def gv

pipeline {
    agent any

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
                // Use the shared script to load version information from package.json
                script {
                    def appEnv = gv.loadAppEnv()

                    // Set environment variables
                    env.VERSION = appEnv.version
                    env.APP_NAME = appEnv.name
                    env.APP_PORT = appEnv.webapp-info.port
                    env.GIT_REPO_URL = appEnv.webapp-info.git-repo-url
                    env.GIT_CRED = GIT_CRED
                    env.DOCKER_IMAGE_NAME = "${appEnv.webapp-info.docker_user}/${appEnv.name}:${appEnv.version}"
                    env.DOCKER_REPO_URL = appEnv.webapp-info.docker-repo-url
                    env.DOCKER_CRED = DOCKER_CRED

                    // env.PROD_SERVER_PORT = appEnv.PROD_SERVER_PORT
                    // env.MASTER_SERVER_CRED_ID = appEnv.MASTER_SERVER_CRED_ID
                    // env.PROD_SERVER_USER = appEnv.PROD_SERVER_USER
                    // env.PROD_SERVER_HOST = appEnv.PROD_SERVER_HOST
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

        stage('Unit Testing') {
            steps {
                // Run your node unit tests here
                script {
                    gv.unitTest()
                }
            }
        }

        stage('Code Quality Check') {
            steps {
                // Run your Python unit tests here
                script {
                    gv.codeQualityCheck()
                }
            }
        }

    //     stage('Vulnerability Scan') {
    //         steps {
    //             // Run your Python unit tests here
    //             script {
    //                 gv.vulnerabilityScan()
    //             }
    //         }
    //     }

    //     stage('Build Docker Image') {
    //         steps {
    //             // Build the Docker image
    //             script {
    //                 gv.buildApp()
    //             }
    //         }
    //     }

    //     stage('Scan Docker Image') {
    //         steps {
    //             // Run tests on the Docker image
    //             script {
    //                 gv.scanDockerImage()
    //             }
    //         }
    //     }

    //     stage('Push Image to Docker Repo') {
    //         steps {
    //             // Push the Docker image to Docker Hub
    //             script {
    //                 gv.pushApp()
    //             }
    //         }
    //     }

    //     stage('Deploy the App with Argocd to K8s Cluster') {
    //         steps {
    //             // Deploy the Docker image to the remote production server using Ansible
    //             script {
    //                 gv.deployApp()
    //             }
    //         }
    //     }

    //     stage('Check the running pods on K8s Cluster') {
    //         steps {
    //             // Deploy the Docker image to the remote production server using Ansible
    //             script {
    //                 gv.deployApp()
    //             }
    //         }
    //     }
    }

    // post {
    //     always {
    //         // Clean up steps if necessary
    //         script {
    //             gv.cleanLocalEnv()
    //             gv.cleanProdEnv()
    //         }
    //     }
    // }
}
