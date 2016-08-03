#! /bin/bash
curl -X POST -d @createWidget.txt http://localhost:8080/api/dashboards/1/widgets --header "Content-Type:application/json"
