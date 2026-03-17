node {
    stage('Build') {
        echo "Building project..."
        sh '''
        cd "Password Protection"
        mkdir -p build
        javac -d build src/*.java
        '''
    }

    stage('Test') {
        echo "Testing stage..."
    }

    stage('Deploy') {
        echo "Deploy stage..."
    }
}
