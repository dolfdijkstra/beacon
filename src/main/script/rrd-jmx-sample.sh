rrdtool create all.rrd --start 1323942000 --step 2 \
    DS:numActive:GAUGE:20:U:U \
    DS:maxActive:GAUGE:20:U:U \
    DS:minIdle:COUNTER:20:U:U \
    DS:numIdle:GAUGE:20:U:U \
    DS:maxIdle:COUNTER:20:U:U \
    DS:activeCount:GAUGE:20:U:U \
    DS:queueSize:GAUGE:20:U:U \
    DS:completedTaskCount:COUNTER:20:U:U \
    DS:minSpareThreads:GAUGE:20:U:U \
    DS:maxThreads:GAUGE:20:U:U \
    DS:largestPoolSize:GAUGE:20:U:U \
    DS:poolSize:GAUGE:20:U:U \
    RRA:AVERAGE:0.5:1:200

rrdtool update all.rrd \
    1323942068:0:50:0:2:10:1:0:1771:4:150:4:4 \
    1323942071:0:50:0:2:10:1:0:1772:4:150:4:4 \
    1323942073:0:50:0:2:10:1:0:1773:4:150:4:4 \
    1323942075:0:50:0:2:10:1:0:1774:4:150:4:4 \
    1323942077:0:50:0:2:10:1:0:1775:4:150:4:4 \
    1323942079:0:50:0:2:10:1:0:1776:4:150:4:4 \
    1323942081:0:50:0:2:10:2:0:1777:4:150:4:4 \
    1323942083:0:50:0:2:10:2:0:1778:4:150:4:4 \
    1323942085:0:50:0:2:10:1:0:1780:4:150:4:4 

rrdtool update all.rrd \
    1323942087:0:50:0:2:10:1:0:1781:4:150:4:4 \
    1323942089:0:50:0:2:10:1:0:1782:4:150:4:4 \
    1323942091:0:50:0:2:10:1:0:1783:4:150:4:4 \
    1323942093:0:50:0:2:10:1:0:1784:4:150:4:4 \
    1323942095:0:50:0:2:10:1:0:1785:4:150:4:4 \
    1323942097:0:50:0:2:10:1:0:1786:4:150:4:4 

rrdtool update all.rrd \
    1323942099:0:50:0:2:10:1:0:1787:4:150:4:4 \
    1323942101:0:50:0:2:10:1:0:1788:4:150:4:4 \
    1323942103:0:50:0:2:10:1:0:1789:4:150:4:4 \
    1323942105:0:50:0:2:10:1:0:1790:4:150:4:4 \
    1323942107:0:50:0:2:10:1:0:1791:4:150:4:4

#    DS:numActive:GAUGE:2:U:U \
#    DS:maxActive:COUNTER:2:U:U \
#    DS:minIdle:COUNTER:2:U:U \
#    DS:numIdle:COUNTER:2:U:U \
#    DS:maxIdle:COUNTER:2:U:U \
#    DS:activeCount:COUNTER:2:U:U \
#    DS:queueSize:GAUGE:2:U:U \
#    DS:completedTaskCount:COUNTER:2:U:U \
#    DS:minSpareThreads:2:U:U \
#    DS:maxThreads:2:U:U \
#    DS:largestPoolSize:2:U:U \
#    DS:poolSize:2:U:U \


rrdtool graph all1.png -s 1323942068 -e 1323942107 -h 400 \
            DEF:linea=all.rrd:numActive:AVERAGE LINE3:linea#FF0000:"Active DB" \
            DEF:lineb=all.rrd:maxActive:AVERAGE LINE3:lineb#00FF00:"Max DB" \
            DEF:linec=all.rrd:numIdle:AVERAGE LINE3:linec#0000FF:"Idle DB" \
            DEF:lined=all.rrd:completedTaskCount:AVERAGE LINE3:lined#000000:"Num Requests" \
            DEF:linee=all.rrd:activeCount:AVERAGE LINE3:linee#000FFF:"Concurrent"

