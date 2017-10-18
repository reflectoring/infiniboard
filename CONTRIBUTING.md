# Contributing to infiniboard

Thanks for donating your time to developing infiniboard! This is the most
valuable contribution you can make.

This guide gives an overview of infiniboard and some development guidelines
to make it easier for new contributors to get up and running. If you have
any questions, feel free to get in touch.

## Overview of infiniboard components
Major components are documented via a README file in the folder of the component.

## How can I contribute?

Any kind of contribution is welcome as a pull request.
If you are unsure on how to fork a repository and then create a pull 
request from your fork, please read [this blog post](http://www.reflectoring.io/hacks/github-fork-and-pull/)
for a quick guide.

### Pick an Issue
You can pick any issue from the [issue tracker](https://github.com/reflectoring/infiniboard/issues). 
We try to document all feature ideas, tasks and bugs as issues in the tracker.
Issues marked with the tag ["up-for-grabs"](https://github.com/reflectoring/infiniboard/issues?q=is%3Aissue+is%3Aopen+label%3Aup-for-grabs) are especially documented so that 
new contributors should get an idea of what is to do. You might want to
start with one of those tasks. You can just as well pick any other task, though.
If you have any questions, get in touch.

### Submit an Idea
If you have an idea, submit it to the [issue tracker](https://github.com/reflectoring/infiniboard/issues)
for discussion. We will let you know our thoughts. If you want to develop your idea
yourself, you can do so and submit a pull request.

## Coding Conventions
Below are some hints on conventions used in this project. If you are unsure about
any, just get in touch. During a pull request review, we will also check these
conventions. Fear not, the worst thing that may happen if you do not follow them
is that we might propose some changes to a pull request you submitted.

### Styleguide
For java code style, the gradle plugin ["spotless"](https://github.com/diffplug/spotless) has been included in the build to enforce the
[Google Java Code style](https://google.github.io/styleguide/javaguide.html). Builds will fail if 
the code does not comply to the style guide. To apply the style guide simply call 
`gradlew spotlessApply`. Only after applying the style guide should you push your changes.

**Hint:** in order for automatic style guide enforcement to work, you have to **disable**
automatic code formatting on check-in in your IDE!

### Documentation
Please keep documentation up-to-date when changing the code. Documentation
is made up of the following elements:

* Documentation of the REST API. This documentation is made with [AsciiDoctor](http://asciidoctor.org/) and
  [Spring RestDocs](https://projects.spring.io/spring-restdocs/). Example requests
  and responses are generated automatically from the integration tests covering
  the REST controllers. The documentation files are [here](https://github.com/reflectoring/infiniboard/tree/master/quartermaster/src/main/asciidoc).
* README.md files in the folders of all main components
* Javadoc: please provide sensible javadoc of at least public API
* This contribution guide: this guide is not carved in stone, so when things change,
  change this guide. 

### Setup development environment

#### Create IntelliJ project
Remove all existing IntelliJ project configurations and create the latest one by running:
```sh
$ ./gradlew cleanIdea idea
```

#### Start MongoDB
##### Standalone
To store data infiniboard uses [MongoDB](https://github.com/mongodb/mongo). If you have a local MongoDB running which listens on localhost:27017, just make sure it is running before continuing.

##### Docker instance 
If you do not want to setup a local MongoDB, you can easily start a Docker instance like described below.
Therefore you need to install [Docker 17.06.0+](https://docs.docker.com/engine/installation/) and
[Docker Compose 1.14.0+](https://docs.docker.com/compose/install/) first.

Afterwards uncomment the port declaration in `docker-compose.yml` of service `mongo`. <br>
**Do NOT commit these change.** This change will be rejected in the pull request review process as it breaks the build
on the build server.
```sh
$ docker-compose up -d mongo
```
Mongo DB now listens on localhost:27017

##### clear MongoDB data
The MongoDB data gets stored in an seperate docker volume, so even if you destroy the mongo container using
`docker-compose down` for example you won't loose your data.  From time to time however it might be necessary to
clear the data. 

Make sure no container is using mongodb
```sh
$ docker-compose down
```

figure out the volume name
```sh
$ docker volume ls
DRIVER              VOLUME NAME
local               infiniboard_db-data
```

**Hint:** The name of the volume depends on the directory the `docker-compose.yml` file lives in.

After getting the volume name, erase the volume
```sh
$ docker volume rm <name of volume>
```

#### Start mongo-express
To browse the local docker MongoDB, you can start an pre configured [mongo-express](https://github.com/mongo-express/mongo-express) instance.
```sh
$ docker-compose up -d mongoExpress
```
The mongo-express UI now listens on http://localhost:8081

#### Start harvester
```sh
$ ./gradlew :harvester:bootRun
```
harvester's Actuator API now listens on http://localhost:9090

#### Start quartermaster
```sh
$ ./gradlew :quartermaster:bootRun
```
quartermaster's REST API now listens on http://localhost:8080/api/dashboards <br/>
quartermaster's Actuator API now listens on http://localhost:8090

#### Start dashy
```sh
$ cd dashy

# fetch all node dependencies
$ npm install

# use the local version of angular-cli installed by npm install to start the development server
$ ./node_modules/.bin/ng serve
```
dashy now listens on http://localhost:4200
