@echo off
start /B docker run -d --name virtual -p 8090:8090 -p 8091:8091 --rm docker.io/natbodocker/virtualrobotdisi23:1.0
start /B docker run -d --name basic -p 8020:2020 --rm docker.io/natbodocker/basicrobot24:1.0
start /B docker run -d --name sprint2 -p 8021:2021 --rm sprint2:latest
start /B docker run -d --name sprint1 -p 8022:2022 --rm sprint1:latest

:: Aspetta che tutti i container siano avviati
timeout /t 5 >nul  :: Attendere un po' per garantire che i comandi siano completati
echo Lanciati tutti i container
