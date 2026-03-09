@echo off
echo Compiling project...
javac -encoding UTF-8 -cp "lib/*" -d out src/database/*.java src/model/*.java src/service/*.java src/ui/*.java src/main/*.java

if %errorlevel% equ 0 (
    echo.
    echo Compilation successful!
    echo.
    echo You can now run the application:
    echo - Double click run-gui.bat for GUI version
    echo - Double click run-console.bat for Console version
) else (
    echo.
    echo Compilation failed!
)

pause
