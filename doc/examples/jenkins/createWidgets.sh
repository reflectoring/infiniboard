#!/bin/bash

HOST=localhost
PORT=8080
ENDPOINT=http://$HOST:8080/api/dashboards/1/widgets

curl -X POST -d @widget.jenkins-folder.json $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.jenkins-job.json    $ENDPOINT --header "Content-Type:application/json"
