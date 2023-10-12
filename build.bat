set "JAVA_HOME=%HOME%\Downloads\JAVA\openlogic-openjdk-8u382-b05-windows-64\"
set "PATH=%JAVA_HOME%\bin;%PATH%"
rmdir /S /Q build
mkdir build
mkdir build\java
javac.exe .\src\java\Main.java
move .\src\java\Main.class .\build\java\Main.class
mkdir build\final
jar.exe cvfm .\build\final\App.jar .\src\MANIFEST.MF -C .\build\java\ Main.class
mkdir build\final\lib
copy .\src\lib\sqlite-jdbc-3.43.0.0.jar .\build\final\lib\sqlite-jdbc-3.43.0.0.jar
java -version
pause