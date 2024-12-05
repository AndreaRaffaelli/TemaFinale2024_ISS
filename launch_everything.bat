@echo off

REM Avvia il container Docker
docker run -d -p 8090:8090 -p 8091:8091 --rm docker.io/natbodocker/virtualrobotdisi23:1.0

REM Avvia basicrobot.bat in background
start /B cmd /c "cd basicrobot24-1.0\bin && basicrobot.bat"

REM Avvia il primo progetto Gradle in background
start /B cmd /c "cd sprint1 && gradle run"

REM Avvia il secondo progetto Gradle in background
start /B cmd /c "cd sprint2 && gradle run"

echo Tutti i processi sono stati avviati.
