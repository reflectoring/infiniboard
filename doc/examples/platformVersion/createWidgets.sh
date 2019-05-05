#!/bin/bash

HOST=localhost
PORT=8080
ENDPOINT=http://$HOST:8080/api/dashboards/development/widgets

curl -X POST -d @widget.platform-dev.json  $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.platform-test.json $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.platform-qs.json   $ENDPOINT --header "Content-Type:application/json"
curl -X POST -d @widget.platform-prod.json $ENDPOINT --header "Content-Type:application/json"
