FROM ubuntu

RUN apt-get update
RUN apt-get install openjdk-11-jdk-headless -y
RUN apt-get install git -y
RUN git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git

WORKDIR /devops-21-22-lmn-1211790/CA2_Part_1/gradle_basic_demo

RUN chmod u+x ./gradlew

RUN ./gradlew build

EXPOSE 59005

CMD ./gradlew runServer