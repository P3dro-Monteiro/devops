<center><h1>DevOps</h1></center>
<center><h2>Class Assignment 2 - Part 1</h2></center>
<center><h2>CA2 - Build Tools</h2></center>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA2, we dive into build tools.
The proposed build tool for this assignment was gradle. 
It is also required of us to research an alternative tool to gradle,
and implement it in the same way and compare both of them. <br> 
The alternative tool chosen for this assignment was Bazel and Apache Maven.
This report corresponds to the first part of the assignment.
</div>

---

## Analysis
<div style="text-align: justify">
Build refers to the process of turning source code into a runnable version of it, that is, the act of creating executable 
applications. <br>
A build tool is a program that automate this process. When projects are very small developers can invoke the build process
manually, but this is not practical for large projects since manually we have to know what needs to be built, in what order,
with what dependencies. <br> 
Build tools a.k.a automation tools, allows to structure your build and write it only once for the entirety of the project,
making the build process more consistent and easy to maintain. <br>
Generally speaking a build process could be the following:

- Compile (Turning source Code into an executable application)
- Resolve Dependencies (Get the needed libraries used in implementation)
- Package (Gather software modules, may include QA)
- Deployment (Install it on a device or deploy it to a remote server)

In summary, with build tools the goal is to automate a wide variety of tasks that developers need done in a daily basis.
</div>

<!--<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\gradle_logo.png" alt="drawing" width="400"/>-->
<img src="/imgs/CA2/gradle_logo.png" alt="drawing" width="400">
[Source](https://docs.gradle.org/current/userguide/what_is_gradle.html)
<div style="text-align: justify">
Gradle is an open source, designed to be flexible and build any type of software. It is considered to be a hybrid between
apache ant and maven. Gradle is high performant, runs only the needed tasks by knowing what inputs or outputs have changed.
It can use build cache to reuse tasks outputs from previous runs in a new build, even from different machines. 
Gradle runs using Java Virtual Machine, but it isn't limited to building only java projects. 
As maven gradle recognises projects conventions and uses them, but we can override them if needed. 
<br>
Is based in high level language such as Groovy or Kotlin.
<br>
In my opinion the best feature is that Gradle is task based. This feature is more logic to me. When we want to set up
a build sequence, we can easily translate them to tasks.
</div>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\bazel_logo.png" alt="drawing" width="200"/>-->
<img src="/imgs/CA2/bazel_logo.png" alt="drawing" width="200">

[Source](https://bazel.build/faq)
<div style="text-align: justify">
Bazel is Google build tool that was open sourced recently. It supports many languages like java, C++, shell, kubernetes,
java script, and many others. This makes bazel one of the build tools more versatile that I have researched. <br>
Bazel understands a specific BUILD high level language. It is a concise text format that describes a project as sets of
small interconnected libraries, binaries and tests. This fact allows to define our build granularity, that is, we can
have a build for a project like maven our gradle, or we can choose to make separate build for every file on the project
if we want to.<br>
Bazel is most suited for projects with a large codebase, written in multiple compiled languages, project that deploy in multiple
platforms and have extensive tests.
</div>
<br>

<!---<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\maven_logo.png" alt="drawing" width="200"/>-->
<img src="/imgs/CA2/maven_logo.png" alt="drawing" width="200">

[Source](https://maven.apache.org/what-is-maven.html)
<div style="text-align: justify">
Apache Maven is a automation tool mostly used for Java Projects. It is XML based and its main feature is dependency management
and reuse. Maven focus on making the build process easy, providing an uniform build system and quality project information.
<br>
Maven projects are configured using a Project Object Model Extensible Markup Language file, (pom.xml). In this file are written
our build scripts and then provides configuration for a single project. I am familiar with Maven since it is the tool we are using in our project.
</div>

---

<center><h2>Implementation - Automation With Gradle</h2></center>

---

## Step 1 - Build the Project

To build a project means to compiles the source files into .jar files (Java Archives). For that I used this command:

> **./gradlew build**

Used where the build.gradle file is located.

## Step 2 - Run the Server

For running the server on this assignment I had to run the following command at the command line:

> **java -cp build/libs/basic_demo-0.1.0.jar basic_demo.ChatServerApp <server port>**

The configured port was 59005.

## Step 3 - Run the Client

For running the chat client I used:

> **./gradlew runClient**

With this command the chat client is launched at localhost at port 59005.

## Step 4 - Add New Task to Execute the Server [c54e3df](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/c54e3dfde66c2142379d750fdde29734dee32f2f)

<div style="text-align: justify">
As mentioned before, gradle is task based, so every action that we need to be done out of the convention scope, we can
write them in Groovy. Every task has a name, type and dependencies, that is, can depend on other tasks. <br>
The run server command we saw previously called java, so we want to execute java, then the type of the tasks needs to be
JavaExec. On the other hand we can't run java if the .java files are not compiled, so I have made this task class dependent.
So that if we want to run it, it compiles .java files first. The last thing need to do was the arguments, that is, the port
to lauch the chat server. That was defined in the scope of the task. The final result was the following:
</div>

>task runServer(**type:JavaExec**, **dependsOn: classes**){
> 
>group = "DevOps"
>description = "Run Server Launched on localhost: 59005"
>
>    classpath = sourceSets.main.runtimeClasspath
>
>    mainClass = 'basic_demo.ChatServerApp'
>
>    **args '59005'**
> 
>}

## Step 5 - Add a Simple Unit Test and Update the Gradle Script so That it is Able to Execute the Test [883ad71](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/883ad71b145a6fa9edb265243facf3cd29d47015)
<div style="text-align: justify">
By default, Gradle build follow java project convention, so Gradle already knows were the test folder should be, the thing
we need to do is create a test folder accordingly with convention, implement a test, and add a junit 4.12 
dependency for running the tests. <br>
Gradle has a section dedicated to dependencies were we need to set them:
</div>

>dependencies {
> 
>// Use Apache Log4J for logging
> 
>implementation group: 'org.apache.logging.log4j', name: 'log4j-api', version: '2.11.2'
> 
>implementation group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.11.2'
> 
>//junit 4.12
> 
>**implementation 'junit:junit:4.12'**
> 
>}

## Step 6 - Add a new task of type Copy to be Used to Make a Backup of the Sources of the Application [e5c44a9](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/e5c44a96b8179eba0dd11f25994e2ed1bfe6ef7d)
<div style="text-align: justify">

It is very simple to implement a Copy task with Gradle, we only have to say **from** were we want to copy the files and
**into** what folder we want them copied:
</div>

>task backupSourceCode(type: **Copy**){
> 
>**from "./src"**
> 
>**into "./srcBackup"**
> 
>ext.message = "Source Code Back Up Success"
> 
>println "Source Code Back Up Success"
> 
>}

## Step 7 - Add a New Task of Type Zip to be Used to Make an Archive of the Sources of the Application [4635953](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/4635953983a5482a5f34dd820aa10d47070daa56)
<div style="text-align: justify">

At the end of the first part we needed to create a Zip type task. This one is very similar to Copy type tasks. We just have
to say what(**from**) we want to Zip, destination directory and the name to give to the archive:

>task zipSourceCode(type: **Zip**){
> 
>**from "./src"**
> 
>**destinationDirectory = file("./")**
> 
>**archiveName = "sourceCode.zip"**
> 
>ext.message = "Source Code Has Been Zipped"
> 
>println "Source Code Has Been Zipped"
> 
>}
</div>

---

<center><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></center>

---



