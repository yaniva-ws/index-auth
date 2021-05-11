pipeline {
    agent { label 'slave11' }

    tools { maven 'maven 3.6.3' }

    stages{
        stage ('clean and clone') {
            steps {
                cleanWs()
                git branch: 'master',
                        credentialsId: 'whitesource-github-user',
                        url: 'git@github.com:yaniva-ws/index-auth.git'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'mvn deploy'
            }
        }
    }
    post {
        always {
            junit(
                    allowEmptyResults: true,
                    testResults: '**/surefire-reports/*.xml'
            )
        }
    }
}
