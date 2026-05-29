@echo off
@rem ==============================================================================
@rem Maven Wrapper Windows Command Script
@rem Auto-downloads maven-wrapper.jar via Powershell if missing, then runs Java
@rem ==============================================================================

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set WRAPPER_JAR="%DIRNAME%.mvn\wrapper\maven-wrapper.jar"
set PROPERTIES_FILE="%DIRNAME%.mvn\wrapper\maven-wrapper.properties"

if not exist %WRAPPER_JAR% (
    echo Downloading maven-wrapper.jar...
    powershell -Command "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar', %WRAPPER_JAR%)"
)

java -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%DIRNAME%" org.apache.maven.wrapper.MavenWrapperMain %*
