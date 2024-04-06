def loadAppEnv() {
    appenv = readJSON file: "${env.WORKSPACE}/package.json"
    return appenv
}

def installDependencies() {
    sh 'npm install'
}

def unitTest() {
    sh 'make test'
}

def codeQualityCheck() {
    def scannerHome = tool 'SonarQube-Scanner';
    withSonarQubeEnv('SonarQube') {
        sh "${tool("scannerHome")}/bin/sonar-scanner -Dsonar.settings=sonar-project.properties"
    }
}

def vulnerabilityScan() {
    sh '##'
}

def buildApp() {
    sh 'make docker-build'
}

def scanDockerImage() {
    sh '##'
}

def pushApp() {
    docker.withRegistry(env.DOCKER_REPO_URL, env.DOCKER_CRED) {
        docker.image(env.DOCKER_IMAGE_NAME).push()
    }
}

def deployApp() {
    // Log in to Docker registry using Jenkins Docker Hub credentials
    sshagent([env.MASTER_SERVER_CRED_ID]) {
        // SSH into the production server and pull the Docker image
        sh """
            ssh -o StrictHostKeyChecking=no -p ${env.PROD_SERVER_PORT} ${env.PROD_SERVER_USER}@${env.PROD_SERVER_HOST} \
                "docker rm ${env.APP_NAME} -f && docker run -p ${env.APP_PORT}:${env.APP_PORT} --name ${env.APP_NAME} -d ${env.DOCKER_IMAGE_NAME}"
        """
    }
}

def cleanLocalEnv() {
    sh 'make docker-clean'
}

def cleanProdEnv() {
    sh 'make clean'
    sh "docker images -a | grep ${env.VERSION} | awk '{print \$3}' | uniq | xargs docker rmi -f"
}

return this
