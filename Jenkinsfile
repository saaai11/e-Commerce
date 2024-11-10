pipeline {
    agent any
    environment {
        MAVEN_HOME = '/opt/homebrew/opt/maven/libexec'
        PATH = "/usr/local/bin:${MAVEN_HOME}/bin:${env.PATH}"
        IMAGE_NAME = 'saai11/ecommerce' // Change this if needed
        IMAGE_TAG = 'latest' // Or use your preferred tag
        CONTAINER_NAME = 'ecommerce_container'
        PORT = '8074' // Adjust port if needed
    }
    stages {
        stage('Build') {
            steps {
                script {
                    // Run Maven build
                    sh 'mvn clean install'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Run tests
                    sh 'mvn test'
                }
            }
        }
        stage('Check Docker') {
            steps {
                script {
                    sh 'docker --version'
                }
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    sh "docker rmi saai11/ecommerce:latest || true"
                    sh "mvn spring-boot:build-image -Dspring-boot.build-image.imageName=${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
        stage('Deploy Docker Image') {
            steps {
                script {
                    // Stop and remove any running containers with the same name
                    sh "docker ps -q -f name=${CONTAINER_NAME} | xargs -r docker stop | xargs -r docker rm"

                    // Run the Docker container
                    sh "docker run -d --platform linux/amd64 --name ${CONTAINER_NAME} -p ${PORT}:${PORT} ${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
    }
    post {
        always {
            // Clean up: stop and remove the container after the build
            script {
sh "docker ps -aq -f name=${CONTAINER_NAME} | xargs -r docker stop | xargs -r docker rm"
            }
        }
    }
}
