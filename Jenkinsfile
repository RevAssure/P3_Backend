pipeline{
  agent any
  environment{
    PORT="8082"
    IMAGE_TAG="rev-assure-backend"
    CONTAINER_NAME="rev-assure-backend-app"
  }
  stages {
    stage('Testing'){
      steps{
        sh 'mvn -f pom.xml test'
      }
    }
    stage('Remove Image if exists'){
        steps {
            sh 'docker rmi -f ${IMAGE_TAG} || true'
        }
    }
    stage('Create Image'){
      steps{
        sh 'DOCKER_BUILDKIT=0 docker build -t ${IMAGE_TAG} .'
      }
    }
    stage('Remove previous container if exists'){
      steps{
        sh 'docker stop ${CONTAINER_NAME} || true && docker rm ${CONTAINER_NAME} || true' 
      }
    }
    stage('Create Conatiner'){
      steps{
        sh 'docker run -e DB_URL -e DB_USERNAME -e DB_PASSWORD -e DB_CREATE_OR_UPDATE -d --rm -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_TAG}'
        }      
      }
    }
}
    


