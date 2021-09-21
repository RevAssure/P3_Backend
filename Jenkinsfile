pipeline{
  agent {
        dockerfile true
    }
  stages {
    stage('Testing'){
      steps{
        sh 'chmod a+x ./P3_Backend/mvnw'
        sh './P3_Backend/mvnw test'
      }
    }
    stage('Build'){
      steps{
        sh 'DOCKER_BUILDKIT=0 docker build -t p3_backend .'
      }
    }
    stage('Run in EC2 instance'){
      steps{
        sh 'docker run -e DB_URL -e DB_USERNAME -e DB_PASSWORD p3_backend -p 8082:8082'
        }      
      }
    }
    
  }
}
