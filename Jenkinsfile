pipeline {
    agent any
    environment {
            JAVA_HOME = '/opt/homebrew/opt/openjdk/libexec/openjdk.jdk/Contents/Home'
            PATH = "${JAVA_HOME}/bin:${env.PATH}"
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