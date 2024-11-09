pipeline {
    agent any
    environment {
           MAVEN_HOME = '/opt/homebrew/opt/maven/libexec'
           PATH = "${MAVEN_HOME}/bin:${env.PATH}"
            // PATH=$JAVA_HOME/bin:$PATH
        }
    stages {
        stage('Build') {
            steps {
                script {
                    // Run Maven build (change to Gradle if needed)
                    sh 'mvn clean install'
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    // Run tests (adjust command if needed)
                    sh 'mvn test'
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    // Deploy to a server or cloud (adjust as needed)
                    sh 'scp target/myapp.jar user@server:/path/to/deploy'
                }
            }
        }
    }
}