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

// def testApp() {
//     docker.image(env.DOCKER_IMAGE_NAME).withRun("-p ${env.APP_PORT}:${env.APP_PORT}") { c ->
//         // Customize testing commands based on your needs
//         sh 'sleep 5'
//         sh "curl -I http://localhost:${env.APP_PORT}/"
//         sh "curl -I http://localhost:${env.APP_PORT}/app"
//         // Add more testing commands as needed
//     }
// }

// def pushApp() {
//     docker.withRegistry(env.DOCKER_HUB_REGISTRY_URL, env.DOCKER_HUB_CREDENTIALS_ID) {
//         docker.image(env.DOCKER_IMAGE_NAME).push()
//     }
// }

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