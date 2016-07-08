#!/bin/bash
curl -X POST -d @widget.platform-dev.json  http://localhost:8080/api/widgets --header "Content-Type:application/json"
curl -X POST -d @widget.platform-prod.json http://localhost:8080/api/widgets --header "Content-Type:application/json"
