pipeline {
    agent any

    tools {
        // Assumes Maven is configured in Jenkins Global Tool Configuration with name 'M3'
        // If not, can rely on container if using a docker agent, but for simple setup:
        maven 'M3' 
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Skip tests here to speed up build, run them in next stage
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deployment step for Windows
                    // Assuming Tomcat is either not installed or we just archive for now.
                    // If user provides Tomcat path later, we can add a copy command.
                    // For now, we'll just echo a message.
                    bat 'echo "Deployment step: Copy target/*.war to your Tomcat webapps folder"'
                }
            }
        }
    }

    post {
        always {
            // Archive the artifacts
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
