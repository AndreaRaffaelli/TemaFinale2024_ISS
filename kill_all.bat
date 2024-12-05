@echo off

REM Ferma e rimuove il container Docker
FOR /F "tokens=*" %%i IN ('docker ps -q --filter "ancestor=docker.io/natbodocker/virtualrobotdisi23:1.0"') DO (
    docker stop %%i
)

REM Termina i processi Gradle
taskkill /F /IM gradle.exe /T

REM Termina il processo basicrobot.bat
taskkill /F /IM basicrobot.bat

echo Tutti i processi sono stati terminati.
