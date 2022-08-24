FROM ubuntu

RUN apt-get update
RUN apt-get install openjdk-11-jdk-headless -y

COPY basic_demo-0.1.0.jar /

EXPOSE 59005

CMD java -cp basic_demo-0.1.0.jar basic_demo.ChatServerApp 59005