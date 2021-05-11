pipeline {
    agent { label 'slave11' }

    tools { maven 'maven 3.6.3' }

    environment{
        PATH_2_POM = './jwt-utils'
    }

    stages{
        stage ('clean and clone') {
            steps {
                cleanWs()
                git branch: 'main',
                        credentialsId: 'whitesource-github-user',
                        url: 'git@github.com:yaniva-ws/index-auth.git'
            }
        }
        stage('Build') {
            steps {
                sh 'cd ${PATH_2_POM} && mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'cd ${PATH_2_POM} && mvn test'
            }
        }
        stage('Deploy') {
            steps {
                sh 'cd ${PATH_2_POM} && mvn deploy'
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
