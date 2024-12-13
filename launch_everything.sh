#!/bin/bash

docker run -d --name virtual -p 8090:8090 -p 8091:8091 --rm  docker.io/natbodocker/virtualrobotdisi23:1.0 &
docker run -d --name basic -p 8020:2020 --rm docker.io/natbodocker/basicrobot24:1.0 &
docker run -d --name sprint2 -p 8021:2021 --rm sprint2:latest &
docker run -d --name sprint1 -p 8022:2022 --rm sprint1:latest &

wait

echo "Lanciati tutti i container"

#cd ./basicrobot24-1.0/bin/ &&  ./basicrobot24 & > /dev/null 2>&1
#cd ./sprint2 && gradle run & > /dev/null 2>&1
#cd ./sprint1 && gradle run & > /dev/null 2>&1



