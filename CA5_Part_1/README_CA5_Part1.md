<div style="text-align: center">

# DevOps
# Class Assignment 5 - Part 1
# CA5 - Continuous Integration / Continuous Delivery

</div>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA5, we take on continuous integration and delivery from the perspective of pipelining.
"Pipelining is a continuous delivery service that automates the building, testing, and deployment of your software into 
production. Continuous delivery is a software development methodology where the release process is automated. Every software 
change is automatically built, tested, and deployed to production. Before the final push to production, a person, an automated 
test, or a business rule decides when the final push should occur. Although every successful software change can be immediately 
released to production with continuous delivery, not all changes need to be released right away. <br>
Continuous integration is a software development practice where members of a team use a version control system and frequently integrate their 
work to the same location, such as a main branch. Each change is built and verified to detect integration errors as 
quickly as possible. Continuous integration is focused on automatically building and testing code, as compared to continuous 
delivery, which automates the entire software release process up to production." <br>
In short “Continuous integration (CI) is the practice of merging all developer working copies to a
shared mainline several times a day.”, and “Continuous delivery (CD) is a software engineering approach in which teams produce
software in short cycles, ensuring that the software can be reliably released at any time.”
In this assignment we will learn how to script our pipeline, that is, a set of strategies that allows organizations to 
deliver new features faster and efficiently create an efficient and repeatable process to develop software.

[Source](https://docs.aws.amazon.com/codepipeline/latest/userguide/concepts-continuous-delivery-integration.html)

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\pipelining.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/pipelining.png">
</div>

For configuring the pipeline we will be using Jenkins.

</div>

<br>

---

## Analysis

<div style="text-align: justify">
Jenkins is an open source automation server. It helps automate the parts of software development related to building, 
testing, and deploying, facilitating continuous integration and continuous delivery. It is a server-based system that
runs in servlet containers such as Apache Tomcat. It supports version control tools, including AccuRev, CVS, Subversion, 
Git, Mercurial, Perforce, ClearCase and RTC, and can execute Apache Ant, Apache Maven and sbt based projects as well as 
arbitrary shell scripts and Windows batch commands. <br>
In this assigment we will be using Jenkins to pipeline our CA2_Part_1 Project. Like many other frameworks that we have studied before
this assignment, Jenkins uses a script to execute several pipelining steps that we intended throught a Jenkinfile at the root of the project
that we want to CI/CD. <br>
The goal of the Part 1 of this assignment is to practice with Jenkins using the "gradle
basic demo" (CA2_Part_1) developing the following tasks:

```
 1 - Create a very simple pipeline (similar to the example from the lectures). When configuring the pipeline, in the Script
 Path field, you should specify the relative path (inside your repository) for the Jenkinsfile.
 
 2 - You should define the following stages in your pipeline:
 
       2.1 - Checkout. To check out the code form the repository.
       2.2 - Assemble. Compiles and Produces the archive files with the application.
       2.3 - Test. Executes the Unit Tests and publish in Jenkins the Test results.
       2.4 - Archive. Archives in Jenkins the archive files (generated during Assemble).
       2.5 - [Extra Step] Trigger Jenkins build after commit.
```
<br>

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\jenkins.svg">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/jenkins.svg">
</div>

<br>
</div>

---

<div style="text-align: center"><h2>Implementation - Pipelining With Jenkins</h2></div>

---

## Step 1 - Install Jenkins([cdd31a3](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/cdd31a32ab1c2ea6109b00ea45882abb470c848b))

<div style="text-align: justify">
Like it was mentioned before Jenkins is a open source automation server, that means that it needs to be deployed before using it.
For that we have two options. We can deploy it in a container or use a .war file to deploy it. The first option implies the use
of Docker and there for more Disk space, the other less disk space. Since I am running low on physical space for this assignment
I will be using the .war file. We can download the official Jenkins.war from:

```
https://updates.jenkins.io/download/war/
```

Now for using it we have to start the server using the following command at the Jenkins.war location:

```
java -jar jenkins.war --httpPort=9090
```

The **--httpPort=9090** forces the deployment at http://localhost:9090. The default value will be port 8080.

I will be running it in port 9090 because 8080 is the port we are running the springBoot tomcat server  for the SWitCH
main project. For more information we can visit:

```
https://www.jenkins.io/doc/book/installing/war-file/
```

When we access http://localhost:9090/ for the first time we will have to configure our credentials for Jenkins. After
this we are ready for starting to configure our [Pipeline](https://www.jenkins.io/doc/book/pipeline/).

</div>

## Step 2 - Configuring Jenkins Pipeline ([18e072d](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/18e072d891600968d2ddfa7b4e0552411cf00cd7))

Configuring the pipeline is easy, we just have to enter our repository link, credentials and if we want to write the scrip
directly on Jenkins or if we use a [Jenkinsfile](https://www.jenkins.io/doc/book/pipeline/jenkinsfile/) from the Source Control Management(SCM). In my case I will be using a Jenkins file
at the root of the project CA2_Part_1.

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\peipeline_defenitions.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/peipeline_defenitions.png">
</div>

## Step 3 - Scripting Steps for Our Build ([7d533a2](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/7d533a22c2b33fe8720dfcbbd4d50ae2d7b53607))

Now we are ready to define the build steps for our project. Checkout, assemble, test and archive. Every Jenkins pipeline
starts with:

```
pipeline {
agent any

    stages {
    
    }
}
```

And then we can script as many stages as we want. <br>
Firs of all we have to checkout/download the files from git repository. We have to use the previously created credentials
to access our remote repository and checkout our code:

```
pipeline {
agent any

    stages {

        stage('Checkout') {
                steps {
                    echo 'Checking out...'
                    git credentialsId: 'PM_BitBucket_Credentials', url: 'https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git'
                }
        }
```

Since I am using the Jenkins.war, Jenkins will download the repository to my local machine. <br>
Now with the code in Jenkins possession we now can script the actual build steps. We will start with building all the classes
and test classes of the project:

```
stage('Build') {
                steps {
                    dir('CA2_Part_1/gradle_basic_demo/') {
                        echo 'Assembling Project...'
                        script {
                            bat 'dir'
                            bat 'gradle clean assemble'    
                            bat 'gradle testClasses'
                        }
                }
            }
        }
```

As we can see, we just have to script the commands that we want to be executed, this case gradle commands from CA2_Part_1.
There is a detail here, I am using **bat** instead of **sh**, this is because I have a Windows OS, and I have to use batch
commands from the windows command prompt. If it was a unix based OS we should use shell commands. <br>
Now we will script the test [step](https://www.jenkins.io/doc/pipeline/steps/junit/). That is, after the build we will run the [test](https://www.jenkins.io/doc/pipeline/tour/tests-and-artifacts/) of our classes:

```
stage('Test') {
            steps {
                dir('CA2_Part_1/gradle_basic_demo/') {
                    echo 'Testing 1...2...3...'
                    script {
                        bat 'dir'
                        bat 'gradle check'
                        bat 'gradle test'       
                    }
                }
            }
        }
```

If the tests are successful we will archive our artifacts:

```
stage ('Archiving') {
            steps {
                dir('CA2_Part_1/gradle_basic_demo/') {
                    echo 'Archiving...'
                    archiveArtifacts 'build/distributions/'
                }
            }
        }
    }
```

Finally, we will be archiving tests results. This needs to be done sparely and out of scope of the stages:

```
post {
        always {
            dir('CA2_Part_1/gradle_basic_demo/') {
                script {
                    junit testResults: '**/test-results/**/*.xml'
                }
            }
        }
    }
```

Now we can take a look at the entirety of the script:

```
pipeline {
agent any

    stages {

        stage('Checkout') {
                steps {
                    echo 'Checking out...'
                    git credentialsId: 'PM_BitBucket_Credentials', url: 'https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git'
                }
        }

        stage('Build') {
                steps {
                    dir('CA2_Part_1/gradle_basic_demo/') {
                        echo 'Assembling Project...'
                        script {
                            bat 'dir'
                            bat 'gradle clean assemble'    
                            bat 'gradle testClasses'
                        }
                }
            }
        }

         stage('Test') {
            steps {
                dir('CA2_Part_1/gradle_basic_demo/') {
                    echo 'Testing 1...2...3...'
                    script {
                        bat 'dir'
                        bat 'gradle check'
                        bat 'gradle test'       
                    }
                }
            }
        }

        stage ('Archiving') {
            steps {
                dir('CA2_Part_1/gradle_basic_demo/') {
                    echo 'Archiving...'
                    archiveArtifacts 'build/distributions/'
                }
            }
        }
    }

    post {
        always {
            dir('CA2_Part_1/gradle_basic_demo/') {
                script {
                    junit testResults: '**/test-results/**/*.xml'
                }
            }
        }
    }

}
```

After running our build, all the artifacts and tests will be successful:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\build_success.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/build_success.png">
</div>

## Step 4 - Triggering Jenkins To Build Project After Commit ([745684d](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/745684d9b7a1a1a5332ef81200d05674beb2c633))

In a professional context it is not usual for build to be triggered manually. The point of DevOps and these kinds of technologies
is to automate repetitive tasks. Normally in a professional environment, every time a push is made to a branch, Jenkins will
start to build the project automatically following the scripted stages instead of waiting for human input to do so.
To solve this jenkins has a build [trigger](https://www.youtube.com/watch?v=nNaR5Q_pIa4) option on the pipeline settings that we can configure so that everytime we
commit something to the repository Jenkins will start a build:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\jenkins_trigger.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/jenkins_trigger.png">
</div>

After this we have to make so that our remote repository send a message to Jenkins to alert it that a commit has been made.
This is called a webhook. So we have to configure a webhook for bitbucket:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\bitbucket_webhook.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/bitbucket_webhook.png">
</div>

Now the setup is made. Jenkins is "listening" for bitbucket webhooks and bitbucket is configured to send a message to Jenkins
so that it can start to build the project. This won't work in my current setup since Jenkins is running at localhost and
not exposed to the internet. That means that Jenkins won't be getting any message and no automatic build will start. <br>
For solving this issue we will use an open source tool that allows to expose a localhost port to the internet by creating a
"tunnel" that exposes localhost to the internet. This tool is called ngrok:

> **https://ngrok.com/download**

And we just have to use a simple command to expose our port to the internet:

> **ngrok http 9090**

This will expose our port and create an address that forwards to localhost:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\ngrok.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/ngrok.png">
</div>

After this I thought that everything was ready for automatic building this project. But I was wrong, because everytime that 
Jenkins the webhook it refused it and no build was started:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\Forbiden.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/Forbiden.png">
</div>

After some search I found a solution. I had to create an API Token in Jenkins and add it to the webhook URL so that he accepts
incoming traffic from bitbucket and starts a build. After this Jenkins received the webhook and started the build:

<div style="text-align: center">
<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA5\Part_1\triggered.png">
</div>

<div style="text-align: center">
<img src="/imgs/CA5/Part_1/triggered.png">
</div>

This concludes this report. Jenkins is a very powerful tool and can use many other technologies through plugins. 
This we will be seeing in CA5_Part_2.

---

<div style="text-align: center"><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></div>

---