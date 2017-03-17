[![Build Status](https://circleci.com/gh/reflectoring/infiniboard.svg?style=shield&circle-token=b5481c7e89b830ae2579de7a5303f495a96fc8b2)](https://circleci.com/gh/reflectoring/infiniboard) [![Gradle Status](https://gradleupdate.appspot.com/reflectoring/infiniboard/status.svg?branch=master)](https://gradleupdate.appspot.com/reflectoring/infiniboard/status) [ ![Download](https://api.bintray.com/packages/reflectoring/releases/infiniboard/images/download.svg) ](https://bintray.com/reflectoring/releases/infiniboard/_latestVersion)

<img src="https://raw.githubusercontent.com/reflectoring/infiniboard/master/doc/assets/infiniboard.png" alt="infiniboard" width="250px" height="250px" />  

# infiniboard
infiniboard is a customizable, general purpose project dashboard to assist you in gathering your most important project metrics in one place.

# getting-started

## start mongo db

Uncomment the port declaration in `docker-compose.yml`. <br>
Do NOT commit these changes.
```sh
$ docker-compose up -d mongo
```
Mongo DB now listens on localhost:27017

## start harvester
```sh
$ cd harvester
$ ../gradlew bootRun
```
harvester's Actuator API now listens on http://localhost:9090

## start quartermaster
```sh
$ cd quartermaster
$ ../gradlew bootRun
```
quartermaster's REST API now listens on http://localhost:8080/api/dashboards <br/>
quartermaster's Actuator API now listens on http://localhost:8090

## start dashy
```sh
$ cd dashy

# fetch all node dependencies
$ npm install

# use the local version of angular-cli installed by npm install
$ ./node_modules/.bin/ng serve
```
dashy now listens on http://localhost:4200


## setup IDE

Remove all existing IntelliJ project configurations and create the latest one by running:
```sh
$ ./gradlew cleanIdea idea
```

## building components
Each components has specific instruction on how it is build defined in its `Readme.md`.

## Want to contribute?
See the [contribution guide](https://github.com/reflectoring/infiniboard/blob/master/CONTRIBUTING.md).

## license

> The MIT License (MIT)
> Copyright (c) 2016 reflectoring
> 
> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
> 
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
