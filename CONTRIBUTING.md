# How to Contribute to this Repository

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
**Table of Contents**

- [Git Flow](#git-flow)
  - [So... how do you use git flow?](#so-how-do-you-use-git-flow)
    - [The easy way](#the-easy-way)
    - [The "do it yourself" way](#the-do-it-yourself-way)
- [How to add code](#how-to-add-code)
  - [1. Get the code from GitHub](#1-get-the-code-from-github)
  - [1.5. Set up `git flow`](#15-set-up-git-flow)
  - [2. Create a new branch off of `develop`](#2-create-a-new-branch-off-of-develop)
  - [3. Add your code!](#3-add-your-code)
  - [4. Connect your local branch to remote and push your code](#4-connect-your-local-branch-to-remote-and-push-your-code)
  - [5. Make a pull request back into `develop`](#5-make-a-pull-request-back-into-develop)
  - [6. Wait for review](#6-wait-for-review)
  - [7. See your code on the robot!](#7-see-your-code-on-the-robot)
- [Issues and not knowing what to do at the shop](#issues-and-not-knowing-what-to-do-at-the-shop)
  - [Making an Issue](#making-an-issue)
  - [Using Projects](#using-projects)
- [GradleRIO](#gradlerio)
  - [What is GradleRIO?](#what-is-gradlerio)
  - [Why do we use GradleRIO?](#why-do-we-use-gradlerio)
  - [Setting up your workspace](#setting-up-your-workspace)
    - [1. Download IntelliJ](#1-download-intellij)
    - [2. Install JDK](#2-install-jdk)
    - [3. Clone the repository](#3-clone-the-repository)
    - [4. Set up the workspace](#4-set-up-the-workspace)
    - [5. Import the project as `gradle`](#5-import-the-project-as-gradle)
    - [5.5. Set `JAVA_HOME`](#55-set-java_home)
    - [6. Profit](#6-profit)
- [Good Practices](#good-practices)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

DISCLAIMER: This guide assumes that you have prior git knowledge. PLEASE learn git before you attempt this. You WILL break things if you don't.

## Git Flow

This year, we are using **git flow**, a development model to make our repository all neat and pretty!

The main idea is to use separate branches for development and for features. The full blog post can be found [here][blogpost].

### So... how do you use git flow?

#### The easy way

- Install the git flow extension.
    - Follow [these][instructions] instructions.
    
That's it! You're done! Well... ALMOST done. Now you just need to know how to use git flow. A cheat sheet can be found [here][cheatsheet]

#### The "do it yourself" way

The blog gives a good explanation on how to manually use commands to use the git flow technique. Although this is only recommended for people experienced in git, you can try it if you want. Just don't delete all our code. 

## How to add code

So let's say you have a great idea for the robot, such as better code for the shooter or something. How would you put this code on GitHub?

### 1. Get the code from GitHub

If you haven't already, clone the repository into a folder:

`git clone https://github.com/RoboEagles4828/2017Robot.git`

Now `cd` into the repository.

### 1.5. Set up `git flow`

If you are using the git flow extension, set it up using:

`git flow init`

### 2. Create a new branch off of `develop`

Since all features must branch off develop, make a branch from develop:

With extension: `git flow feature start <name>`

Without extension: `git checkout -b <name> develop`

### 3. Add your code!

Type up your code in whatever text editor you choose, and commit using:

`git commit -a -m "Title message" -m "Description (optional)"`

### 4. Connect your local branch to remote and push your code

Now, connect your branch to the remote and push your commits:

`git push --set-upstream origin/<name> <name>`

### 5. Make a pull request back into `develop`

**EXTENSION USERS: This is EXTREMELY important! DO NOT use `git flow feature finish <name>`! Since our branches have protection, this will break things, most likely deleting your code!**

Go to GitHub. On the repository page, you should see a tab labeled "Pull Requests". Click it.

On the right hand side, click "New pull request".

Near the top of the page, there should be 2 dropdowns, each with `master` selected. Switch the base one to `develop` and the compare one to your branch. Click "Create Pull Request".

Create a nice title for yout pull request, and click "Create pull request".

### 6. Wait for review

Near the bottom, you will see some checks. You must wait for these to finish before merging.

You will also need a person to review your code. Just contact any programmer on Slack, and they'll probably look it over for you and merge it.

### 7. See your code on the robot!

Now that the code is in develop, it is officially a part of the robot code! 

## Issues and not knowing what to do at the shop

We've all experience this before. You get to the shop, get out your computer, open up IntelliJ (or, god forbid, Eclipse), and sit there for 30 minutes, not knowing what to do.

Say no more! Introducing, Issues and Projects!

Issues are basically problems with the code, and Projects are a place to store all the TODO's and such.

### Making an Issue

Found a bug in the code? Want a feature to be implemented on the robot? Here's how to make an Issue.

1. Click the "Issues" tab on the toolbar
2. Click "New Issue"
3. Type the Issue and click "Submit new issue"
4. On the right side, assign people to the Issue

That's it for making an Issue!

### Using Projects

Projects can be found at the GitHub organization page or the repository page in the "Projects" tab. This is basically a running list of things to do.

If you want to do a thing, click the dropdown arrow and then "Convert to Issue". This allows you to make an Issue for that card! When finished, drag to the appropriate section.

## GradleRIO

### What is GradleRIO?

GradleRIO is a powerful plugin that allows FRC teams to produce and build their code without being limited to the Eclipse IDE. 
It allows you to use it with Eclipse, IntelliJ IDEA or any IDE of your choice. GradleRIO also allows you to build and deploy your code to the RoboRIO from the command-line. Finally it has a myriad of small but useful benefits over the default build system like lowered deploy times, better dependency handling, and the ability to easily integrate unit tests.

See [here][gradlerio] for more information.

### Why do we use GradleRIO?

In the past we've had to put up with the ANT build system supplied by FIRST but due to its limitations we decided to switch to GradleRIO for the 2017 season and onward.

### Setting up your workspace

GradleRIO can be used in IntelliJ and Eclipse (but why would you use Eclipse?). This guide will be focused on IntelliJ because I hate Eclipse.

#### 1. Download IntelliJ

Just get it from the site.

#### 2. Install JDK

Install the most recent JDK from Oracle. Follow the prompts.

#### 3. Clone the repository

You should have this done, but if you haven't, use this command: 

`git clone https://github.com/RoboEagles4828/2017Robot.git`

Don't forget to `cd` into the directory.

#### 4. Set up the workspace

Run this command to set up the workspace:

Windows:   `gradlew idea`

OSX/Linux: `./gradlew idea`

#### 5. Import the project as `gradle`

Go into a file explorer and open the directory with the files. Double click the `.ipr` file.

When in IntelliJ, there should be a pop-up that asks you to import the project as Gradle. Click import.

Click "Import" to use the default options.

#### 5.5. Set `JAVA_HOME`

There is a chance that your `JAVA_HOME` variable will not be set. If you run into a problem with importing in the last step, follow [these instructions][javahome] to set it.

#### 6. Profit

That's it! You're now all set to mess with the code!

## Good Practices

These practices are good for everything. Use them!

- Try to use an insightful title. Bonus points for including the specific error if you can figure it out, but keep things concise (one line).
    - Great: "Null pointer exception in Toaster class when setting temperature" or "Out of memory error while saving custom temperature preset"
    - Good: "Problem with vision when running with toaster mode on" or "Pancake cooking functionality?"
    - Not good: "The robot broke" or "I want something to be added"
- Include exactly what happened if the Issue is about a bug. Try to be as specific as possible, and document the exact steps that you followed to produce the issue. If you have logs, error codes, or dumps, you should include those inside code blocks in the Issues. A sequence of three backticks (`) indicate the start and end of code blocks.
- Don't use Issues for random information, such as documentation. See [here][ripnikhil] for an example of what *not* to do.
- When referencing other Commits, Pull Requests, or Issues, use it's `#` code. This can be found next to the name of the commit/issue/pull request. Example "Jackie, you totally broke the whole robot in #123"
- When writing a title for anything, use the imperative form of the verb.
    - Good: "Fix Vision"
    - Not good: "Fixed Vision"

[blogpost]: http://nvie.com/posts/a-successful-git-branching-model/
[instructions]: https://github.com/nvie/gitflow/wiki/Installation
[cheatsheet]: http://danielkummer.github.io/git-flow-cheatsheet/
[ripnikhil]: https://github.com/RoboEagles4828/2017Robot/issues/10
[gradlerio]: https://github.com/Open-RIO/GradleRIO
[javahome]: https://docs.oracle.com/cd/E19182-01/820-7851/inst_cli_jdk_javahome_t/
