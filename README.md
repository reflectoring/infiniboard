[![Build Status](https://circleci.com/gh/reflectoring/infiniboard.svg?style=shield&circle-token=b5481c7e89b830ae2579de7a5303f495a96fc8b2)](https://circleci.com/gh/reflectoring/infiniboard) [![Gradle Status](https://gradleupdate.appspot.com/reflectoring/infiniboard/status.svg?branch=master)](https://gradleupdate.appspot.com/reflectoring/infiniboard/status)

# infiniboard
infiniboard is a customizable, general purpose project dashboard to assist you in gathering your most important project metrics in one place.


## local development

Remove all existing IntelliJ project configurations and create the latest one by running:
```
$ ./gradlew cleanIdea idea
```

### infiniboard-app
If you do not have node/npm set up globally, setup a local installation by running:
```
$ ./gradlew -Pdownload_node npmSetup
$ ./gradlew -Pdownload_node npmInstall
```

otherwise just run:
```
$ npm install
```

To start the client in development mode run:
```
$ node node_modules/.bin/gulp dev
```

To package the client for production usage run:
```
$ node node_modules/.bin/gulp
```

The package client is located inside the `build` directory.


### quartermaster
Open a terminal and start a gradle deamon that builds continuously:
```
$ cd quartermaster
$ ../gradlew buildWithClient
$ ../gradlew -t classes
```

Open another terminal and start the application:
```
$ cd quartermaster
$ ../gradlew bootRun
For remot debug user
$ ../gradlew bootRun --debug-jvm
```

Now all changes are reloaded live.

## license

> The MIT License (MIT)
> Copyright (c) 2016 reflectoring
> 
> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
> 
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
