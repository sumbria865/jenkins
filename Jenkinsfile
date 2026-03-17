pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh '''
                echo "Building project..."
                cd "Password Protection"
                mkdir -p build
                javac -d build src/*.java
                '''
            }
        }

        stage('Test') {
            steps {
                sh 'echo "Testing stage..."'
            }
        }

        stage('Deploy') {
            steps {
                sh 'echo "Deploy stage..."'
            }
        }
    }
}
