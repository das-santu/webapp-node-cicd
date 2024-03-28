# Simple Web Application with Jenkins Pipeline Script with Groove

This is used in the demonstration of Simple Web Application with Jenkins Pipeline Script with Groove.
  
  Below are the steps required to get this working on a base linux system.
  
  - Setup an DevOps enviroment with following Servers
    - Install and Configure a Jenkins Master Server
    - Install and Configure a Jenkins Agent Server
    - Install and Configure a Production Server for Web App deployment
    - Create a GitHub Account for version control with respected user token
    - Create a DockerHub Account for app image repo with respected user token


## 1. Update version.json 

  - The version.json file have the following information and that needs to be updated as per your enviroment

    - app name
    - app version
    - app port
    - git hub repo url
    - git hub credential id (update according to jenkins credential for same)
    - docker hub repo url
    - docker hub credential id (update according to jenkins credential for same)
    - jenkins master server private key (update according to jenkins credential for same)
    - production server port
    - production server user
    - production server host


## 2. Start the Jenkins Server and Cerate a Pipeline Job

  - Following are the steps to ceate a pipeline job

    - First Create a Pipeline Job with Respected Name and Description
    - Mention Pipeline > Definition > Pipeline script from SCM
    - SCM > git, Repository URL > <your-git-repo-url>
    - Mention the GitHub credential if private, Credentials > Create a credential on jenkins for git and choose the credential id here.
    - Mention the branch name of git repo > Branches to build > Branch Specifier > */main
    - Then mention the jenkins script filename > Script Path>  Jenkinsfile
    - Finally Apply and Save the Pipeline Job
    - Then Click on Build now to Run the Pipeline


## 3. Jenkins Pipeline Stages:

  - Jenkins Pipeline have following stages
    - Declarative: Checkout SCM
    - Script Initialization
    - Load Env Variables
    - Install Dependencies
    - Black - Code Formatting
    - Flake8 - Code Linting
    - Unit Tests
    - Build Docker Image
    - Test Docker Image
    - Push Image to Docker Hub
    - Deploy to Production
    - Declarative: Post Actions


## 4. Test Your App

Open a browser and go to URL

        http://<IP>:5000                      => Welcome
        http://<IP>:5000/app                  => This is the App Section!!


## Credential need to cerate on Jenkins are following

        ID                          Kind
        ------------------------      ------------------------
        PYTHON_SSH_AGENT_KEY        Username with password
        GIT_ACCESS_TOKEN            Username with password
        DOCKER_HUB_ACCESS_TOKEN	Username with password
        MASTER_SSH_KEY	       	Secret file