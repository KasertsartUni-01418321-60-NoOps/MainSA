@title NP-chaonay Java Building System : Application Building
@color 0A
set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
rmdir /S /Q build
mkdir build
mkdir build\java
javac.exe -cp '.\src\lib\sqlite-jdbc-3.43.0.0.jar' .\src\java\Main.java
move .\src\java\*.class .\build\java\
mkdir build\final
mkdir build\final\data
jar.exe cvfm .\build\final\App.jar .\src\MANIFEST.MF -C .\build\java\ Main.class -C .\build\java\ Main$1.class
mkdir build\final\lib
copy .\src\lib\sqlite-jdbc-3.43.0.0.jar .\build\final\lib\sqlite-jdbc-3.43.0.0.jar
@echo [SCRIPT] (Process ended)
@pause