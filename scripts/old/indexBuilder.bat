@echo off
C:
cd C:\Program Files\Apache Software Foundation\Tomcat 6.0\ctbaWebapps\ROOT\WEB-INF\classes
java -Xmx512m -Xms128m  -classpath . org.net9.bbs.lucene.index.IndexBuilder 
rem -Djava.ext.dirs=C:\Program Files\Apache Software Foundation\Tomcat 6.0\ctbaWebapps\ROOT\WEB-INF\lib 
rem pause