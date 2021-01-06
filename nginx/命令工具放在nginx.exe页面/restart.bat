@echo off
taskkill /f /fi "IMAGENAME eq nginx.exe"
start nginx.exe
#pause