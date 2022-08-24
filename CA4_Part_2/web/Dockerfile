#WEB_CONTAINER

FROM tomcat:8-jdk8-temurin

RUN apt-get update -y 

RUN apt-get install sudo nano git nodejs npm -f -y

RUN apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /tmp/build

WORKDIR /tmp/build/

RUN git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git

WORKDIR /tmp/build/devops-21-22-lmn-1211790/CA2_Part_2

RUN chmod u+x gradlew

RUN ./gradlew clean build

RUN cp build/libs/reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ 

RUN rm -Rf /tmp/build/

EXPOSE 8080