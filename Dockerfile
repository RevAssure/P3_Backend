#
# build stage
#
FROM maven:3.8.2-jdk-8 AS build
WORKDIR app
COPY src app/src
COPY pom.xml app
RUN mvn -f app/pom.xml clean package
ENTRYPOINT ["java","-jar","app/target/RevAssure-0.1.jar"]