<center><h1>DevOps</h1></center>
<center><h2>Class Assignment 3 - Part 2</h2></center>
<center><h2>CA3 - Virtualization with Automation</h2></center>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA3, we take on virtualization.
The proposed virtualization tool for this assignment was VirtualBox, as usual an alternative will ber researched. I choose
VMware workstation, as an alternative, mainly because they have a non enterprise edition that lets you test the hypervisor
without the need of paying for it.<br>
In this second part we will learn how to use Vagrant, a automation tool that is used to setup a virtualized environment,
making some or all operations done on the first part to be ran automatically by a script written in a VagrantFile. <br>
We will have the basic tutorial deployed in two virtual machines, one for the database (db) and another for the web server
(web).
</div>

---

## Analysis
<div style="text-align: justify">

"In today’s modern IT world, virtualization is important concept. Virtualization can improve IT agility, flexibility, and 
scalability while also lowering costs. Virtualization’s benefits include increased workload mobility, increased resource 
performance and availability, and automated operations, all of which make IT easier to manage and less expensive to own and 
operate. For software and operating system testing, virtualization has become the norm. Multiple operating systems can be 
run on the same physical resources thanks to server virtualization. As a result, the number of dedicated physical servers 
required is minimised." [Source](https://www.interviewbit.com/blog/vmware-vs-virtualbox/) <br>
On this assigment we will be using VirtualBox and VMware for deploying a multi virtual machine environment and Vagrant
for automate their deployment. <br>

<!--- This is an HTML comment in Markdown <img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\Virtualbox_logo.jfif" alt="drawing" width="200"/>-->
<img src="/imgs/CA3/Part2/Virtualbox_logo.jfif" alt="drawing" style="width:200px;">

"VirtualBox is a powerful open source x86 and AMD64/Intel64 virtualization product for enterprise as well as home use.
Not only is VirtualBox an extremely feature rich, high performance product for enterprise customers, it is also the only
professional solution that is freely available as Open Source Software under the terms of the GNU General Public License
(GPL) version 2.Presently, VirtualBox runs on Windows, Linux, Macintosh, and
Solaris hosts and supports a large number of guest operating systems including but not limited to Windows
(NT 4.0, 2000, XP, Server 2003, Vista, Windows 7, Windows 8, Windows 10), DOS/Windows 3.x, Linux (2.4, 2.6, 3.x and 4.x),
Solaris and OpenSolaris, OS/2, and OpenBSD. VirtualBox is being actively developed with frequent releases and has an ever
growing list of features, supported guest operating systems and platforms it runs on. VirtualBox is a community effort
backed by a dedicated company: everyone is encouraged to contribute while Oracle ensures the product always meets
professional quality criteria." [Source](https://www.virtualbox.org/)

| Advantages  | Disadvantages  |
|:---:|:---:|
| Increased effectiveness and lower costs  |  Dependent on the host machine |
|  Installation and setup are simple | Affected by the host machine's flaws  |
| User-friendly  | Less efficient  |
| Personalizable  |   |


<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\vmware.jpg" alt="drawing" width="200"/>-->
<img src="/imgs/CA3/Part2/vmware.jpg" alt="drawing" width="200">

"VMware is a hypervisor software developed by VMware, Inc., Palo Alto(California) that is installed on a physical server 
and allows different operating systems to operate on the same machine. On the same physical server, all VMs share resources 
like networking and RAM. Virtualization, networking and security management tools, software-defined data centre software, 
and storage software are few among VMware’s offerings. VMware’s virtualization products have become an important part of 
the IT infrastructure of all businesses." [Source](https://www.interviewbit.com/blog/vmware-vs-virtualbox/)

| Advantages  | Disadvantages  |
|:---:|:---:|
| Increased efficiency  | Handling requirements  |
|  Optimal data center space utilization | Not best for complete physical product testing  |
| Fast rollback provision  | Low efficency  |
| Adding new virtual machine is easy  | Lack of reliability  |

Comparing both main features:

|   Feature  | VirtualBox    | VMware    |
|:---:|:---:|:---:|
|   Hardware and software virtualisation |  Provides virtualization for both hardware and software   |  Provides virtualization only for hardware.    |
|Host operating system | VirtualBox is available for Linux, Windows, Solaris, macOS, FreeBSD etc. There exists a wide scope of supporting various OS.|VMare is available for Linux and Windows. You must install VMware Fusion/Fusion Pro if you have a Mac. There is a narrow scope of supporting various OS |
|Guest operating system | On VirtualBox-based VMs, the following guest operating systems can be installed: Linux, Windows, Solaris, FreeBSD, and macOS.| VMware also supports operating systems such as Windows, Linux, Solaris, and Mac. The only distinction is that to operate macOS virtual machines, VMware requires VMware Fusion/Fusion Pro.|
|User-friendly interface |VirtualBox provides a user-friendly interface. |VMware provides a complicated user interface. |
| Video memory|Video memory, incase of VirtualBox, is limited to 128 MB. | Video memory, incase of VMware, is limited to 2 GB.|
| Snapshots |VirtualBox supports snapshots, which means it allows you to save and restore the state of a virtual machine. | Snapshots only supported on paid virtualization products, not on VMware Workstation Player|
|Network modes |The following network modes are available in VirtualBox:Not attached, Network Address Translation (NAT), NAT Network, Bridged networking, Internal networking, Host-only networking, Generic networking, UDP Tunnel, Virtual Distributed Ethernet (VDE) | VMware supports the following network nodes:Network Address Translation (NAT), Host-only networking, Virtual network editor (on VMware workstation and Fusion Pro)|
[Source](https://www.interviewbit.com/blog/vmware-vs-virtualbox/)

Personally I would choose VirtualBox over VMware. For my current level of expertise, that is, beginner, VirtualBox proves
to be a very simple tool and that sure is an advantage through the learning process. On the other hand it seems that VMware
is a professional solution, with a wide variety of services and probably used by many companies. <br>
At our current scale it is difficult to sate that one is better than the other. Considering the scope of our assignment I
find them equivalent for the task at hand.

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\vagrant.png" alt="drawing" width="200"/>-->

<img src="/imgs/CA3/Part2/vagrant.png" alt="drawing" width="200">

"Vagrant is a tool for building and managing virtual machine environments in a single workflow. With an easy-to-use workflow 
and focus on automation, Vagrant lowers development environment setup time, increases production parity, and makes the 
"works on my machine" excuse a relic of the past. If you are already familiar with the basics of Vagrant, the documentation 
provides a better reference build for all available features and internals.
Vagrant provides easy to configure, reproducible, and portable work environments built on top of industry-standard technology 
and controlled by a single consistent workflow to help maximize the productivity and flexibility of you and your team.
To achieve its magic, Vagrant stands on the shoulders of giants. Machines are provisioned on top of VirtualBox, VMware, AWS, 
or any other provider. Then, industry-standard provisioning tools such as shell scripts, Chef, or Puppet, can automatically 
install and configure software on the virtual machine." [Source](https://www.vagrantup.com/intro) <br>
Vagrant revolves around a simple file called VagrantFile. This file is simply a configuration for virtual machines, normally
written in ruby. With this file we can automate the deployment of one or more virtual machines.We can check 
[here](https://www.vagrantup.com/docs/vagrantfile) the configuration options for the VagrantFile.
</div>

---

<center><h2>Implementation - Virtualization With VirtualBox</h2></center>

---

## Step 1 - Installing Vagrant

<div style="text-align: justify">
First we must download and install vagrant in our computer: 

> **https://www.vagrantup.com/docs/vagrantfile**

After installation, we should check if vagrant is active. In a terminal we can check its version using:

> **vagrant -v**

## Step 2 - Initialize Vagrant Project

If we didn't have a VagrantFile we could initialize our vagrant project using:

> **vagrant init**

This will generate a VagrantFile and a folder .vagrant similar to version controls systems like git.
But in this second part of the assignment a vagrant file is provided, we had only to copied it to assignment folder.

## Step 3 - Configure Vagrant Project Trough VagrantFile

The provided VagrantFile deploys two virtual machines, one named **web** to run tomcat and the spring boot basic application
and another named **db** used to execute H2 server database. If not told otherwise vagrant will use the default hypervisor
to run the virtual machines, in my case, VirtualBox is the default hypervisor. Vor every virtual machine we have to dictate
which operating system will be used, we will be using ubuntu-xenial:

```
#This is the initial statment for a vagrant configuration file
Vagrant.configure("2") do |config|
#This dictates which box should be used in the virtual machine
config.vm.box = "envimation/ubuntu-xenial"
```

With this we tell which operating system we will be using.
Then we should initialize through shell scrip commands common configurations for both VM:

```
#This starts a shell for running shell command, for installing java i.e.
config.vm.provision "shell", inline: <<-SHELL
  sudo apt-get update -y
  sudo apt-get install iputils-ping -y
  sudo apt-get install -y avahi-daemon libnss-mdns
  sudo apt-get install -y unzip
  sudo apt-get install openjdk-8-jdk-headless -y
SHELL
```

Now we are ready to configure our database and web server. <br>
For the database configuration we need to configure IP address and port for H2 console and H2 server and of course download H2 dependency:

```
config.vm.define "db" do |db|
  db.vm.box = "envimation/ubuntu-xenial"
  #This configures the host name for this virtual machine
  db.vm.hostname = "db"
  #This configures the ip address
  db.vm.network "private_network", ip: "192.168.56.11"
  #This configures the access port, we have two because we have to consider H2 console and server
  db.vm.network "forwarded_port", guest: 8082, host: 8082
  db.vm.network "forwarded_port", guest: 9092, host: 9092
  db.vm.provision "shell", inline: <<-SHELL
  wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
SHELL
```

Now we have to guarantee that H2 server will always be initiated:

```
#We do this with the :run => 'always' statment
db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
end
```

Then the web server needs a similar configuration:

```
config.vm.define "web" do |web|
web.vm.box = "envimation/ubuntu-xenial"
web.vm.hostname = "web"
web.vm.network "private_network", ip: "192.168.56.10"

    #We set ram memmory for this VM
    web.vm.provider "virtualbox" do |v|
      v.memory = 1024
    end

    web.vm.network "forwarded_port", guest: 8080, host: 8080

    web.vm.provision "shell", inline: <<-SHELL, privileged: false
      sudo apt-get install git -y
      sudo apt-get install nodejs -y
      sudo apt-get install npm -y
      sudo ln -s /usr/bin/nodejs /usr/bin/node
      sudo apt install tomcat8 -y
      sudo apt install tomcat8-admin -y
      
      #This line was added for testing purposes, everytime we changed configuration we wanted the repository to be deleted and clone the recetly pushed
      rm -R  devops-21-22-lmn-1211790
      #This line was changed to match our repository directory
      git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git
      #This line was changed to match our repository directory structure
      cd devops-21-22-lmn-1211790/CA2_Part_2
      chmod u+x gradlew
      ./gradlew clean build
      #This line was changed to match our repository directory structure and our .war file name
      sudo cp ./build/libs/reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT.war /var/lib/tomcat8/webapps
    SHELL

    end
end
```
Recapping, we have configured two virtual machines with one VagrantFile. If we think of what we did in CA3_Part1 were we
installed manually a virtual server and inputted all shell commands to install needed resources, and compare it with the
provided VagrantFile, we can quickly conclude that we would prefer to write a ruby script than to write every command manually.
Basically with vagrant we can strategize what we want to do and in what order and configure our deployment accordingly.
There is also one more upside to all this, after we write a configuration file for vagrant we can replicate it in other machines
with minor changes.
Now that the vagrant file is described and updated to work with our repository, there is an error that came up while building
the project regarding java version:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\erro_jdk.png"/>-->

<img src="/imgs/CA3/Part2/erro_jdk.png">

In my local machine I have installed jdk11, and previous projects were developed using this version of java. But the box
we are using for this assignment is compatible only with jdk8. To solve this I changed the build.gradle file to use the 
version 8 of java:
<br>
<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\java8.png" alt="drawing" width="600"/>-->

<img src="/imgs/CA3/Part2/java8.png" alt="drawing" width="600">

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\java8.png" alt="drawing" width="600">-->

<img src="/imgs/CA3/Part2/java8.png" alt="drawing" width="600">
<br>
At this point we should configure our application by following Alexandre Bragança commits, at (https://bitbucket.org/atb/tut-basic-gradle/commits/).
First we should add support for build the web application resource file (.war), by adding it to the plugin section and
to the dependencies section:

```
plugins {
    id 'org.springframework.boot' version '2.6.6'
    id 'io.spring.dependency-management' version '1.0.11.RELEASE'
    id 'java'
    id 'org.siouan.frontend-jdk8' version '6.0.0'
    //To support war file creation
    id 'war'
}
```
```
dependencies {
    imp+lementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-data-rest'
    implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
    runtimeOnly 'com.h2database:h2'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    //To support war file for deploying to tomcat
    providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
}
```

Then we should add a java class for application servelert initializer at  src/main/java/com/greglturnquist/payroll/ServletInitializer.java:

```
package com.greglturnquist.payroll;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ReactAndSpringDataRestApplication.class);
    }
}
```

Now we should start and configure the database H2. In the application properties file at src/main/resources/application.properties:

```
server.servlet.context-path=/reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT
spring.data.rest.base-path=/api
#spring.datasource.url=jdbc:h2:mem:jpadb
# In the following settings the h2 file is created in /home/vagrant folder
spring.datasource.url=jdbc:h2:tcp://192.168.56.11:9092/./jpadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
# So that spring will no drop de database on every execution.
spring.jpa.hibernate.ddl-auto=update
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.h2.console.settings.web-allow-others=true
```

The application javaScript file should be updated to use the .war file at src/main/js/app.js:

```
componentDidMount() { // <2>
    client({method: 'GET', path: '/reac-and-spring-data-rest-basic-0.0.1-SNAPSHOT/api/employees'}).done(response => {
    this.setState({employees: response.entity._embedded.employees});
    });
}
```

With this we are ready to deploy our virtual machines. We should open a terminal at the same directory has the VagrantFile and 
type:

> **vagrant up**

And the virtual machines will be automatically initialized, and if everything is running without error we should have
a build success message like this:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\Success.png"/>-->

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\Success.png"/>

Now if we access the ip addresses that we have configured we can see the pages for the frontend and the H2 server:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\frodo.png"/>
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\H2.png"/>-->

<img src="imgs/CA3/Part2/frodo.png"/>
<img src="/imgs/CA3/Part2/H2.png"/>

In summary in this assignment we developed a client/service environment but this time with two virtual machines. We explored the
capabilities of vagrant to deploy a multi virtual machine environment and simulated a service were the frontend and database were
deployed in different virtual machines.
</div>

---

<center><h2>Implementation - Virtualization With VMware</h2></center>

---

## Step 1 - Configure VMware to be Recognized by Vagrant

I copied the project to the alternative folder to deploy it with VMware. The configuration is similar but there are a few
details that we have to be mindful about. First for vagrant to recognize VMware as a hypervisor we must first install 
vagrant VMware [utility](https://www.vagrantup.com/vmware/downloads).


## Step 2 - Solve IP addresses Collision

When we have configured the VM with VirtualBox the ip addresses remain occupied and when we try to deploy the same VM
with VMware an error occurs:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\ipsCollide.png"/>-->
<img src="/imgs/CA3/Part2/ipsCollide.png"/>

We have to change the ip addresses of the VM's in the vagrant configuration file:

```
config.vm.define "db" do |db|
    db.vm.box = "envimation/ubuntu-xenial"
    db.vm.hostname = "db"
    db.vm.network "private_network", ip: "192.168.33.11"
    db.vm.network "forwarded_port", guest: 8082, host: 8082
    db.vm.network "forwarded_port", guest: 9092, host: 9092
    
    db.vm.provision "shell", inline: <<-SHELL
      wget https://repo1.maven.org/maven2/com/h2database/h2/1.4.200/h2-1.4.200.jar
    SHELL

    db.vm.provision "shell", :run => 'always', inline: <<-SHELL
      java -cp ./h2*.jar org.h2.tools.Server -web -webAllowOthers -tcp -tcpAllowOthers -ifNotExists > ~/out.txt &
    SHELL
  end
    
config.vm.define "web" do |web|
    web.vm.box = "envimation/ubuntu-xenial"
    web.vm.hostname = "web"
    web.vm.network "private_network", ip: "192.168.33.10"
    web.vm.provider "vmware_workstation" do |v|
      v.memory = 1024
    end
```

## Step 3 - Set VMware has the new Hypervisor

Finally, we have to set vmWare has the hypervisor for this vagrant project so that he is used instead of the default
VirtualBox:

```
Vagrant.configure("2") do |config|
  config.vm.box = "envimation/ubuntu-xenial"
  config.ssh.forward_agent = true
  config.vm.provider "vmware_workstation" do |v|
    v.gui = true
  end  
```

After this final step we are ready to deploy our VM's using VMware. Unfortunately I was unable to access the virtual machines 
through my host browser, I tried to debug and find out the problem but without success. The machines were deployd without error:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\vmware_db_web.png"/>-->
<img src="/imgs/CA3/Part2/vmware_db_web.png">

The ports were occupied and listened to:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\alternative_ips.png"/>-->
<img src="/imgs/CA3/Part2/alternative_ips.png">

But in the end I could not access the deployed web server:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\alternative_connection_db.png" alt="drawing" width="1000"/>
img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\alternative_connection_web.png" alt="drawing" width="1000"/>-->
<img src="/imgs/CA3/Part2/alternative_connection_db.png" alt="drawing" width="1000">
<img src="/imgs/CA3/Part2/alternative_connection_web.png" alt="drawing" width="1000">

There was one final experiment that I did. I tried to deploy all four VM's, two with VirtualBox and another two with
VMware. After various tries I could not deploy them both. I think it is related to the fact that the hypervisors conflict 
when they ask the host to set up a NAT connection with them. The second hypervisor to be deployed can't resolve network interface
deployment and times out. Probably because there is one hypervisor already connected to the host:

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA3\Part2\error_vmware_conflicting_host_ip.png"/>-->
<img src="/imgs/CA3/Part2/error_vmware_conflicting_host_ip.png">

---

<h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2>

---




