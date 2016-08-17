set JAVA_HOME=D:\Java\jdk1.8.0_66_x64
call mvn eclipse:clean
call mvn eclipse:eclipse -DdownloadSources=true
@pause