#!/bin/bash

HOST=localhost
PORT=8080
ENDPOINT=http://$HOST:8080/api/dashboards

curl -X POST -d @dashboard-dev.json  $ENDPOINT --header "Content-Type:application/json"
