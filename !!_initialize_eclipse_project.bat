@echo off

if exist "initialize_eclipse_project_lock.lock" goto lock_file_exists

set /P project_name=Enter your project name (should be upper camel case): 
echo.
set /P package_name_suffix=Enter the part of the package name that comes after "com.maknmakstudios.minecraft.". The package name ending should only be one deep. If you make it more than one deep, enter a one deep package name ending, and change it manually yourself later.: 
echo.

echo Renaming project and changing package name...
powershell -executionpolicy bypass -File rename_project_and_change_package.ps1 "%project_name%" "%package_name_suffix%"
echo Done.
echo.

echo Cleaning Gradle project files...
call gradlew clean
echo.

echo Cleaning Eclipse project files...
call gradlew cleaneclipse
echo.

echo Generating Eclipse project files...
call gradlew eclipse
echo.

echo Adding Gradle nature to the Eclipse project...
powershell -executionpolicy bypass -File add_gradle_nature.ps1
echo Done.
echo.

echo Initializing Git repository...
rd /q /s .git 2>nul
git init
echo.

echo Finished initializing Eclipse project!
echo.

echo Locking initializer...
copy /y NUL initialize_eclipse_project_lock.lock >NUL
echo Done.
echo.

goto end

:lock_file_exists

echo Lock file exists. Aborting...
echo.

:end

PAUSE
