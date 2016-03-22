[![Build Status](https://circleci.com/gh/reflectoring/infiniboard/tree/circleci.svg?style=shield&circle-token=75e95e854013500044492bdd5a7990aecbc97a92)](https://circleci.com/gh/reflectoring/infiniboard) [![Gradle Status](https://gradleupdate.appspot.com/reflectoring/infiniboard/status.svg?branch=master)](https://gradleupdate.appspot.com/reflectoring/infiniboard/status)

# infiniboard
infiniboard is a customizable, general purpose project dashboard to assist you in gathering your most important project metrics in one place.


## local development

Remove all existing IntelliJ project configurations and create the latest one by running:
```
$ ./gradlew cleanIdea idea
```

### quartermaster
Open a terminal and start a gradle deamon that builds continuously:
```
$ ./gradlew -t classes
```

Open another terminal and start the application:
```
$ ./gradlew bootRun
```

Now all changes are reloaded live.

#### disable embedded mongo db
To disable the embedded mongo db use `-Pno_embedded_mongo` e.g.:
```
$ ./gradlew -Pno_embedded_mongo bootRun
```


### infiniboard-app
If you do not have node/npm set up globally, setup a local installation by running:
```
$ ./gradlew -Pdownload_node npmSetup
$ ./gradlew -Pdownload_node npmInstall
```

To start the client in development mode run:
```
$ npm install
$ npm start
```

To package the client for production usage run:
```
$ npm install
$ gulp
```
The package client is located inside the `build` directory.

## license

> The MIT License (MIT)
> Copyright (c) 2016 reflectoring
> 
> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
> 
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
