@echo off
cd /d "%~dp0"

echo Menjalankan Sistem Perpustakaan (Console)...
java -cp "out;lib/*" main.MainApp
pause
