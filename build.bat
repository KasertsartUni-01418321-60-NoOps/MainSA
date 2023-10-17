@title NP-chaonay Java Building System : Application Building
@color 0A
set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
rmdir /S /Q build
mkdir build
mkdir build\java
:: put your lib/code here!!
javac.exe -cp '.;.\src\java\;.\src\lib\FXRouter.jar;.\src\lib\sqlite-jdbc-3.43.0.0.jar' .\src\java\*.java
move .\src\java\*.class .\build\java\
mkdir build\final
mkdir build\final\data
jar.exe cvfm .\build\final\App.jar .\src\MANIFEST.MF -C .\build\java\ . -C .\src\ res
mkdir build\final\lib
:: [put your lib here!!]
copy .\src\lib\sqlite-jdbc-3.43.0.0.jar .\build\final\lib\sqlite-jdbc-3.43.0.0.jar
copy .\src\lib\FXRouter.jar .\build\final\lib\FXRouter.jar
:: [END]
:: check if we have to include another folder/file... also put your lib into manifest.mf too!
@echo [SCRIPT] (Process ended)
@pause