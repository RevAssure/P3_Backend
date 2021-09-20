pipeline{
  agent {
        dockerfile {
            filename 'Dockerfile'
            label 'docker-node'
        }
    }
  stages {
    stage('Test'){
      steps{
        sh 'mvn --version'
      }
    }
    
  }
}
