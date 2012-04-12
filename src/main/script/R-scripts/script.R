elements <- read.table("/tmp/request/1322210871425-p.txt",header=T,sep=" ")
elements$element
t(elements)
png("hist.png")
par(xpd=T,mar=par()$mar+c(0,0,0,4))
barplot(t(elements),horiz=T,main="request", col=heat.colors(3),space=0.1,cex.axis=0.8,las=1,cex=0.8)
par(mar=c(5,4,4,2)+0.1)
dev.off()
