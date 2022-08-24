<center><h1>DevOps</h1></center>
<center><h2>Class Assignment 2 - Part 2</h2></center>
<center><h2>CA2 - Build Tools</h2></center>

---

## Overview

<div style="text-align: justify">
In the present assignment, CA2, we dive into build tools.
The proposed build tool for this assignment was gradle. 
It is also required the research of an alternative tool to gradle,
and implement it in the same way and compare both of them. <br> 
The alternative tool chosen for this assignment was Bazel and Apache Maven.
This report corresponds to the second part of the assignment.
</div>

<div style="text-align: justify">
Build refers to the process of turning source code into a runnable version of it, that is, the act of creating executable 
applications. <br>
A build tool is a program that automate this process. When projects are very small, developers can invoke the build process
manually, but this is not practical for large projects because we need to know what to built, in what order and in which
dependencies. <br> 
Build tools, a.k.a automation tools, structure your build and write it only once for the entirety of the project,
making the build process more consistent and easy to maintain. <br>
Generally speaking, a build process could be the following:

- Compile (Turning source Code into an executable application)
- Resolve Dependencies (Get the needed libraries used in implementation)
- Package (Gather software modules, may include QA)
- Deployment (Install it on a device or deploy it to a remote server)

In summary, with build tools the goal is to automate a wide variety of tasks that developers need done in a daily basis.
</div>

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\gradle_logo.png" alt="drawing" width="400"/>

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
In my opinion the best feature is that Gradle is task based. When we want to set up
a build sequence, we can easily translate them to tasks, which is more intuitive.
</div>
<br>

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\maven_logo.png" alt="drawing" width="200"/>

[Source](https://maven.apache.org/what-is-maven.html)
<div style="text-align: justify">
Apache Maven is a automation tool mostly used for Java Projects. It is XML based and its main feature is dependency management
and reuse. Maven focus on making the build process easy, providing an uniform build system and quality project information.
<br>
Maven projects are configured using a Project Object Model Extensible Markup Language file, (pom.xml). In this file are written
our build scripts and then provides configuration for a single project. I am familiar with Maven since it is the tool we are using in our project.
</div>
<br>

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\bazel_logo.png" alt="drawing" width="200"/>

[Source](https://bazel.build/faq)
<div style="text-align: justify">
Bazel is Google build tool that was open sourced recently. It supports many languages like java, C++, shell, kubernetes,
java script, and many others. This makes bazel one of the  most versatile build tools that I have researched. <br>
Bazel understands a specific BUILD high level language. It is a concise text format that describes a project as sets of
small interconnected libraries, binaries and tests. This fact allows to define our build granularity, we can
have a build for a project like maven or gradle, or we can choose to make separate build for every file on the project
if we want to.<br>
Bazel is most suited for projects with a large codebase, written in multiple compiled languages, project that deploy in multiple
platforms and have extensive tests.
</div>
<br>

---

## Analysis, Gradle Vs Maven
<div style="text-align: justify">
In my understanding, Maven is not as flexible as Gradle. It follows a structure divided in life cycle, phases and goals.
These are a set of tasks that are executed when the Maven build is run. 
Maven builds goes through a set of stages called build phases. And a build phase is made up of a set of goals that represent
specific tasks to be made on a specific phase that contribute to project management. <br>
Maven default phases are:

* validate <br>
* compile <br>
* test <br>
* package <br>
* verify <br>
* install <br>
* deploy <br>

These build phases are built sequentially, when we run maven build command we specify the phase to be executed. Since they
are sequential if we execute **./mvn package** it will execute previous phases, validate, compile and test.
Both Gradle and Maven provide convention over configuration. However, Maven provides a very rigid model that makes customization 
sometimes impossible. While this can make it easier to understand any given Maven build, as long as you don’t 
have any special requirements, it also makes it unsuitable for many automation problems. Gradle, on the other hand, is 
built to empower users. <br>
"Both build systems provide built-in capability to resolve dependencies from configurable repositories. 
Both are able to cache dependencies locally and download them in parallel.
As a library consumer, Maven allows one to override a dependency, but only by version. 
Gradle provides customizable dependency selection and substitution rules that can be declared once and handle unwanted 
dependencies project-wide. This substitution mechanism enables Gradle to build multiple source projects together to create composite builds.
As a library producer, Gradle allows producers to declare `api` and `implementation` dependencies to prevent unwanted 
libraries from leaking into the classpaths of consumers. Maven allows publishers to provide metadata through optional 
dependencies, but as documentation only. Gradle fully supports feature variants and optional dependencies. 
Improving build time is one of the most direct ways to ship faster. Both Gradle and Maven employ some form of parallel project 
building and parallel dependency resolution. The biggest differences are Gradle's mechanisms for work avoidance and incrementality."[Source](https://gradle.org/maven-vs-gradle/) <br>

In my personal experience I have had only used Maven before learning DevOps, but now that I have experimented with Gradle,
I would gladly make the migration in our project. I had more difficulties when implementing the alternative with Maven 
compared to the main implementation with Gradle. Gradle is very easy to understand and implement, and also more
flexible. This is related to implementation language, Groovy is certainly better than XML. 
</div>

---

<center><h2>Implementation - Automation With Gradle</h2></center>

---

## Step 1 - Create a New Branch ([612ae2e](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/612ae2eed493bac481568d08af111bf527860b29))

First we created a new branch:

> **git branch tut-basic-gradle**
 
Then we moved to it:

> **git checkout tut-basic-gradle**

See CA1 for more information.

## Step 2 - Create a Gradle Spring Boot Project ([612ae2e](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/612ae2eed493bac481568d08af111bf527860b29))

I created a spring boot gradle project with **https://start.spring.io/** with the indicated dependencies: Rest,
Repositories, Thymeleaf, JPA and H2.

## Step 3,4 and 5 - Extract Generated Zip File to Project Folder and Copy Source Files ([612ae2e](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/612ae2eed493bac481568d08af111bf527860b29) / [bd13472](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/bd134724aa4582a214caf94734f43170b1f74709))

The generated file was extracted to the project folder. And the source files were replaced by the ones from the basic tutorial. <br>
Then we could see default tasks from a Gradle project with:

> **./gradlew tasks**

## Step 6 - Running the Application ([bd13472](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/bd134724aa4582a214caf94734f43170b1f74709))

We can now run the application using spring boot:

> **./gradlew bootRun**

This will deploy the server at http://localhost:8080/. At this stage we don't have any frontend, so the deployed server
will be shown blank. We have to add the plug in to handle the frontend.

## Step 7 to 12 - Add Gradle Plugin to Manage Frontend ([119ca4a](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/119ca4aad37967df0b31512054cdc1175118842e))

As suggested we used **https://github.com/Siouan/frontend-gradle-plugin**. To make Gradle use this plugin to manage frontend 
we have to add it to the plugin section on the gradle.build file:

>plugins {
> 
>id 'org.springframework.boot' version '2.6.6'
> 
>id 'io.spring.dependency-management' version '1.0.11.RELEASE'
> 
>id 'java'
> 
>**id 'org.siouan.frontend-jdk11' version '6.0.0'**
> 
>}

Now that we have the plugin, we need to configure it by adding the following lines to gradle.build:

>frontend {
> 
>nodeVersion = "14.17.3"
> 
>assembleScript = "run build"
> 
>cleanScript = "run clean"
> 
>checkScript = "run check"
> 
>}

One of the source files that we copied earlier from the basic tutorial was the webpack file, this one also needs to be configured.
This time in the package.json file:

>"scripts": {
> 
>"webpack": "webpack",
> 
>"build": "npm run webpack",
> 
>"check": "echo Checking frontend",
> 
>"clean": "echo Cleaning frontend",
> 
>"lint": "echo Linting frontend",
> 
>"test": "echo Testing frontend"
> 
>},

After this we can build our project again with the recent added plugin and configurations:

> **./gradlew build**

We can execute the recently built application:

> **./gradlew bootRun**
 
Now if we check our local address our webpage will be working has expected.

## Step 13 - Add a Task to Copy Generated jar Files to a "dist" Folder at Project Root ([eb0e7b0](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/eb0e7b035934438bce36dbdacfceb8e79d1d8d12))
<div style="text-align: justify">

As we have seen in [CA2](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_1\README_CA2_Part1.md) part 1 we have to create a
task with type copy and copy generated .jar files to ./dist folder. If this folder does not exist it will be created. Bur there
is a slight detail we should consider, if there are no .jar files, this task will fail. So we have to garantee that this task
depends on another. If we check the task list using:

> **./gradlew tasks**

We can see that there is a task named jar that assembles .jar files:

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\build_tasks.jpg" alt="drawing"/>

So the solution for this issue is to make this new task jar dependent. Gradle offers a few option to make a task dependent
on another or do them first or last. The doFirst command, makes the task execute first than everything else and the doLast
as the name stats, before everything else:

>task hello {
> 
>group ’devops’
> 
>description ’The hello task greets Gradle by saying "Hello Gradle"’
> 
>// Adding extra properties to a task
> 
>ext.message = "What’s up?"
> 
>// This is executed during the configuration phase.’
> 
>println "Configuring hello task."
> 
>}

>hello {
> 
>doFirst {
> 
>println "First time: " + message
> 
>}
> 
>}
 
>hello {
> 
>doLast {
> 
>println "Second time: " + message
> 
>}
> 
>}

>hello {
> 
>println ’This is executed during the configuration phase as well.’
> 
>}

There is a third option that is my personal favorite because we can make a task dependent on another instead of only before
or after, this can come in handy if we want a specific task to run in the middle of a build:

>**B.dependsOn A**

This means that task B depend on task A. Gradle executes task A everytime before executing task B. So we just need to
make our new task dependent on jar task:

>task backupJavaArchive(type: Copy, dependsOn: jar) {
> 
>from "./build/libs"
> 
>into "./dist"
> 
>}

</div>

## Step 14 to 17 - Add a Task to Delete All Files Generated by WebPack ([8ca4a09](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/8ca4a09e531cdeaa91f96381501ee7eb33307f2f))
<div style="text-align: justify">
After step 13 step 14 seems easy enough, but with a twist, because we want our task to be executed before the task clean.
That is, clean task should be dependant on our new delete task. We can achieve this by declaring the dependency after the task:

>task deleteWebPackChild(type: Delete) {
> 
>delete files("src/main/resources/static/built/")
> 
>}
>
>**clean.dependsOn deleteWebPackChild**

Now when we execute clean task using:

>**./gradlew clean**

Our delete task will be also executed. Finally, I checked out to our master branch and merged our changes:

>**git merge tut-react-gradle**

</div>

---

<center><h2>Implementation - Automation With Maven</h2></center>

## Step 1 - Create a New Branch ([a29fa2c](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a29fa2c387c080c55541fe230ab0f032c12e6886))

First we created a new branch:

> **git branch tut-basic-maven**

Then we moved to it:

> **git checkout tut-basic-maven**

See CA1 for more information.

## Step 2 - Create a Gradle Spring Boot Project ([a29fa2c](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a29fa2c387c080c55541fe230ab0f032c12e6886))

I created a spring boot maven project with **https://start.spring.io/** with the indicated dependencies: Rest,
Repositories, Thymeleaf, JPA and H2.

## Step 3,4 and 5 - Extract Generated Zip File to Project Folder and Copy Source Files ([a29fa2c](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a29fa2c387c080c55541fe230ab0f032c12e6886))

The generated file was extracted to the project folder. And the source files were replaced by the ones from the basic tutorial.
Then we could see default tasks from a maven project with:

> **./mvn verify**

This command builds the project, runs all the test cases and run any checks on the results of the integration tests to 
ensure quality criteria are met. In the console we can see every call that his being made.

## Step 6 - Running the Application ([a29fa2c](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a29fa2c387c080c55541fe230ab0f032c12e6886))

We can now run the application using spring boot:

> **./mvnw spring-boot:run**

This command has two very useful options to debug the build if we get an error:

> **./mvnw spring-boot:run -e**

> **./mvnw spring-boot:run -X**

This will deploy the server at http://localhost:8080/. At this stage we don't have any frontend, so the deployed server
will be shown blank. We have to add the plug in to handle the frontend.

## Step 7 to 12 - Add Gradle Plugin to Manage Frontend ([c50d268](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/c50d26853b11669fb1b15f7c44206ea33a25c5cb))

As suggested we used **https://github.com/eirslett/frontend-maven-plugin**. To make Maven use this plugin to manage frontend
we have to add it to [pom.xml](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_2_Alternative_Maven\pom.xml), 
just has the author recommends. See lines 11 to 16, 41 to 46 and 57 to 92. With these tasks
we can see one of the main differences between Maven and Gradle. Gradle is far simpler than Maven, we needed more than
the double of lines of code to do the same thing with Maven.
After this we can build our project again with the recent added plugin and configurations:

> **./mvnw spring-boot:run**

Now if we check our local address our webpage will be working has expected.

## Step 13 - Add a Task to Copy Generated jar Files to a "dist" Folder at Project Root ([fdb377f](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/fdb377f4bea1ff71ef29b654a6a388d717bf0211))
<div style="text-align: justify">
In my understanding, Maven is not as flexible as Gradle. It follows a structure divided in life cycle, phases and goals.
These are a set of tasks that are executed when the Maven build is run. <br>
Maven builds goes through a set of stages called build phases. And a build phase is made up of a set of goals that represent
specific tasks to be made on a specific phase that contribute to project management. <br>
That said, for this step we have to set a goal on a phase for Maven to be able to copy the files we want. I chose to copy
the files on the validate phase:

> **./mvn validate**

This command validates the maven project that everything is correct and all the necessary information is available. Now,
everytime this command is run files generated .jar files will be copied:

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\copy.jpg" alt="drawing"/>

See [pom.xml](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_2_Alternative_Maven\pom.xml) lines 93 to 119.
[Documentation](https://maven.apache.org/plugins/maven-resources-plugin/examples/copy-resources.html)
</div>

## Step 14 to 17 - Add a Task to Delete All Files Generated by WebPack [a11778a](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/a11778a0366db1643c99d2ab47732ac433a25d37)
<div style="text-align: justify">
Following the same logic that we used on task 13 we need to choose a phase to create our goal. Since we want to delete
something I chose the clean phase:

> **./mvn clean**

This command cleans the maven project by deleting the target directory. And we want to delete webpack generated files
as well, see [pom.xml](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_2_Alternative_Maven\pom.xml) lines 120 to 132.
Everytime clean command is executed, our created goal will be also executed:

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\delete.jpg" alt="drawing"/>

[Documentation](https://maven.apache.org/plugins/maven-clean-plugin/examples/delete_additional_files.html)

Finally, I checked out to our master branch and merged changes:

>**git merge tut-react-maven**

</div>

---

<center><h2>Implementation - Automation With Bazel</h2></center>

<div style="text-align: justify">
Bazel was my first option as an alternative building tool to Gradle. But unfortunately Bazel revealed to be a very complex tool,
at least for me. I spent some time trying to implement it but I could not advance in the implementation process and then decided
to switch to Maven since it was more familiar to me, and wanted to deliver a full report with alternative research and implementation.
Despite that, and since I tried to implement it I leave her what I have learned so far. <br>
With Bazel we need to define the project with a WORKSPACE file and the target to be built with a Build file: 

 * [WORKSPACE](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_2_Alternative_Bazel\WORKSPACE) 
 * [BUILD](C:\temp\devops\devops-21-22-lmn-1211790\CA2_Part_2_Alternative_Bazel\Build)

Like said before, Bazel has the capability to BUILD different parts of the project in different ways 
by defining several BUILD files to those paths:

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\bazel_structure.jpg" alt="drawing"/>

We can build a Bazel project using:

> **bazel build //:TutReact**

Despite my research I could not fix the issue. In my opinion it has something to do with the Spring Boot framework. Then
I decided that I should change alternative build tool to implement.

<br>

<img src="C:\temp\devops\devops-21-22-lmn-1211790\imgs\CA2\build_failure_bazel.jpg" alt="drawing" />

[Documentation](https://docs.bazel.build/versions/1.2.0/tutorial/java.html)
</div>

---

<center><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></center>

---



