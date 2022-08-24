<div style="text-align: center">

# DevOps
# Class Assignment 4 - Part 2
# CA4 - Containers With Automation

</div>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA4, we take on containers.
The proposed containerization tool for this assignment was Docker Desktop. After we learned how to use Docker we take on
a automation tool to deploy several containers. This will be similar to CA3_Part_2 but with containers. We will be using
docker compose, "a tool for defining and running multi-container Docker applications". This is the equivalent of Vagrant
but this time for containers. We will be using a .yaml file were we will write our instructions for building and deploying
our containers.
As usual we will be researching and applying an alternative to docker compose. In this case it is not a "direct "alternative
instead a similar technology. We will be diving into kubernetes to implement the same tasks of this assignment.

[docker compose](https://docs.docker.com/compose/)

<br>

[kubernetes](https://kubernetes.io/)

</div>

<br>

---

## Analysis

<div style="text-align: justify">

As mentioned above we will be using docker compose to deploy CA2_Part_2 project in two containers. One with the database
and another with de web server. First we will be scripting a Dockerfile, for each, to build our container images and 
using a .yaml, can also be named as .yml, file to build and deploy our containers. "YAML is a digestible data serialization 
language often used to create configuration files with any programming language.
Designed for human interaction, YAML is a strict superset of JSON, another data serialization language. But because it�s
a strict superset, it can do everything that JSON can and more. One major difference is that newlines and indentation actually
mean something in YAML, as opposed to JSON, which uses brackets and braces. The format lends itself to specifying configuration,
which is how we use it at CircleCI." [Source](https://circleci.com/blog/what-is-yaml-a-beginner-s-guide/) <br>
For networking, we will be using a bridge network.
It is a private network within a docker host, in our case Docker Desktop, were we can connect several containers to communicate
with each other. This private network will communicate with the external world through NAT(Network Address Translation).
We can create a bridge network manually using docker:

```
docker network create <network-name>
```

Add a container to the network:
```
docker network connect <network-name> <container-id>
```

Add a container to the network when you run it:
```
docker run --network <network-name> <image-id>
```

List networks:
```
docker network ls
```

At the end of the assignment we will be doing the same with kubernetes. "Kubernetes, also known as K8s, is an open-source
system for automating deployment, scaling, and management of containerized applications. It groups containers that make 
up an application into logical units for easy management and discovery." It is a perfect tool for microservices management.
Nowadays, it is the market norm. <br> 
The implementation of the alternative was made with the help of Mr. [Rob Rich](https://github.com/robrich/kubernetes-hands-on-workshop).

In kubernetes the larger component is called a cluster. A kubernetes cluster is made by nodes and within nodes we have
our pods that are the smallest component in the kubernetes eco-system. Inside each pod we can have one or more containers.
With this architecture in mind kubernetes is used to deploy SaS in cloud systems, and it is a pillar for many software companies.
We will not dive in depth into kubernetes since our application is very simple, and I lack the skills to develop
something that would explore the full capacities of kubernetes. Alas, kubernetes uses also a .yaml configuration file to
construct its components, clusters, nodes and pods. We will be using them for deploying a database pod and a webserver pod.

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Report\k8_components.png">
</div>-->


<div style="text-align: center">
<img src="/imgs/CA4/Report/k8_components.png">
</div>

[Source](https://www.youtube.com/watch?v=5cNrTU6o3Fw&t=662s)

</div>

---

<div style="text-align: center"><h2>Implementation - Multi Container Environment With Docker Compose</h2></div>

---

## Step 1 - Create Dockerfile for Container Image Creation([46cdc72](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/46cdc721c55903aa227fde5290669dc27c6139e6))

<div style="text-align: justify">
First we must create our containers to be deployd. I created two folder at CA4_Part_2, one for each container image, db and web.
At this folders Dockerfiles were created to create the needed container images.
The database was deployed using an ubuntu image and for running the database we needed, java 8, unzip to be able to use the 
.jar file and wget to download the file from a http server. Ports 8082 and 9092 were exposed and the java command to run
the server Is always run by the CMD instruction(See CA4_Part_1).

<br>

```
#DB_CONTAINER

FROM ubuntu

RUN apt-get update && \
apt-get install -y openjdk-8-jdk-headless && \
apt-get install unzip -y && \
apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD java -cp ./h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

<br>

The web server image was based in a tomcat ready image (tomcat:8-jdk8-temurin) and we used the git strategy. I cloned my
personal repository to the container to build de application and deploy the .war file to the server by coping it to
/usr/local/tomcat/webapps/. In this case we don't need a CMD command to run the server because the tomcat image that was 
chosen to build this image runs the server automatically:

<br>

```
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
```

After this we are ready to write the docker-compose.yml file to automate our deployment.

</div>

## Step 2 - Create docker-compose.yml for Container Image Deployment([7abd21e](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/7abd21ec4ec59b48232f5d94355d100ec7499b0c))

<div style="text-align: justify">

With docker-compose, every container is referred as a service, so we will have a database service and a web server service.
We can define the services name using the **services** and **build** keywords:

```
services:
  web:
    build: web
```

There are three main aspects that we have to consider, in this application case. First since we have the dockerfiles written,
so we don't have to be worried about how the docker images are going to be built, that part is the Dockerfile responsibility.
Secondly it is compose responsibility to define the [networking](https://www.freecodecamp.org/news/how-to-get-a-docker-container-ip-address-explained-with-examples/) 
for the containers. If we don't define the network in the configuration file docker compose will define a bridge network 
by default. It is possible to know the services ip addresses but, I would recommend dictating the network ourselves, 
since by principle it is better to be in "control" of the machine than to be "controlled" by the machine. We can see the 
ip addresses definition under the **networks** keyword:

```
networks:
      default:
        ipv4_address: 192.168.56.10
```

On the other hand
we saw that we could tell which host port would be "listening" to which container port by using the **-p** option:

```
docker run -p 59005:59005 <image name>
```

Docker compose can save us some work here, we can define this using the **ports** keyword:

```
ports:
      - "8080:8080"
```

Thirdly, we can set up a **volume** shared by the host and the container to persist data on the hosts hard drive. For that
we must dictate the "from" and "to" folder:

```
volumes:
      - ./data:/usr/src/data-backup
```

We can see the assembled **docker-compose.yml** bellow:

```
version: '3'
services:
  web:
    build: web
    ports:
      - "8080:8080"
    networks:
      default:
        ipv4_address: 192.168.56.10
    depends_on:
      - "db"
  db:
    build: db
    ports:
      - "8082:8082"
      - "9092:9092"
    volumes:
      - ./data:/usr/src/data-backup
    networks:
      default:
        ipv4_address: 192.168.56.11
networks:
  default:
    ipam:
      driver: default
      config:
        - subnet: 192.168.56.0/24
```

<br>

Now we are ready to build and deploy our containers. We can do both with a single command, that builds images from Dockerfiles,
pulls images from registries, creates and starts containers, configures the bridge network for the containers and handles logs.
All in accordance with the **docker-compose.yml**:

```
docker-compose up
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\containers_up.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/containers_up.png">
</div>

There are many other commands, here I leave a few and more relevant that I used to solve the assignment:

**Build the images:**
```
docker-compose build
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\build.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/build.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\images_list.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/images_list.png">
</div>

**Start/Stop the containers:**
```
docker-compose start/stop <service>
```

It is worth mentioning that after building the images I couldn't start the containers and got a IPV4 error. After some
debugging I discovered that I had a network occupying the network and used prune option to remove unused docker networks.
This fixed the issue and was able to deploy the container images running the database and web server.

```
docker network prune
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\error_IPV4.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/error_IPV4.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\container_stop.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/container_stop.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\webUP.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/webUP.png">
</div>

With them deployed we could access the front end and back end from our browser. <br>
It is worth mentioning that since we are using docker in the h2 console we can use the alias of the image "db" instead
of the ip address:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\h2_console.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/h2_console.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\frodo.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/frodo.png">
</div>

[Source](https://docs.docker.com/compose/reference/overview/)

</div>

## Step 3 - Push Created Container Images to Docker Hub ([7abd21e](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/7abd21ec4ec59b48232f5d94355d100ec7499b0c))

<div style="text-align: justify">

This step was covered previously at CA4_Par1. The process was the same, tagging the images and pushing them afterwards:

```
docker tag ca4_part_2_web pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_web

docker tag ca4_part_2_db pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_db

docker push pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_web

docker push pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_db
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\pushed_images.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/pushed_images.png">
</div>

</div>

## Step 4 - Use Created Volume to Back Up the Database  ([dc8da6b](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/dc8da6b315a84ac0e0cb8be028ccb13a8df22a8d))

<div style="text-align: justify">

We know now that a volume is a shared folder between the host and the container, and if we want to back up something from
the container we just have to copy it to the shared folder location inside the container then it will be available in our
machine hard drive. For that we have to start the container and open a ssh connection with the container:

```
docker-compose exec db bash
```

and use the copy command to copy the database to the shared folder:

```
cp jpadb.mv.db . /usr/src/data-backup
```

Then the data folder is created with the database file inside:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2\copied_db.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2/copied_db.png">
</div>

This concludes the CA4_Part_2 assignment. Now we will be doing the same with kubernetes.

</div>

---

<div style="text-align: center"><h2>Implementation - Multi Container Environment With Kubernetes</h2></div>

---

## Step 1 - Create Dockerfile for Container Image Creation([9f2e2fa](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9f2e2fac5e05944d595b9377ee697adc6c353df3))

<div style="text-align: justify">

For this step we will be using the same Dockerfiles for the db and we containers images:

**db Dockerfile**

```
#DB_CONTAINER

FROM ubuntu

RUN apt-get update && \
apt-get install -y openjdk-8-jdk-headless && \
apt-get install unzip -y && \
apt-get install wget -y

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app/

RUN wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar

EXPOSE 8082
EXPOSE 9092

CMD java -cp ./h2-1.4.200.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists
```

**web Dockerfile**

```
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
```

</div>

## Step 2 - Create service.yml and deployment.yml for Container Image Deployment ([9f2e2fa](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9f2e2fac5e05944d595b9377ee697adc6c353df3))

<div style="text-align: justify">

Kubernetes is a container orchestration tool made to replace docker-compose and docker-swarm, but it can not live without docker. 
With kubernetes we still be using docker to build our images and kubernetes to deploy them in the way we want to.<br>
We will be defining two pods, one with db container and the other with web container. We will be writing a service.yml that
is responsible for defining the image scope and networking. And the deployment.yml defines the desired container state.
Each container will have a service.yml and a deployment.yml. [Source](https://robrich.org/slides/kubernetes-test-drive/#/)
With the service.yml files we name the service dictate the ports to be exposed and set the type of the service. In this case
I used NodePort type, that means that we will have "a public single port per worker node(machine)". <br>
While with the deployment.yml file we can dictate how many instances of a pod(replicas) we want, which image to use and
the port to be exposed. **(Note that several experiments were made to complete the alternative assignment, some images
bellow can differ in values of node ports, ip addresses and images hash due to the fact that kubernetes was stopped and
restarted several times)**

**For the web container, service.yml**

```
apiVersion: v1
kind: Service
metadata:
  name: web
spec:
  type: NodePort
  selector:
    app: webserver
  ports:
    - port: 8080
      targetPort: 8080
      name: port1
```

**For the web container, deployment.yml**

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: web
spec:
  replicas: 1
  selector:
    matchLabels:
      app: webserver
  template:
    metadata:
      labels:
        app: webserver
        version: v0.1
    spec:
      containers:
        - name: webserver
          image: pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_webv2
          ports:
            - containerPort: 8080
```

**For the db container, service.yml**

```
apiVersion: v1
kind: Service
metadata:
  name: db
spec:
  type: NodePort
  selector:
    app: database
  ports:
    - port: 8082
      targetPort: 8082
      name: port1
    - port: 9092
      targetPort: 9092
      name: port2
```

**For the db container, deployment.yml**

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: db
spec:
  replicas: 1
  selector:
    matchLabels:
      app: database
  template:
    metadata:
      labels:
        app: database
        version: v0.1
    spec:
      containers:
        - name: database
          image: pmonteiro1211790/devops-21-22-lmn-1211790:ca4_part_2_db
          ports:
            - containerPort: 8082
            - containerPort: 9092
```

Now that we have the files ready we can deploy our containers. If we hadn't built our images previously we would have to
do it at this stage before using kubernetes:

```
dcoker build <image name>
```

Since we are using the same images we don't need to build them again. At the root of web and db folders we have to use the following
[command](https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands#apply) to deploy our containers:

```
kubectl apply -f deployment.yml

kubectl apply -f service.yml
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\service_created.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/service_created.png">
</div>

After this we can check our pods status with:

```
kubectl get all
```

This is a very useful command since we can see the cluster ip addresses, the host ports that are listening to pods exposed
ports and the current status:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\get_all.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/get_all.png">
</div>

There is an even more powerful command that show more details of the pods:

```
kubectl describe pods

or

kubectl describe services
```

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\pod_description.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/pod_description.png">
</div>

Now we just have to connect to the ports that we checked through **kubectl get all** to see if the connection is established 
has expected:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\h2_console_kubernetes.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/h2_console_kubernetes.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\frodo_kubernetes.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/frodo_kubernetes.png">
</div>

Unfortunately for the webserver part I could not see the webpage for some reason that I could not figure it out. Either way
I will describe the debug process that I have done to fix this issue.

</div>

## Step 3 - Debugging Deployd Kubernetes Pods  ([9f2e2fa](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9f2e2fac5e05944d595b9377ee697adc6c353df3))

<div style="text-align: justify">

We could establish a connection through http://localhost:<port> to the database and to the web server, the only difference 
was that we could not see the page. My first thought was to look for .war file deployment, then I checked the logs to see
if an error has occurred:

```
kubectl logs <pod name>
```

But no error has occurred and the .war file was apparently successfully deployed:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\server_logs.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/server_logs.png">
</div>

Then I entered through ssh into the containers to check for problems.
We can ssh using kubernetes with the following command:

```
kubectl exec --stdin --tty <pod name> -- /bin/bash
```

With this I noticed two things. First the db pod didn't have the database file to deploy information. But this
was not related to the web server problem. On the other hand the shared volume was not working has well, since I tried 
to copy a file to the defined path and nothing happened. Probably should have defined a Volume in the deployment or service
.yml files.

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\empty_db.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/empty_db.png">
</div>

For the web server I checked two things, first if the .war file was present at **/usr/local/tomcat/webapps** and the logs 
from the tomcat server at **/usr/local/tomcat/logs**. <br>
The .war file was present, the main application class was detected and the logs showed that they received a get and return a 404.
The only thing that I found odd was the ip address that the GET was made to. It was not the same showed at the pods ip table.
Maybe this was the problem:

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\war_file.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/war_file.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\tomcat_logs.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/tomcat_logs.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\class_detected.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/class_detected.png">
</div>

<!---<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_2_Kubernetes\404_log.png">
</div>-->

<div style="text-align: center">
<img src="/imgs/CA4/Part_2_Kubernetes/404_log.png">
</div>

At this point I chose to conclude this report since I ended up "wasting" too much time debugging and googling without
success. My current knowledge it is not enough to solve this issue, but, It was a good experience learning a bit about
how kubernetes and docker empower software out there.

</div>

---

<div style="text-align: center"><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></div>

---