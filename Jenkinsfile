pipeline {
    agent any
    environment {
        MAVEN_HOME = '/opt/homebrew/opt/maven/libexec'
        PATH = "/usr/local/bin:${MAVEN_HOME}/bin:${env.PATH}"
        IMAGE_NAME = 'saai11/ecommerce' // Change this if needed
        IMAGE_TAG = 'latest' // Or use your preferred tag
        CONTAINER_NAME = 'ecommerce_container'
        PORT = '8073' // Adjust port if needed
        VERSION = "v1.0.0" // Current release version


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
        stage('Push to ECR') {
            steps {
                script {
                    script {
                        withEnv([
                            "AWS_REGION=${env.AWS_REGION}",
                            "AWS_ACCESS_KEY_ID=${env.AWS_ACCESS_KEY_ID}",
                            "AWS_SECRET_ACCESS_KEY=${env.AWS_SECRET_ACCESS_KEY}"
                        ]) {
                            // Use double quotes and `$` syntax for shell environment variable expansion
                            sh """
                             aws ecr get-login-password --region eu-north-1 | docker login --username AWS --password-stdin 180294204151.dkr.ecr.eu-north-1.amazonaws.com

                            """

                            sh """
                                docker tag saai11/ecommerce:latest 180294204151.dkr.ecr.eu-north-1.amazonaws.com/ecommerce:latest
                            """

                            sh """
                                docker push 180294204151.dkr.ecr.eu-north-1.amazonaws.com/ecommerce:latest
                            """
                        }
                    }

                }
            }
        }
        stage('Deploy to EC2') {
            steps {
                script {
                    withEnv([
                        "AWS_REGION=${env.AWS_REGION}",
                        "AWS_ACCESS_KEY_ID=${env.AWS_ACCESS_KEY_ID}",
                        "AWS_SECRET_ACCESS_KEY=${env.AWS_SECRET_ACCESS_KEY}"
                    ]) {
                        // SSH into EC2 instance and deploy the Docker container
                     sh '''#!/bin/bash
                     echo "Logging into AWS ECR..."
                     aws ecr get-login-password --region eu-north-1 | docker login --username AWS --password-stdin 180294204151.dkr.ecr.eu-north-1.amazonaws.com/ecommerce
                     '''
                        sh '''
                                                echo "Stopping and removing existing container..."
                                                docker stop ecommerce || true
                                                docker rm ecommerce || true
                                            '''

                                            // Run a new container from the latest image
                                            sh '''
                                                echo "Running new container..."
                                                docker run -d -p 8073:8073 --name ecommerce 180294204151.dkr.ecr.eu-north-1.amazonaws.com/ecommerce:latest
                                                echo "Deployment completed!"
                                            '''

                    }
                }
            }
        }

//         stage('Deploy Docker Image') {
//             steps {
//                 script {
//                     // Stop and remove any running containers with the same name
//                     sh "docker ps -q -f name=${CONTAINER_NAME} | xargs -r docker stop | xargs -r docker rm"
//
//                     // Run the Docker container
//                     sh "docker run -d --platform linux/amd64 --name ${CONTAINER_NAME} -p ${PORT}:${PORT} ${IMAGE_NAME}:${IMAGE_TAG}"
//                 }
//             }
//         }
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
