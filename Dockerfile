FROM maven:3.8.2-jdk-8
WORKDIR app
COPY src src
COPY pom.xml .
EXPOSE 8082
RUN mvn -f pom.xml clean package -DskipTests=true
CMD ["java","-jar","target/RevAssure-0.1.jar"]
