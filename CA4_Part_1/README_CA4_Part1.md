<div style="text-align: center"><h1>DevOps</h1>
<h2>Class Assignment 4 - Part 1</h2>
<h2>CA4 - Containers</h2></div>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA4, we take on containers.
The proposed containerization tool for this assignment was Docker Desktop.
In this first part we will learn how to use Docker, an containerization tool that is used to setup a containerization environment.
It has some automation characteristics too since we can use a Dockerfile to set up a container the same way we did with a Vagrantfile<br>
The purpose of this assignment is to create a image for running a container with our chart server from CA2_Part_1. We will
be doing so by cloning our repository inside the container and running the chat server(v1), and, with the compiled .jar file
directly(v2).
</div>

<br>

---

## Analysis
<div style="text-align: justify">

As we can see from the image bellow, unlike virtual machines, containers don't need a virtualized operating system neither
virtualized hardware, because it runs directly on the host operating system:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Report\Cointainers_Scheme.png"  alt="Container Infrastructure">-->
<img src="/imgs/CA4/Report/Cointainers_Scheme.png"  alt="Container Infrastructure">
<br>

[Source](https://www.docker.com/resources/what-container/)

The container makes use of direct system-calls to the underling operating system, but is able to use resources only within the
container. The container’s operating system has its own filesystem, its own processes and memory and uses its own devices that are simulated by
the container. For instance, regarding network adapters containers also use virtual switches the very same way virtual machines do.
"A container is a standard unit of software that packages up code and all its dependencies so the application runs quickly 
and reliably from one computing environment to another. A Docker container image is a lightweight, standalone, executable 
package of software that includes everything needed to run an application: code, runtime, system tools, system libraries and settings.
Container images become containers at runtime and in the case of Docker containers – images become containers when they 
run on Docker Engine. Available for both Linux and Windows-based applications, containerized software will always run 
the same, regardless of the infrastructure. Containers isolate software from its environment and ensure that it works 
uniformly despite differences for instance between development and staging." [Source](https://www.docker.com/resources/what-container/) <br>
This kind of tools are very used and useful, specially when applying microservices. As we know microservices devide monoliths
into small pieces of software that run together but independently of the rest. Containers play a big part for allowing that.

<br>

| Advantages  | Disadvantages  |
|:---:|:---:|
| Provide a lightweight, fast, and isolated infrastructure to run your applications. They are more flexible and can be backed up and restored faster.  |  Works well for Linux operating systems. While Windows has a container environment, it is not supported nearly as much as the Linux environments. |
| The application, dependencies, libraries, binaries, and configuration files are usually bundled into the container, providing an easy solution to migrating your application anywhere. | Containers share the kernel of the OS, so if the kernel becomes vulnerable, all of the containers will be vulnerable, as well.  |
| The average container size is usually less than 100MB, as opposed to a couple of gigabytes used by a virtual machine.  | Networking can be tricky when you’re working with containers. You have to maintain a good network connection while actively trying to keep containers isolated.  |
| Containers can help decrease your operating and development cost.  | Containerization is usually used to build multi-layered infrastructure, with one application in one container. This means you have to monitor more things than you would if running all of your applications on one virtual machine. |

[Source](https://spin.atomicobject.com/2019/05/24/containerization-pros-cons/)

<br>

There are two main differences between the two approaches developed in this assignment. When using git we always guarantee
that the container can be run in every machine that has docker installed, because since we are cloning the repository
and build the project inside the container there are no compatibilities issues since the application is built using the
java version installed in the container. On the other hand container image size will be larger than the alternate solution
since we clone a full repository. <br>
The reverse happens when we use the .jar file directly. We will have a smaller container image, but, since we build our
application outside the container we have to compile it with the same version installed on the container, otherwise we can
have compatibilities issues.

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Container_Size.png"  alt="Container Size">-->
<img src="/imgs/CA4/Part_1/Container_Size.png"  alt="Container Size">

</div>

---

<div style="text-align: center"><h2>Implementation - Containerization With Docker</h2></div>

---

## Step 1 - Installing Docker Desktop

<div style="text-align: justify">
First we must download and install Docker in our computer:

<br>

```
https://www.docker.com/products/docker-desktop/
```

<br>

After installation, we should check if docker was correctly installed. In a terminal we can check its version using:

<br>

```
docker -v** or **docker --version
```

<br>

## Step 2 - Creating Docker Image - v1 using Git [3752bc6](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/3752bc6ba6a2f3a23da3dcc44db101a4412099b7)

<br>

Docker images are cousins of box images that were used in CA3 for virtualization purposes. They are used to executing code in a container,
and we can see them as templates of the containers. <br>
This time we are going to create the image ourselves using a Dockerfile. In this file we can declare a set of instructions
in order to create our container image. The most common commands used are listed bellow: 

<br>

```
ADD  |  The ADD instruction copies new files, directories or remote file URLs from <src> and adds them to the filesystem of the image at the path <dest>. 

COPY  |  The COPY instruction copies new files or directories from <src> and adds them to the filesystem of the container at the path <dest> 

CMD  |  There can only be one CMD instruction in a Dockerfile. If you list more than one CMD then only the last CMD will take effect.   

ENTRYPOINT  |   An ENTRYPOINT allows you to configure a container that will run as an executable.  

ENV  |    The ENV instruction sets the environment variable <key> to the value <value>. This value will be in the environment for all subsequent instructions in the build stage and can be replaced inline in many as well. The value will be interpreted for other environment variables, so quote characters will be removed if they are not escaped. Like command line parsing, quotes and backslashes can be used to include spaces within values. 

EXPOSE  |  The EXPOSE instruction informs Docker that the container listens on the specified network ports at runtime. You can specify whether the port listens on TCP or UDP, and the default is TCP if the protocol is not specified.   

FROM  |  The FROM instruction initializes a new build stage and sets the Base Image for subsequent instructions. As such, a valid Dockerfile must start with a FROM instruction. The image can be any valid image – it is especially easy to start by pulling an image from the Public Repositories.   

RUN  |  The FROM instruction initializes a new build stage and sets the Base Image for subsequent instructions. As such, a valid Dockerfile must start with a FROM instruction. The image can be any valid image – it is especially easy to start by pulling an image from the Public Repositories.   

VOLUME  |  The VOLUME instruction creates a mount point with the specified name and marks it as holding externally mounted volumes from native host or other containers. 

WORKDIR  |   The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile. If the WORKDIR doesn’t exist, it will be created even if it’s not used in any subsequent Dockerfile instruction.  
```

<br>

[Source](https://docs.docker.com/engine/reference/builder/)

<br>

This list of commands will be enough to complete the current assignment. <br>
As was stated before the purpose is to run our chat application using a container. More specifically we want to create a container
to run our chat server inside then connect to it from the host machine. The chat server application has three main dependencies,
gradle to be built, java to be run and Git to download my remote repository and run the chat server. Note that installing
gradle on the container it is not necessary since we can use gradle wrapper directly<br>
First we have to choose the operating system that we want to use. I will be using Ubuntu.

<br>

```
#FROM initializes a new build stage and sets the base for image creation, in this case ubuntu
FROM ubuntu

#With the run instruction we can execute commands inside docker, in this case BASH commands

#It's a common good practice to update the system before installation 
RUN apt-get update

#Then we will install java 11 for running the application.
RUN apt-get install openjdk-11-jdk-headless -y

#We should install git so we can clone my remote repository
RUN apt-get install git -y

#This command will clone my local repository to the container
RUN git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git

#WORKDIR states that from this line forward our working directory will be the one passed as argument 
WORKDIR /devops-21-22-lmn-1211790/CA2_Part_1/gradle_basic_demo

#This command grants execute permission to the user gradle wrapper file
RUN chmod u+x ./gradlew

#This command builds the gradle project
RUN ./gradlew build

#EXPOSE states that the container is "listening" to a specific port. That means that we can communicate with our container through this port
EXPOSE 59005

#CMD is a very important command, it can be declared only once per Dockerfile, with it we can set a command to be executed everytime our container starts
#In this case we want to execute the server when the container is started
CMD ./gradlew runServer
```

<br>

After creating the Dockerfile we have to build our image using:

<br>

```
docker build [OPTIONS] PATH | URL | -

(https://docs.docker.com/engine/reference/commandline/build/)
```

<br>

This command will start creating the image creation process by doing the tasks that I scripted in the Dockerfile.
We can see the list of images created using:

<br>

```
docker images
```

<br>

We can also use docker desktop GUI as shown above.

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Images_List.png"  alt="Images List">-->
<img src="/imgs/CA4/Part_1/Images_List.png"  alt="Images List">

## Step 3 - Creating Docker Image - v2 using .jar file [3752bc6](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/3752bc6ba6a2f3a23da3dcc44db101a4412099b7)

The alternative solution is to build the image externally and then copy the compiled .jar file to the container to run the chat server.
With this approach we have to be careful to build the image file with the same version of java that we installed inside 
the container.

<br>

```
#FROM initializes a new build stage and sets the base for image creation, in this case ubuntu
FROM ubuntu

#It's a common good practice to update the system before installation 
RUN apt-get update

#Then we will install java 11 for running the application.
RUN apt-get install openjdk-11-jdk-headless -y

#COPY copies a file or folder from a selected path from the host, to a path from the container.
COPY basic_demo-0.1.0.jar /

#EXPOSE states that the container is "listening" to a specific port. That means that we can communicate with our container through this port
EXPOSE 59005

#CMD is a very important command, it can be declared only once per Dockerfile, with it we can set a command to be executed everytime our container starts
#In this case we want to execute the server when the container is started
CMD java -cp basic_demo-0.1.0.jar basic_demo.ChatServerApp 59005
```

<br>

After creating the Dockerfile we have to build our image using:

<br>

```
docker build [OPTIONS] PATH | URL | -

(https://docs.docker.com/engine/reference/commandline/build/)
```

<br>

This command will start creating the image creation process by doing the tasks that I scripted in the Dockerfile.

<br>

## Step 4 - Running Docker Container From Created Images [e00f7bf](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/e00f7bf8a2617b58523e1bc25c8942937a458486)

<br>

Now that we have created our images we can run the containers using them. We just have to use the following command:

<br>

```
docker run [OPTIONS] IMAGE [COMMAND] [ARG...]

(https://docs.docker.com/engine/reference/commandline/run/)
```

<br>

We want to communicate from the host machine with the container and if we don't want to know the ip address from the
container we can connect the same port from the host to the container through the -p option:

<br>

```
--publish or -p	(Publish a container's port(s) to the host)

docker run -p 59005:59005 <image name>
```

<br>

After that we could see that our Dockerfile was correctly scripted since the chat server is running without errors from
both versions:

<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Connected_Host_Container_V1.png"  alt="Chat Connection">-->
<img src="/imgs/CA4/Part_1/Connected_Host_Container_V1.png"  alt="Chat Connection">
<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Connected_Host_Container_V2.png"  alt="Chat Connection">-->
<img src="/imgs/CA4/Part_1/Connected_Host_Container_V2.png"  alt="Chat Connection">

<br>

## Step 5 - Docker Image Tagging and Docker Hub Upload [a7f1829](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a7f1829aae77967075a16d77cd9d0c45139bd5ee)

<br>

Docker provides a repository service for users registered in Docker Hub. We can create one to push our images to docker hub
and make them available to everyone. This is very useful if we want to share a docker image with other people.

<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Docker_Hub.png"  alt="Docker Repository">-->
<img src="/imgs/CA4/Part_1/Docker_Hub.png"  alt="Docker Repository">

<br>

If we want to push an image to docker hub we can use the following command:

<br>

```
docker push [OPTIONS] NAME[:TAG]

(https://docs.docker.com/engine/reference/commandline/push/)
```

<br>

In this case:

<br>

```
docker push pmonteiro1211790/devops-21-22-lmn-1211790:tagname
```

<br>

We have to use the image name to reference it. If we list all the images we can see the image name:

<br>

```
docker images
```

<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Images_List.png"  alt="Images List">-->
<img src="/imgs/CA4/Part_1/Images_List.png"  alt="Images List">

<br>

We should tag the images before pushing them to the repository. We can do that with the following command:

<br>

```
docker tag SOURCE_IMAGE[:TAG] TARGET_IMAGE[:TAG]

(https://docs.docker.com/engine/reference/commandline/tag/)
```

<br>

Int his case:

<br>

```
docker tag cdf191a642e3 pmonteiro1211790/devops-21-22-lmn-1211790:v1
docker tag 572830295579 pmonteiro1211790/devops-21-22-lmn-1211790:v2
```

<br>

And we can see the new tagged images on the docker image list:

<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Images_List_With_Tag.png"  alt="Images List With Tag">-->
<img src="/imgs/CA4/Part_1/Images_List_With_Tag.png"  alt="Images List With Tag">

<br>

To conclude CA4_Part_1 we just have to push the images to docker hub repository.

<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA4\Part_1\Images_Pushed.png"  alt="Images List With Tag">-->
<img src="/imgs/CA4/Part_1/Images_Pushed.png"  alt="Images List With Tag">

<br>

</div>

---

<div style="text-align: center"><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></div>

---




