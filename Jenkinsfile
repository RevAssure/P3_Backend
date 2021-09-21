pipeline{
  agent any
  environment{
    PORT="8082"
    IMAGE_TAG="RevAssure-backend"
    CONTAINER_NAME="RevAssure-backend-app"
  }
//   stages {
//     stage('checkout'){
//       steps{
//         git branch: 'devops' url:'https://github.com/RevAssure/P3_Backend.git'
//       }
//     }
//     stage('Testing'){
//       steps{
//         sh 'chmod a+x ./P3_Backend/mvnw'
//         sh './P3_Backend/mvnw test'
//       }
//     }
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
        sh 'docker stop ${CONTAINER_NAME} || true'
      }
    }
    stage('Create Conatiner'){
      steps{
        sh 'docker run -e DB_URL -e DB_USERNAME -e DB_PASSWORD -p ${PORT}:${PORT} --name ${CONTAINER_NAME} ${IMAGE_TAG}'
        }      
      }
    }
    


