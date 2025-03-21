@echo off
echo Downloading Gradle wrapper...

if not exist gradlew.bat (
  echo Creating Gradle directories...
  mkdir gradle\wrapper

  echo Downloading Gradle wrapper files...
  curl -o gradlew.bat https://raw.githubusercontent.com/gradle/gradle/v8.5.0/wrapper/gradlew.bat
  curl -o gradle\wrapper\gradle-wrapper.jar https://raw.githubusercontent.com/gradle/gradle/v8.5.0/gradle/wrapper/gradle-wrapper.jar
  
  echo distributionBase=GRADLE_USER_HOME> gradle\wrapper\gradle-wrapper.properties
  echo distributionPath=wrapper/dists>> gradle\wrapper\gradle-wrapper.properties
  echo distributionUrl=https\://services.gradle.org/distributions/gradle-8.5-bin.zip>> gradle\wrapper\gradle-wrapper.properties
  echo zipStoreBase=GRADLE_USER_HOME>> gradle\wrapper\gradle-wrapper.properties
  echo zipStorePath=wrapper/dists>> gradle\wrapper\gradle-wrapper.properties
)

echo Building and running the application...
call gradlew.bat bootRun

pause 