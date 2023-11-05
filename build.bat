@REM this file is for sample only, do not overwrite if it is specific to your machine's hw/sw config; just make your own file and add .gitignore
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
@REM  put your code here!!
mkdir build\java\th\ac\ku\sci\cs\projectsa\
mkdir build\java\th\ac\ku\sci\cs\projectsa\uictrl
mkdir build\java\th\ac\ku\sci\cs\projectsa\fun
@REM  [END zone]
@REM  put your lib/code here!!
javac.exe -encoding UTF-8 -cp '.;.\src\java\th\ac\ku\sci\cs\projectsa;.\src\java\th\ac\ku\sci\cs\projectsa\uictrl;.\src\java\th\ac\ku\sci\cs\projectsa\fun;.\src\lib\FXRouter.jar;.\src\lib\sqlite-jdbc-3.43.0.0.jar' .\src\java\th\ac\ku\sci\cs\projectsa\*.java .\src\java\th\ac\ku\sci\cs\projectsa\uictrl\*.java .\src\java\th\ac\ku\sci\cs\projectsa\fun\*.java
@echo [SCRIPT] (Check if java compilation is success, then press any key to continue...)
@pause
@REM  [END zone]
@REM  put your code here!!
move .\src\java\th\ac\ku\sci\cs\projectsa\*.class .\build\java\th\ac\ku\sci\cs\projectsa\
move .\src\java\th\ac\ku\sci\cs\projectsa\uictrl\*.class .\build\java\th\ac\ku\sci\cs\projectsa\uictrl\
move .\src\java\th\ac\ku\sci\cs\projectsa\fun\*.class .\build\java\th\ac\ku\sci\cs\projectsa\fun\
@REM  [END zone]
mkdir build\final
mkdir build\final\data
mkdir build\final\misc
mkdir build\final\misc\fun
mkdir build\final\misc\fun\midi
mkdir build\final\misc\fun\midi\sf
mkdir build\final\misc\fun\midi\songs
copy .\misc\midiSongs\*.mid build\final\misc\fun\midi\songs
jar.exe cvfm .\build\final\App.jar .\src\MANIFEST.MF -C .\build\java\ . -C .\src\ resources
mkdir build\final\lib
@REM  [put your lib here!!]
copy .\src\lib\sqlite-jdbc-3.43.0.0.jar .\build\final\lib\sqlite-jdbc-3.43.0.0.jar
copy .\src\lib\FXRouter.jar .\build\final\lib\FXRouter.jar
@REM  [END zone]
@REM  check if we have to include another folder/file... also put your lib into manifest.mf too!
@echo [SCRIPT] (Process ended)
@pause