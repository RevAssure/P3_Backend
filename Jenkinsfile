pipeline{
  agent {
        dockerfile {
            filename 'Dockerfile'
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
