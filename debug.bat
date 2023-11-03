@title NP-chaonay Java Building System : Application Executing
@color 0A
set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
cd build/final
java -agentlib:jdwp=transport=dt_socket,server=y,address=5005 -jar App.jar
@echo [SCRIPT] (Process ended)
@pause