@echo off
cd /d "%~dp0"

echo Menjalankan Sistem Perpustakaan (GUI)...
java -cp "out;lib/*" ui.MainGUI
pause
