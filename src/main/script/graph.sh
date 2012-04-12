#!/bin/bash

if [ -z $1 ]
then
  echo $0 start end
  exit 1
fi
if [ -z $2 ]
then
  echo $0 start end
  exit 1
fi

rrd_file=all.rrd
pools_file=pools.png
requests_file=requests.png

start=$1
echo start: $start

end=$2
echo end: $end

rrdtool graph $pools_file -s $start -e $end -w 800 -h 400 --x-grid SECOND:30:MINUTE:1:MINUTE:5:0:%H:%M \
            DEF:linee=$rrd_file:activeCount:AVERAGE AREA:linee#00FFFF:"Concurrent" \
            DEF:linea=$rrd_file:numActive:AVERAGE AREA:linea#00FF33:"Active DB" \
            DEF:linec=$rrd_file:numIdle:AVERAGE LINE2:linec#000000:"Idle DB" 

#            DEF:lineb=$rrd_file:maxActive:AVERAGE LINE1:lineb#0099FF:"Max DB" \
rrdtool graph $requests_file -s $start -e $end -w 800 -h 400 --x-grid SECOND:30:MINUTE:1:MINUTE:5:0:%H:%M \
            DEF:lined=$rrd_file:completedTaskCount:AVERAGE AREA:lined#008FFF:"Num Requests"


