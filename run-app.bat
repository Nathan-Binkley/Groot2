@echo off
echo Downloading Maven if not present...

if not exist mvnw (
  echo Downloading Maven wrapper...
  curl -o mvnw.cmd https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw.cmd
  curl -o .mvn/wrapper/maven-wrapper.jar --create-dirs https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/0.5.6/maven-wrapper-0.5.6.jar
  curl -o .mvn/wrapper/maven-wrapper.properties --create-dirs https://raw.githubusercontent.com/takari/maven-wrapper/master/mvnw/maven-wrapper.properties
)

echo Building and running the application...
call mvnw.cmd clean spring-boot:run

pause 