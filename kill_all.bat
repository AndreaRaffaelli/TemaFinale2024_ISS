@echo off
start /B docker stop virtual
start /B docker stop basic
start /B docker stop sprint1
start /B docker stop sprint2

:: Ferma e rimuove il container Docker
:: docker ps -q --filter "ancestor=docker.io/natbodocker/virtualrobotdisi23:1.0" | xargs -r docker stop
::
:: Termina i processi Gradle
:: taskkill /F /IM gradle.exe
::
:: Termina il processo basicrobot24
:: taskkill /F /IM basicrobot24.exe

:: Aspetta che tutti i processi siano terminati
timeout /t 5 >nul  :: Attendere un po' per garantire che i comandi siano completati
echo Tutti i processi sono stati terminati.
