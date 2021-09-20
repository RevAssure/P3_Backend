pipeline{
  echo 'hello'
  agent {
        echo 'hello'
        dockerfile true
    }
  echo 'hello'
  stages {
    stage('Test'){
      steps{
        echo 'hello'
        sh 'mvn --version'
      }
    }
    
  }
}
