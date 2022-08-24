<center><h1>DevOps</h1></center>
<center><h2>Class Assignment 3 - Part 1</h2></center>
<center><h2>CA3 - Virtualization</h2></center>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA3, we take on virtualization.
The proposed virtualization tool for this assignment was VirtualBox. <br>
We aim to learn the utility of virtualization and learn how to run previous projects on a virtual machine, that will simulate
a remote virtual server role. <br>
This report corresponds to the first part of the assignment.
</div>

---

## Analysis
<div style="text-align: justify">

Servers are machines dedicated to providing services, the majority of them by the means of networking. 
A wide variety of servers use Linux, because they are very stable and allow that many services and resources can be added.
Nowadays, there are few servers that run on physical hardware, that is, they access directly to the machine hardware. The norm
is for it to access virtualized hardware. That can be achieved with a hypervisor. <br>
Hypervisors are software that emulate other computers and its various hardware devices. They can run within a host operating system
or even without one:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\hypervisors.png" alt="drawing" width="600"/>-->

<img src="/imgs/CA3/Part1/hypervisors.png" alt="drawing" width="600">

That said, virtual machines are simulated computers. These tools are commonly used because they bring some advantages with them, 
for example, administration benefits. We can easily add or remove RAM memory to any virtual machine since hardware is virtualized
we can do it through software, create a clone of that virtual machine and deploy it has many times has we see fit, and,
we can create snapshots of a virtual machine state and then roll back to it if we want, for example, if we need to test
something and the return to previous snapshoted state. On the other hand we have efficiency benefits, more evident in big
datacenters, were there are manny servers. Before virtualization a server could be oversized for that specific task, that is,
not all its hardware is being used for that task, and then we are wasting computing capacity when executing smaller tasks.
This is where virtualization comes in handy. We can tell how many resources we want virtualized for a specific task, and then,
free other resources to other tasks to be done simultaneously and utilize of all resources available of that machine.
On this assigment we will be using VirtualBox. <br>
"VirtualBox is a powerful x86 and AMD64/Intel64 virtualization product for enterprise as well as home use. 
Not only is VirtualBox an extremely feature rich, high performance product for enterprise customers, it is also the only 
professional solution that is freely available as Open Source Software under the terms of the GNU General Public License 
(GPL) version 2.Presently, VirtualBox runs on Windows, Linux, Macintosh, and 
Solaris hosts and supports a large number of guest operating systems including but not limited to Windows 
(NT 4.0, 2000, XP, Server 2003, Vista, Windows 7, Windows 8, Windows 10), DOS/Windows 3.x, Linux (2.4, 2.6, 3.x and 4.x), 
Solaris and OpenSolaris, OS/2, and OpenBSD. VirtualBox is being actively developed with frequent releases and has an ever 
growing list of features, supported guest operating systems and platforms it runs on. VirtualBox is a community effort 
backed by a dedicated company: everyone is encouraged to contribute while Oracle ensures the product always meets 
professional quality criteria." [Source](https://www.virtualbox.org/)
</div>

---

<center><h2>Implementation - Virtualization With VirtualBox</h2></center>

---

## Step 1 - Create and Set Up a Virtual Machine

<div style="text-align: justify">

To create a virtual machine we need first to set it up with the hypervisor software, that is, to configure how much RAM
and virtual memory we want to use, how much disk space we want to use, which devices(usb, lan ports, cd drives, etc.)
and also which kind of operating system will be installed on it. In this assignment a simple version of [Ubuntu](https://help.ubuntu.com/community/Installation/MinimalCD)
without GUI was installed on our virtual machine:

<!---img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\Initial_Set_Up.png" alt="drawing" width="1500"/>-->

<img src="imgs/CA3/Part1/Initial_Set_Up.png" alt="drawing" width="1500">

Before starting the virtual we should create a Host-Only network. Our network ip address was 192.168.147.1/24: 

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\host_only_network.png"/>-->

<img src="/imgs/CA3/Part1/host_only_network.png"/>

After that we can log in to our virtual machine and proceed to operating system configuration.
Firstly we should update packages repositories:

> **sudo apt update**

And then install networking tools:

> **sudo apt install net-tool**

Configure the network configuration file to match an ip within the network previously defined:

> **sudo nano /etc/netplan/01-netcfg.yaml**
> 
> network: <br>
>   version: 2 <br>
>   renderer: networkd <br>
>   ethernets: <br>
>       enp0s3: <br>
>           dhcp4: yes <br>
>       enp0s8: <br>
>           addresses: <br>
>               **192.168.147.5/24** <br>


And apply changes:

> **sudo netplan apply**

Install openssh-server to make possible connection through ssh to the VM:

> **sudo apt install openssh-server**

To open secure terminal sessions we should enable password authentication:

> **sudo nano /etc/ssh/sshd_config** (uncomment "PasswordAuthentication yes" line)

And apply changes:

> **sudo service ssh restart**

Install ftp server so e can use FTS protocol to transfer files from host to guest operating systems:

> **sudo apt install vsftpd**

We should enable write access for vsftpd:

> **sudo nano /etc/vsftpd.conf** (uncomment "write_enable=YES" line)

And apply changes:

> **sudo service vsftpd restart**

Now that everything is configured we can connect to our virtual machine with ssh:

> **ssh userName@192.168.147.5** (userName and passWord were defined wen guest operating system was installed in the virtual machine)

We will be needing our remote repository for this assignment, then we should install git:

> **sudo apt install git**

And also java for running java applications:

> **sudo apt install openjdk-11-jdk-headless**

Now that everything is set up we can test it with previous test application. The most interesting to be testing is the
simple chat application(CA2_Part_1) because we can simulate a client/service environment.
</div>

## Step 2 - Deploy and Application on Virtual Machine

<div style="text-align: justify">

First we should clone our repository to be able to access previous applications:

> **git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git**

For the "tut-react" we have to run the spring-boot application inside the virtual machine and then from our host OS on a browser we can access
the application:

> **./gradlew springBoot**

When the application is built and deployed we can access it via our browser to the ip we defined earlier 192.168.147.5, 
and by default spring boot deploys tomcat server in 8080 port:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\tut_react.jpg"/>-->

<img src="/imgs/CA3Part1tut_react.jpg">

We can now try and deploy the simple chat application. For this we have to execute the server inside the virtual machine
and the clients in the host machine. This is required for two reasons, first and most important is due
to the assignment purpose, has mentioned before, to simulate a client/service environment. And the client role will be filled 
by the host and the service role will be filled by the virtual machine. The second reason is, because of the client application
has a graphical user interface, it pops up a simple chat box. And our version of [Ubuntu](https://help.ubuntu.com/community/Installation/MinimalCD)
has no capabilities to run a graphical environment and when we try to run the client inside the VM an error occurs:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\error_chat_VM.jpg"/>-->

<img src="/imgs/CA3/Part1/error_chat_VM.jpg">

When we developed the CA2_Part_1 the chat server and client were deployd to localhost port 59005. Now if we want to
run the client in our local machine and connect it to the server deployed in our virtual machine we have to change
the arguments in the task runClient in our local machine. We want to connect to 192.168.147.5 on port 59005:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\runClient.png"/>-->

<img src="/imgs/CA3/Part1/runClient.png"/>

Then we have to build the application on the virtual machine and perform the runServer task. This will make the chat server
run in the VM. <br>
Then we want to do the same on the host machine, but now we want to perform the runClient task. When we are connected we can see
the server response when people join and exit the chatroom:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\chat_connection.jpg"/>
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part1\chat_exit.jpg"/>-->

<img src="/imgs/CA3/Part1/chat_connection.jpg"/>
<img src="/imgs/CA3/Part1/chat_exit.jpg"/>

If we imagine our applications as services to be provided and our virtual machine the server that supports them, we can say
that this successfully simulates a client/service environment.  <br>
With this I conclude that I have acquired the knowledge required to perform this CA3_Part_1.
</div>

---

<center><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></center>

---



