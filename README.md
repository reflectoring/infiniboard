[![pipeline status](https://gitlab.com/reflectoring/infiniboard/badges/master/pipeline.svg)](https://gitlab.com/reflectoring/infiniboard/commits/master) [![Gradle Status](https://gradleupdate.appspot.com/reflectoring/infiniboard/status.svg?branch=master)](https://gradleupdate.appspot.com/reflectoring/infiniboard/status) [ ![Download](https://api.bintray.com/packages/reflectoring/releases/infiniboard/images/download.svg) ](https://bintray.com/reflectoring/releases/infiniboard/_latestVersion)

<img src="https://raw.githubusercontent.com/reflectoring/infiniboard/master/doc/assets/infiniboard.png" alt="infiniboard" width="250px" height="250px" />  

# infiniboard
infiniboard is a customizable, general purpose project dashboard to assist you in gathering your most important project metrics in one place.

## screenshot
![Dashboard](https://raw.githubusercontent.com/reflectoring/infiniboard/master/doc/assets/infiniboard-preview.png)

## Want to contribute?
See the [contribution guide](https://github.com/reflectoring/infiniboard/blob/master/CONTRIBUTING.md).

## release
To release infiniboard follow these steps:
1. make sure all needed changes are pushed to `master` and the build passed
1. create an [GitHub personal access token](https://github.com/settings/tokens) with `public_repo` privileges
1. export your personal access token as environment variable `GITHUB_TOKEN`
1. execute `github-promote-release.sh`

### example 
```bash
$ export GITHUB_TOKEN=...
$ ./github-promote-release.sh 632 0.2.1
```

***Note:** currently the windows version of [aktau/github-release](https://github.com/aktau/github-release) is broken (version 0.7.2). Mac OS X version seems to be working fine.*

## license

> The MIT License (MIT)
> Copyright (c) 2016 reflectoring
> 
> Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
> 
> The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
> 
> THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
