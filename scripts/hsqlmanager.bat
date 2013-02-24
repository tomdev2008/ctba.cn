@echo off
java -cp ../WebRoot/WEB-INF/lib/hsqldb.jar org.hsqldb.util.DatabaseManager --url jdbc:hsqldb:file:../TestRoot/hsql/ctba_db %2 %3 %4 %5 %6 %7 %8 %9