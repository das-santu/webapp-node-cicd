# Simple NodeJS Web Application with Jenkins and ArgoCD CICD Pipiline

This is used in the demonstration of Develop, Test and Deploy a Web Application with Jenkins and ArgoCD Operator into Kubernetes Enviroment.

## 1. CICD Architecture Diagram

![CICD Architecture](/images/cicd_architecture.jpg 'CICD Architecture')

## 2. App Web View Snapshot

![Web View](/images/web_view.jpg 'Web View')

## 3. Pipeline Stages:

- CICD Pipeline have following stages
  - Declarative: Checkout SCM
  - Script Initialization
  - Load Env Variables
  - Install Dependencies
  - Unit Tests
  - Code quality check with SonarQube
  - Vulnerability Scan with OWASP
  - Build Docker Image
  - Scan Docker Image with Trivy
  - Push Image to Nexus Docker Hub
  - Update the SCM menifest with latest image version
  - Pull the updated menifest and deploy the app with ArgoCD to K8s Cluster
  - Test the running pod on K8s Cluster

## 4. Test Your Web App

Open a browser and go to URL

        http://<IP>:5000                      => Welcome

## Credential need to cerate on Jenkins are following

        ID                          Kind
        ------------------------      ------------------------
        PYTHON_SSH_AGENT_KEY        Username with password
        GIT_ACCESS_TOKEN            Username with password
        DOCKER_HUB_ACCESS_TOKEN	Username with password
        MASTER_SSH_KEY	       	Secret file
