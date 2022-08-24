<center><h1>DevOps</h1></center>
<center><h2>Class Assignment 1</h2></center>
<center><h2>CA1 - Version Control Systems</h2></center>

---

## Overview

<div style="text-align: justify">The purpose of this assignment is to learn and explore a Version Control System through practice its functionalities. 
The proposed VCS for this assignment was git. 
It is also required of us to research an alternative tool to git, regarding version control features,
and implement it in the same way and compare both of them. The alternative tool chosen for this assignment was Mercurial.
We need a place to store our data to be controlled with our VCS tool, that is, a repository.
Since we are using git and its alternative mercurial, we will be using different remote repositories to test and learn
each tool. We will be using BitBucket for git and Source Forge for mercurial.
This is a necessity since BitBucket it´s not compatible with mercurial anymore.</div> 

[Sunsetting Mercurial](https://bitbucket.org/blog/sunsetting-mercurial-support-in-bitbucket)

<div style="text-align: left">Every step of this reported was done using the command line on the Windows terminal app.
Every command listed here was used on assignment development. Commits will be referenced for every step that
those types of commands were used.</div>

---

## Analysis

<div style="text-align: justify">Version Control System are used to track changes done to a project, mainly used on team development environments,
so that every team member can contribute to the project by developing on its local machine then sending (committing)
changes to a shared, remote (in most cases) repository.
"Version control is a system that records changes to a file or set of files over time so that you can recall specific
versions later (...) It allows you to revert files back to a previous state, revert the entire project back to a
previous state, compare changes over time, see who last modified something that might be causing a problem, who
introduced an issue and when, and more. Using a VCS also generally means that if you screw things up or lose files,
you can easily recover. In addition, you get all this for very little overhead." (Scott Chacon and Ben Straub in Pro Git)
<br> As mentioned before, in this assignment we will be using Git, "Git is a free and open source distributed version control 
system designed to handle everything from small to very large projects with speed and efficiency." (https://git-scm.com/)<br>
Also we will be using Mercurial as the alternative, "Mercurial is a free, distributed source control management tool.
It efficiently handles projects of any size and offers an easy and intuitive interface." (https://www.mercurial-scm.org/)<br>
There are two types of version control systems, centralized version control systems (CVCS) and distributed version control systems (DVCS).
Nowadays DVCS are more common, with Git being the market favorite, but every tool has its pros and cons.
With CVCS  you have a single “central” copy of your project on a server and commit your changes to this central copy. 
You pull the files that you need, but you never have a full copy of your project locally. Some of the most common version 
control systems are centralized, including Subversion (SVN) and Perforce.
There are some benefits with this approach, because centralized systems are easier to understand and make use of, we can access level control
at the directory level, that means, that we can choose which part of the project some people has access to and performs better with binary files.
The down side is that with CVCS you need to be connected to the internet all the time since you don't have the entire project
on your local machine, in example, you need it for build operations<br>
With DVCS you don't rely on a central server to store all the versions of a project’s files. Instead, you clone a copy 
of a repository locally so that you have the full history of the project. Git and Mercurial are  two common distributed version control systems.
This approach may sound a bit too much, but in practice, it is not a problem because the majority of programming projects
consist on plain text files, so hard disk drive and download(pull)/upload(push) will not be a problem in most cases. On the other hand,
branching and merging processes are much easier, performance wise DVCS are better and you don't need to be connected to the internet all the time
since you have all project in you local machine in you version controlled repository.
Has we will see through the implementation of both Git and Mercurial, they are both very similar, not only in the way they work
but also in the similarity of the commands. In some cases the only change is the keyword for the VCS git for Git and
hg (chemical symbol for mercury) for mercurial.<br>
Since Git is more complex than Mercurial, because Git has a more numerous command structure, you need to know 
it well before using it effectively. A less trained developer can damage a whole project if not careful.<br> 
One of the main difference between Git and Mercurial is the branching system. Mercurial creates branches and they are 
treated has part of a commit. With Git branches are not created only because something has changed, they are a new pointer
to an exact time in the history, which makes Git more lightweight than Mercurial regarding branch management. 
The branching mechanism in Git, is one of the main reasons why so many people adopt it.<br>
Git allows history changing but with Mercurial we can not change a project history.<br>
Commit tracking in Mercurial is simpler because it is sequential (1,2,3,4,etc.), with git they are referenced by a hashcode<br>
Finally they have something in common that are rollback operations, they work in the same way in both DVCS.

To list differences mentioned above:

| [Source](https://www.incredibuild.com/blog/mercurial-vs-git-lets-examine)  | Git  | Mercurial  | 
|---|---|---|
| Implementation  | Git can take longer for teams to ramp up given its higher level of complexity in commands and repository structure  |  Simpler and more intuitive commands can help teams to quickly ramp up adoption |
|  Branching | A branch is a pointer to a commit (SHA)  |  A branch is embedded in a commit; branches cannot be renamed or deleted |
| History  | Mutable through rollback, cherry-pick, rebase  | Immutable beyond rollback  |
|  Revision Tracking | Each revision unique by calculated SHA-1  | Incremental, numerical index of revision (0, 1, 2, etc)  |
| Rollback  | Supported via revert command; arbitrary via rebase and cherry-pick  | Supported via backout, revert commands  |

In conclusion when choosing between Git and Mercurial we need to consider, if we need a more complex or simpler DVCS to
implement, the type of history tracking. The way we are going to work, are we going to need a lot of branches? If so,
I recommend Git, otherwise Mercurial could be a good solution since its simpler for simpler projects.<br>
From my simple and not long experience, after the conclusion of this assignment, I consider that Git is the best option
not only for the technical reasons mentioned previously, but also considering my user experience I found fewer obstacles
while using Git than using Mercurial. Git commands are more intuitive and self-explanatory, while with Mercurial some commands
don't even return an informative message when executed. One of those obstacles was finding a remote repository to implement
on. It is very easy to find a repository compatible with Git but not so much for Mercurial.<br>
If I had to choose I would choose Git for any project that I needed to version control, because, I found it easier to
use, understand and study.

Now in the following sections the implementation will be demonstrated step by step with the list of commands that were used and explore
while developing the assignment. A brief explanation of the used commands is also described.

PS: Extra training was made [here](https://bitbucket.org/pmonteiro1211790/devops_training/src/master/), so that the original repository wouldn't be a mess.

</div>

---

<center><h2>Implementation - Version Control with Git</h2></center>

---

## Step 1 - Initialize/Clone a Repository ([0d0945a](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/0d0945aebab135d157289ec43ff73af8c49c31f8))
<div style="text-align: justify">
There are two ways to get a Git repository:

* a) On your local machine on a folder that it is not under version control using the following command:
        
> **git init**

* b) By Cloning an existing Git repository:

> **git clone https://PMonteiro1211790@bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790.git**

In this assignment it was used the second option. First it was created an empty repository on bitbucket.org and then
cloned it to a local machine. From that point forward that directory is under version control with Git. We can confirm
that by checking that a new hidden folder was cloned to our directory, named .git, this file contains all necessary
files to Git. This folder should not be edited.
</div>
## Step 2 - Making Changes and Checking Staging Area
<div style="text-align: justify">
As suggested we are using a sample project "Tutorial React.js and Spring Data REST", so the first step was to copy this
sample Project to our local directory that is now under version control with .git.
At this point we have an empty remote repository and a recently added project on our local machine.
Now we can see what files have been modified, removed or added with the following command:

> **git status**

The command above will tell on what branch we are currently working on and if locally we are "up to date" with the 
remote repository, that is, our local branch has not diverged from the one stored remotely. Also, it will tell you the 
untracked files present in your local machine, they will be displayed under "Untracked Files" label normally in red. 
At this point all recently copied project is untracked. Untracked means that git sees files that aren't present in 
previous snapshots/commits. Now if we want to send untracked files to our repository we have to add them to the staging 
area, otherwise git won't commit them. To do this simply use the following command:

> **git add <untracked_file_name>/<folder_path>**

Of course, it would be too much work to add file by file in every commit by referencing their names, so as any other tool
git has an option to track/stage all changes to your folder under version control. Note that instead of a file name
we can use a folder path, git will stage all files inside that folder recursively.
<br>
For staging all untracked files we have two options:

> **git add -A**  ("-A" option for all)

or

> **git add .**  (at the root of the version controlled folder)

If we apply git status command again, previously untracked files now are under "Changes to be committed" label normally
displayed in green. 
If we make changes to a file that has been added to the staging area and use the **git status** command, this file will
appear twice once under "Changes to be committed" label in green and other under "Changes not staged for commit" in red.
This means that we have a snapshot of the older version of the file to be committed and other newer version of the file
that is unstaged for commit. At this point we can use a command to see the difference between the older version and the
newer version:

> **git diff**

will tell us what changes have occurred on the file.
By using **git add** again we are ready to commit changes tou our remote repository.

It is possible to remove a file using git:

> **git rm <file_name>**

If by any means the file was added to the staging area we have to force its removal applying the "-f" option:

> **git rm -f <file_name>**
</div>

## Step 3 - Committing Changes ([0d0945a](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/0d0945aebab135d157289ec43ff73af8c49c31f8))
<div style="text-align: justify">
Now that we have our staging area with the files that we want we can commit select files by using the following command:

> **git commit**

Git ensures that every commit has a commit message. By using the command above it will open the default text editor
for a commit message to be written.
If we want to write the commit message directly on the command line we should use "-m" option:

> **git commit -m "Commit Message"**

Every commit will return a message after it succeeds, it will tell us how many files were changed, inserted and deleted.
More importantly will tell us the branch that files were committed to and the hashcode of the recently done commit.
It is important to know that every commit will be referenced by a unique hashcode(SHA-1 checksum). We can use this
hashcode to identify a commit and reference on the command line if we want to use git commands on that specific commit.
When we use the commit command a snapshot of our staging area is recorded to be sent to the remote repository.
If we want to skip **git add** to files that are tracked but unstaged we can use the "-a" option and git will add
automatically all tracked unstaged files to the new commit:

> **git commit -a -m "Commit Message"**

> **git commit --amend -m "New commit message"**

> **git push --force <remote_name> <branch_name>**

There is a way to check commit history without resorting to our repository GUI:

> **git log**

This command his very self-explanatory, it prints the list of commits starting from the most recent to the least recent.
For every commit lists the hashcode, branch or tag(if it was tagged), author, date, time and commit message.
It is possible to get a less verbose list using:

> **git log --oneline**

With the "--oneline" option the command returns only hahscode(only the first 7 numbers), branch or tag(if it was tagged)
and commit message.

If we want to see a simple graphical representation of version controlled history use:

> **git log --oneline --decorate --graph --all**

<br>
Now we are ready to send our files to our remote repository.
</div>

## Step 4 - Pushing Changes to Repository

For sending our committed files to our remote repository we need to use:

> **git push remoteURL <branch_name>**

With bit bucket when we clone a repository by default branch name will be "Master" and the remote URL will be replaced
by the alias "Origin":

> **git push origin master**

If we don't specify and use only **git push** default values will be used.
We can check the remote repository URL using:

> **git remote -v**

Here it will show the fetch URL and push URL. They can be different so that we can pull information from one URL and 
push it to another.

## Step 5 - Tagging ([0d0945a](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/0d0945aebab135d157289ec43ff73af8c49c31f8) / [15aec9d](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/15aec9daa5670678cdcf13b69fddaeafd961af2f) / [0d7aa54](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/0d7aa5438b24e81e9f77d6e526dac92d2277a0b1) / [1996073](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/1996073f0ecc9c78406d5d5d7348bbd00c14cc6c) / [3d610b4](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/3d610b4c17ae030f081928df1d2fb8f736fb736a) / [9ba3ebc](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9ba3ebcd45fc6e4964434f89ec76e22d3fc4301f) / [f149948](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/f149948c551ae736a3973076da66b7a455ba6232))
<div style="text-align: justify">
In this assignment we were supposed to tag our commits every time a change was made. It was thoroughly used on the commits 
listed above using commands listed below.
Tagging is a very useful feature, it is the ability to give an alias to a commit, normally this is used to name commits
significantly, that is, instead only of a message and the hashcode that does not provide additional information we can have a
tag that refers to a specific software version for example v1.0.0, v1.0.1, etc., making easier to track a specific commit.
To tag a commit we can use the following:

> **git tag <tag_name>**

This creates a tag for the last commit, if we want to tag an older commit we have to use its hash code:

> **git tag <tag_name> <commit_hash>**

Tags may include message as well, if we want to add a message along with a tag we must use:

> **git tag -a tagName -m "Tag Message"**

We can list all tags with:

> **git tag**

We can also see the tag data with commit data using:

> **git show <tag_name>**

There is one important detail on tags, git push does not push tags with it, so we have to push tags as we push files:

> **git push origin <tag_name>**

Normally humans make mistakes so there can be a typo on a tag and that wrongly written tag is pushed to remote repository.
Git provides a way to delete tags from local and remote repository.
Deleting from local repository:

> **git tag -d <tag_name>**

Deleting from remote repository:

> **git push origin -d <tag_name>**
</div>

## Step 6 - Reverting Current Version to an Older One ([3d610b4](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/3d610b4c17ae030f081928df1d2fb8f736fb736a) / [9ba3ebc](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9ba3ebcd45fc6e4964434f89ec76e22d3fc4301f))
<div style="text-align: justify">
During this assignment it was added support to a new field to record the years of the employee, and only integer values
are allowed. For the sake of further knowledge improvement, the application was then made to support only String values instead of
integer on Job Years field, and then reverted to the version where it was integer.
To revert a commit to a previous version we must use:

> **git revert <commit_hash>**

This command will open your default text editor twice because after you push your changes two commits will be on your log,
first for the **reverted from** version, and the second vor the **reverted to** version, then two messages will be needed.
[3d610b4](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/3d610b4c17ae030f081928df1d2fb8f736fb736a) / [9ba3ebc](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/9ba3ebcd45fc6e4964434f89ec76e22d3fc4301f)
After we push our changes we will be using the same version we had at commit [0d7aa54](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/0d7aa5438b24e81e9f77d6e526dac92d2277a0b1).
</div>

## Step 7 - Branching ([d62d58d](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/d62d58dc386c7ed4f592361ff6f9b50590c19821) / [ea91dc0 ](https://bitbucket.org/pmonteiro1211790/devops-21-22-lmn-1211790/commits/ea91dc0c11ec414a1331a9d4e461803b1f31b9fe))
<div style="text-align: justify">
The final part of this assignment has focused in branches. As we know, every folder under version control has at least
one branch, the master branch. Git gives a way to create other branches using, on the assignment branches were created using:

> **git branch <branch_name>**

This command creates an exact copy of the current branch you are in and creates another one. This is very useful because
we can have a stable version of our application on master branch and develop new feature or fix bugs on a new branch that
is an exact copy of our application without damaging it. the **git branch** command does not change to the branch created
we have to "move" between branches manually using:

> **git checkout <branch_name>**

The following command was used to view all created branches locally:

> **git branch**

On that list current branch, the one pointed at by HEAD, will be marked with an asterisk (*).

Like tags branches need to be pushed to remote repository, but when one new branch is created its upstream information
it's not defined, that is, we have to set where it should upload its information, URL and branch name, for that we should use:

> **git push --set-upstream origin <branch_name>**

This need to be done only one time. We can have branches that exist locally and remotely, if we want to list the ones remotely
we must use the -r option:

> **git branch -r**

If we want to list all, remote and local branches we use the -a option:

> **git branch -a**

If we want to list more information we can use, and combine them with -r and -a option:

> **git branch -v**

and

> **git branch -vv**

When we are working in different branches we can see the log in a simplistic graphical way using:

> **git log --oneline --decorate --graph --all**

Of course for every creation command there is a deletion command, I used this command to delete a branch from locally:

> **git branch -d <branch_name>**

If we want to delete a branch on our remote repository this command should be used instead:

> **git push --delete <remote_name> <branch_name>**

We can also rename a created branch, first we should move to the branch that we want to rename
using **git checkout**, then we should use:

> **git branch -m <new_name>**

If the branch is remote we should push it using:

> **git push origin -u <new_name>**

This creates an exact copy of the branch including config and reflog files. Then we just need to delete the older branch
using **git branch -d** or **git push --delete** depending on the case.
In the final step of the assignment we needed to merge two branches, this is the process of merging the information of one
branch into another. If we are working in a branch and want to merge our work with the master branch, first we need to 
**git add** all changes, then move to the branch we want to merge into using **git checkout**, then use the following: 

> **git merge <branch_name>**

If there are no conflicts the merge will occur and the last thing left to do will be push.
But if there are conflicts, they must be resolved. To provoke this, a conflito.txt file was created on bitbucket to simulate
that other developer added this file, then the file was pulled and edited differently in the local machine and in bitbucket
to provoke a conflict. After the **git merge** command, our version controlled folder enters a mode that does not allow
more pull or pushed information until the conflict is resolved:

<!---![Conflict Pull](C:\temp\devops\devops-21-22-lmn-1211790\CA1\ConflictPull.png)-->
<img src="/CA1/ConflictPull.png">
<!---![Conflict Push](C:\temp\devops\devops-21-22-lmn-1211790\CA1\ConflictPush.png)-->
<img src="/CA1/ConflictPush.png">
![Conflict Push](/CA1/ConflictPush.png)

Now we can see the commits that are conflicting using:

> **git log --merge**
 
Then I resolved the conflict by matching manually through the text editor. After that the merge concluded and allowed 
pushing non-conflicting information. 
</div>

---

<center><h2>Implementation - Version Control with Mercurial</h2></center>

---

## Step 1 - Initialize/Clone a Repository ([13197e](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/13197ef0a17622d5ab020800e27be5e0ae4c8579/))
<div style="text-align: justify">
There are two ways to get a Mercurial repository:

* a) On your local machine on a folder that it is not under version control using the following command:

> **hg init**

* b) By Cloning an existing Git repository:

> **hg clone https://pmonteiro121179@hg.code.sf.net/p/devops-21-22-lmn-1211790/code devops-21-22-lmn-1211790-code**

Mercurial was the investigated option for this assignment. First it was created an empty repository on codebasehq.com and then
cloned it to a local machine. From that point forward that directory is under version control with Mercurial. We can confirm
that by checking that a new hidden folder was cloned to our directory, named .hg, this file contains all necessary
files to Mercurial. This folder should not be edited.
</div>

## Step 2 - Making Changes and Checking Staging Area
<div style="text-align: justify">
As suggested we are using a sample project "Tutorial React.js and Spring Data REST", so the first step was to copy this
sample Project to our local directory that is now under version control with Mercurial.
At this point we have an empty remote repository and a recently added project on our local machine.
Now we can see what files have been modified, removed or added with the following command:

> **hg status**

The command above will tell you only the list of files that are untracked/tracked. In comparison, mercurial gives less information
than git. If you are up-to-date with the repository, nothing will be returned.

At this point all recently copied project is untracked. Now if we want to send untracked files to our repository we have 
to add them, otherwise mercurial won't commit them. To do this simply use the following command:

> **hg add <untracked_file_name>/<folder_path>**

For staging all untracked files we have two options:

> **hg add**  

At this point we can use a command to see the difference between the older version and the newer version:

> **hg diff**

The command above will tell us what changes have occurred on the file.
It is possible to remove a file from the tracked files list using:

> **hg forget <file_name>**
</div>

## Step 3 - Committing Changes ([bcd4b2](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/bcd4b2e21eed586ff0c42a4bccfcf5a70ac7689f/))

Now that we have our staging area with the files that we want, we can commit select files by using the following command:

> **hg commit**

Mercurial as well ensures that every commit has a commit message. By using the command above it will open the default text editor
for a commit message to be written. If we want to write the commit message directly on the command line we should use "-m" option:

> **hg commit -m "Commit Message"**

If we want to change a commit message that is wrong we should use:

> **hg commit --amend -m "New commit message"**

Unlike Git, Mercurial does not return a message if the commit was successful. We have to check commit history:

> **hg log**

We can see a simple graphic history of version controlled folder with: 

> **hg log --graph**

Like Git, in Mercurial every commit will be referenced by a unique hashcode(SHA-1 checksum). Mercurial adds a little extra,
commits are also referenced sequentially by number starting from zero. We can use this
hashcode to identify a commit and reference on the command line if we want to use mercurial commands on that specific commit.
This command his very self-explanatory, it prints the list of commits starting from the most recent to the least recent.
For every commit lists the hashcode, branch or tag(if it was tagged), author, date, time and commit message.

Now we are ready to send our files to our remote repository.

## Step 4 - Pushing Changes to Repository
<div style="text-align: justify">
For sending our committed files to our remote repository we need to use:

> **hg push <remote_URL> <branch_name>**

With code base when we clone a repository by default branch name will be "default" and the remote URL will be replaced
by the alias "default":

> **hg push default default**

If we don't specify and use only **hg push** default values will be used.
We can check the remote repository URL using:

> **hg paths**

Here it will show the fetch/push URL. They can be different so that we can pull information from one URL and
push it to another.
</div>

## Step 5 - Tagging ([4dddaa](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/4dddaa98bd0a8d8b5482d1155b940c46d2945dcf/))

To tag a commit we can use the following:

> **hg tag <tag_name>**

This creates a tag for the last commit, if we want to tag an older commit we have to use its hash code:

> **hg tag <tag_name> <commit_hash>**

Tags with Mercurial doesn't work the same has Git. The tag is interpreted like a commit, so a new commit is made when we
push after tagging any other commit.

We can list all tags with:

> **hg tags**

Mercurial provides a way to delete tags from local and remote repository.
Deleting from local repository:

> **hg tag --remove <tag_name>**

This will interpret tag has a commit message and will be sent to remote repository

## Step 6 - Reverting Current Version to an Older One ([d7efd3](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/d7efd3b20a8fcf414ab806bd2ef543372d235886/))
<div style="text-align: justify">
For the sake of further knowledge improvement, a commit was made with the purpose to be reverted. 
To revert a commit to a previous version we must use:

> **hg backout -r <commit_number / commit_hash>**

This command will open your default text editor to write a revert message:
[93924b](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/93924b59a8e46c07dadf43c29132d5d9098b024c/)
After we push our changes we will be using the same version we had at commit [d7efd3](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/d7efd3b20a8fcf414ab806bd2ef543372d235886/)
</div>

## Step 7 - Branching ([bd234](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/bd2341456b4259d5e4be940c7331942dc3a283f3/) / [79f0e](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/79f0e1fc0490ab7c85ffd116c409aff860a449b3/) / [ff767](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/ff767b3cc9c5e721e62f506f3f3b5c28fbf165a8/) / [aeb1b](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/aeb1b02c7d60248a4127822b66e803546721788d/) / [c97fb](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/c97fb0a11d8343e378a06d59892ea8e01774629e/) / [c1302](https://sourceforge.net/p/devops-21-22-lmn-1211790/code/ci/c1302b373f7af9b2365255c2d86db680ca66c012/))
<div style="text-align: justify">
Like Git, Mercurial gives a way to create other branches:

> **hg branch <branch_name>**

This command creates an exact copy of the current branch you are in and creates another one. This is very useful because
we can have a stable version of our application on master branch and develop new feature or fix bugs on a new branch that
is an exact copy of our application without damaging it. We can list all branches using:

> **hg branches**

We can see thar our recently created branch it is **not** on the list. We need to push it to remote server for it to be accessible:

> **hg push --new-branch**

Branches in Mercurial are treated has part of a commit.
After we push created branch we are automatically sent "there".
We can "move" between branches using:

> **hg checkout <branch_name>**

When we are working in different branches we can see the log in a simplistic graphical way using:

> **hg log --graph**

Of course for every creation command there is a deletion command, but with Mercurial we cannot change its history,
so there is no way to delete a branch, only to close it:

> **hg commit --close-branch -m "Commit Message"**

Now this branch will be closed, we can see closed branches using:

> **hg branches --closed**

We can not rename a branch with mercurial, we need to crate a new one, then close the one that we want to be renamed 
and replace it wit the one created, using commands described above.
In the final step of the assignment we needed to merge two branches, this is the process of merging the information of one
branch into another. If we are working in a branch and want to merge our work with the master branch, first we need to
**hg add** all changes, then move to the branch we want to merge into using **hg checkout**, then use the following:

> **hg merge <branch_name>**

If there are no conflicts the merge will occur and the last thing left to do will be push.
But if there are conflicts, they must be resolved. To provoke this, the same line on the same file was edited on different branches
to provoke a conflict. After the **hg merge** command, our version controlled folder enters a mode that does not allow
more pull or pushed information until the conflict is resolved, also the conflicted file is branded on the area that the conflict
occurred:

<!---![Conflict Pull](C:\temp\devops\devops-21-22-lmn-1211790\CA1\ConflictMergeMercurial.png)-->
<img src="/CA1/ConflictMergeMercurial.png">

<img src="/CA1/MergeMarks.png">

Then I resolved the conflict by matching manually through the text editor. After that we need to use this command to
mark the conflicts has resolved:

> **hg resolve --mark**

the merge concluded and allowed pushing non-conflicting information.

</div>

---

<center><h2>End Of Report - @author Pedro Monteiro 1211790@isep.ipp.pt</h2></center>

---



