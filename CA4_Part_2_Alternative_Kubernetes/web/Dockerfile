#WEB_CONTAINER

FROM tomcat:8-jdk8-temurin

RUN apt-get update -y 

RUN apt-get install sudo nano git nodejs npm -f -y

RUN apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /tmp/build

WORKDIR /tmp/build/

COPY reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /tmp/build/

RUN cp ./reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/

RUN rm -Rf /tmp/build/

EXPOSE 8080