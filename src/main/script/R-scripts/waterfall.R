library(ggplot2)
balance <- data.frame(
 desc = c("Starting Cash", "Sales", "Refunds", "Payouts", "Court Losses", "Court Wins","Refunds", "Contracts", "End Cash"),
 amount = c(2000, 3400, -1100, -100, -6600, 3800,-500, 1400, 2800)
)
#balance$desc <- factor(balance$desc, levels = balance$desc)
balance$id <- seq_along(balance$amount)
balance$type <- ifelse(balance$amount > 0, "in","out")
balance[balance$desc %in% c("Starting Cash", "End Cash"), "type"]<-"net"
balance$end <- cumsum(balance$amount)
balance$end <- c(head(balance$end, -1), 0)
balance$start <- c(0, head(balance$end, -1))
balance <- balance[, c(3, 1, 4, 6, 5, 2)]
balance$type <- factor(balance$type, levels = c("out","in","net"))

strwr <- function(str) gsub(" ", "\n", str)
p0<-ggplot(balance, aes(id, fill = type)) + geom_rect(aes(x = id, xmin=id-0.45,xmax=id+0.45,ymin=start,ymax=end)) +
  scale_y_continuous("", formatter = "comma") +
  scale_x_discrete("",breaks=balance$id, labels = strwr(balance$desc)) +
  opts(legend.position = "none")

