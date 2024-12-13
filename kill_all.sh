#!/bin/bash
docker stop virtual &
docker stop basic &
docker stop sprint1 &
docker stop sprint2 &
## Ferma e rimuove il container Docker
#docker ps -q --filter "ancestor=docker.io/natbodocker/virtualrobotdisi23:1.0" | xargs -r docker stop
#
## Termina i processi Gradle
#pkill -f 'gradle run'
#
## Termina il processo basicrobot24
#pkill -f 'basicrobot24'
#
wait
echo "Tutti i processi sono stati terminati."
