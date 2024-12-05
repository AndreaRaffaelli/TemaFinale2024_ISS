#!/bin/bash

# Ferma e rimuove il container Docker
docker ps -q --filter "ancestor=docker.io/natbodocker/virtualrobotdisi23:1.0" | xargs -r docker stop

# Termina i processi Gradle
pkill -f 'gradle run'

# Termina il processo basicrobot24
pkill -f 'basicrobot24'

echo "Tutti i processi sono stati terminati."
