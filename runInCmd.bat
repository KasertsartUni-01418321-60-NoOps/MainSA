@REM this file is for sample only, do not overwrite if it is specific to your machine's hw/sw config; just make your own file and add .gitignore
@title NP-chaonay Java Building System : Application Executing
@color 0A
set "JAVA_HOME=C:\Program Files\Java\jdk-1.8\openlogic-openjdk-8u392-b08-windows-64"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd build/final
java -jar App.jar -MiscFunFlag+muteMIDI
@echo [SCRIPT] (Process ended)
@pause