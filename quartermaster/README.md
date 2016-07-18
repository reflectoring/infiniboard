## quartermaster

## endpoints
* Client `http://localhost:8080/index.html`
* REST API `http://localhost:8080/api/dashboards`
* Actuator API `http://localhost:8090/`

### bundling the client with quartermaster
Build `infiniboard-app` before doing one of the next steps and the client will be automaticly bundled into quartermaster.  

### build quartermaster for production usage:
```
$ ../gradlew clean build
```

### build quartermaster docker container:
```
$ ../gradlew clean buildDocker
```

### build continuously while developing:
Open a terminal and run:
```
$ ../gradlew clean build
$ ../gradlew -t classes
```

Open another terminal and start the application:
```
$ cd quartermaster
$ ../gradlew bootRun
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
