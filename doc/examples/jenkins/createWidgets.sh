#!/bin/bash

HOST=localhost
PORT=8080
ENDPOINT=http://$HOST:8080/api/dashboards/1/widgets

curl -X POST -d @widget.jenkins-job-notbuilt.json $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.jenkins-job-disabled.json $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.jenkins-job-green.json    $ENDPOINT --header "Content-Type:application/json"