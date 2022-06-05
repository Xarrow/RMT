@echo off
if not exist "%JAVA_HOME%\bin\java.exe" echo Please set the JAVA_HOME variable in your environment, We need java(x64)! jdk8 or later is better! & EXIT /B 1
set BASE_DIR=%~dp0
echo %BASE_DIR%
if not "%3" == "cluster" (
    set "JAVA_OPT=%JAVA_OPT% -Xms512m -Xmx512m -Xmn256m"
) else (
    set "JAVA_OPT=%JAVA_OPT% -server -Xms2g -Xmx2g -Xmn1g -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=320m"
    set "JAVA_OPT=%JAVA_OPT% -XX:-OmitStackTraceInFastThrow XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%BASE_DIR%\logs\java_heapdump.hprof"
    set "JAVA_OPT=%JAVA_OPT% -XX:-UseLargePages"
)

 if not "%2" == "silence" (
	set "JAVA=%JAVA_HOME%\bin\java.exe"
 ) else (
	set "JAVA=%JAVA_HOME%\bin\javaw.exe"
 )

set "JAVA_OPT=%JAVA_OPT% -Dfile.encoding=UTF-8 -Dserver.port=8080"

set "FULL_JAR_PATH=%BASE_DIR%rmt-app.jar"
set "JAVA_OPT=%JAVA_OPT% -jar %FULL_JAR_PATH%"

call "%JAVA%" %JAVA_OPT%


rem startup.bat [-m] [silence] [cluster]