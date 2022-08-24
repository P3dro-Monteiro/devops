FROM tomcat:8-jdk8-temurin

RUN pwd

RUN apt-get update

RUN apt-get install openjdk-8-jdk-headless -y

COPY /build/libs/reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

RUN rm -Rf /tmp/build

EXPOSE 3500