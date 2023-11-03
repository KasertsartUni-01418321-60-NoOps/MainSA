@REM this file is for sample only, do not overwrite if it is specific to your machine's hw/sw config; just make your own file and add .gitignore
@title NP-chaonay Java Building System : Application Executing
@color 0A
set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
@echo [NPc] (Cancelled) Set to JRE 8 by java.com because it have more complete JavaFX installation lamo
@REM set "JAVA_HOME=C:\Program Files\Java\jre-1.8\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd build/final
java -jar App.jar
@echo [SCRIPT] (Process ended)
@pause