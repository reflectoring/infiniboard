#!/bin/bash

HOST=localhost
PORT=8080
ENDPOINT=http://$HOST:8080/api/dashboards/1/widgets

curl -X POST -d @widget.divider.json $ENDPOINT --header "Content-Type:application/json"