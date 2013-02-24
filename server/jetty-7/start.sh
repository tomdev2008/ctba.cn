#!/bin/sh
cd ../../
export CTBA_WORKSPACE_ROOT=$PWD  
cd ./server/jetty-7/
echo ctba root:$CTBA_WORKSPACE_ROOT

 java -Dctba.home=$CTBA_WORKSPACE_ROOT -DSTOP.PORT=8081 -DSTOP.KEY=ctba  -jar start.jar
