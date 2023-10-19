@title NP-chaonay Java Building System : Application Building
@color 0A
set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
rmdir /S /Q build
mkdir build
mkdir build\java
mkdir build\java\th
mkdir build\java\th\ac
mkdir build\java\th\ac\ku
mkdir build\java\th\ac\ku\sci
mkdir build\java\th\ac\ku\sci\cs
:: put your code here!!
mkdir build\java\th\ac\ku\sci\cs\projectsa\
mkdir build\java\th\ac\ku\sci\cs\projectsa\uictrl
:: [END zone]
:: put your lib/code here!!
javac.exe -encoding UTF-8 -cp '.;.\src\java\th\ac\ku\sci\cs\projectsa;.\src\java\th\ac\ku\sci\cs\projectsa\uictrl;.\src\lib\FXRouter.jar;.\src\lib\sqlite-jdbc-3.43.0.0.jar' .\src\java\th\ac\ku\sci\cs\projectsa\*.java .\src\java\th\ac\ku\sci\cs\projectsa\uictrl\*.java
:: [END zone]
:: put your code here!!
move .\src\java\th\ac\ku\sci\cs\projectsa\*.class .\build\java\th\ac\ku\sci\cs\projectsa\
move .\src\java\th\ac\ku\sci\cs\projectsa\uictrl\*.class .\build\java\th\ac\ku\sci\cs\projectsa\uictrl\
:: [END zone]
mkdir build\final
mkdir build\final\data
jar.exe cvfm .\build\final\App.jar .\src\MANIFEST.MF -C .\build\java\ . -C .\src\ resources
mkdir build\final\lib
:: [put your lib here!!]
copy .\src\lib\sqlite-jdbc-3.43.0.0.jar .\build\final\lib\sqlite-jdbc-3.43.0.0.jar
copy .\src\lib\FXRouter.jar .\build\final\lib\FXRouter.jar
:: [END zone]
:: check if we have to include another folder/file... also put your lib into manifest.mf too!
@echo [SCRIPT] (Process ended)
@pause