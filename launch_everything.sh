#!/bin/bash

docker run -d -p 8090:8090 -p 8091:8091 --rm  docker.io/natbodocker/virtualrobotdisi23:1.0

cd ./basicrobot24-1.0/bin/ &&  ./basicrobot24 & > /dev/null 2>&1
cd ./sprint2 && gradle run & > /dev/null 2>&1
cd ./sprint1 && gradle run & > /dev/null 2>&1



