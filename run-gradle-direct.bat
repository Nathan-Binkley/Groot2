@echo off
echo Setting up Gradle environment...

SET GRADLE_VERSION=8.5
SET GRADLE_DIR=%USERPROFILE%\.gradle\wrapper\dists\gradle-%GRADLE_VERSION%

if not exist "%GRADLE_DIR%" (
  echo Downloading Gradle %GRADLE_VERSION%...
  mkdir "%GRADLE_DIR%"
  curl -L -o "%TEMP%\gradle-%GRADLE_VERSION%-bin.zip" https://services.gradle.org/distributions/gradle-%GRADLE_VERSION%-bin.zip
  powershell -command "Expand-Archive -Path '%TEMP%\gradle-%GRADLE_VERSION%-bin.zip' -DestinationPath '%GRADLE_DIR%'"
)

SET PATH=%GRADLE_DIR%\gradle-%GRADLE_VERSION%\bin;%PATH%

echo Checking Gradle installation...
gradle --version

echo Building and running the application...
gradle bootRun

pause 