#!/bin/bash

qry0=Catalina%3Atype%3DDataSource%2Cpath%3D%2Fcs%2Chost%3Dlocalhost%2Cclass%3Djavax.sql.DataSource%2Cname%3D%22csDataSource%22
qry1=Catalina%3Atype%3DExecutor%2Cname%3DtomcatThreadPool
attr0=numActive,maxActive,minIdle,numIdle,maxIdle
attr1=activeCount,queueSize,completedTaskCount,minSpareThreads,maxThreads,largestPoolSize,poolSize
echo time,$attr0,$attr1
n=$1
s=$2
for((i=0;i<$n;i++))
do
   a=`date +"%s"`
   b=`curl -s "http://jsk-virtualbox:8180/cs/jmx.jsp?qry0=$qry0&qry1=$qry1&attr0=$attr0&attr1=$attr1"`
   echo $a$b
   sleep $s
done

