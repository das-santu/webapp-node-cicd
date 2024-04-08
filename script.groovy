def loadAppEnv() {
    appenv = readJSON file: "${env.WORKSPACE}/package.json"
    return appenv
}

def installDependencies() {
    sh 'npm install'
}

def unitTest() {
    sh 'npm test'
}

def codeAnalysis() {
    // def scannerHome = tool 'Sonar-Scanner';
    withSonarQubeEnv('Sonar-Server') {
        sh "${tool("Sonar-Scanner")}/bin/sonar-scanner -Dsonar.settings=sonar-project.properties"
    }
}

def dependencyCheck() {
    dependencyCheck additionalArguments: ''' 
        -o './'
        -s './'
        -f 'ALL' 
        --prettyPrint''', odcInstallation: 'DP-Check'
    dependencyCheckPublisher pattern: 'dependency-check-report.xml'
}

def buildAppImage() {
    sh 'make docker-build'
}

def scanAppImage() {
    sh "trivy image ${env.DOCKER_IMAGE_NAME}"
}

def pushAppImage() {
    withCredentials([usernamePassword(credentialsId: 'nexus_creds', passwordVariable: 'pass', usernameVariable: 'user')]) {
          sh "docker login ${NEXUS_DOCKER_REPO} -u ${env.user} -p ${env.pass}"
          sh "docker push ${env.DOCKER_IMAGE_NAME}"
        }
}

// def deployApp() {
//     // Log in to Docker registry using Jenkins Docker Hub credentials
//     sshagent([env.MASTER_SERVER_CRED_ID]) {
//         // SSH into the production server and pull the Docker image
//         sh """
//             ssh -o StrictHostKeyChecking=no -p ${env.PROD_SERVER_PORT} ${env.PROD_SERVER_USER}@${env.PROD_SERVER_HOST} \
//                 "docker rm ${env.APP_NAME} -f && docker run -p ${env.APP_PORT}:${env.APP_PORT} --name ${env.APP_NAME} -d ${env.DOCKER_IMAGE_NAME}"
//         """
//     }
// }

// def cleanEnv() {
//     sh 'make clean'
//     sh "docker images -a | grep ${env.VERSION} | awk '{print \$3}' | uniq | xargs docker rmi -f"
// }

return this