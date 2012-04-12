library(ggplot2)
balance <- data.frame(
 desc = c("Starting Cash", "Sales", "Refunds", "Payouts", "Court Losses", "Court Wins", "Contracts", "End Cash"),
 amount = c(2000, 3400, -1100, -100, -6600, 3800, 1400, 2800)
)
balance$desc <- factor(balance$desc, levels = balance$desc)
balance$id <- seq_along(balance$amount)
balance$type <- ifelse(balance$amount > 0, "in","out")
balance[balance$desc %in% c("Starting Cash", "End Cash"), "type"]<-"net"
balance$end <- cumsum(balance$amount)
balance$end <- c(head(balance$end, -1), 0)
balance$start <- c(0, head(balance$end, -1))
balance <- balance[, c(3, 1, 4, 6, 5, 2)]

p0<-ggplot(balance, aes(desc, fill = type)) + geom_rect(aes(x = desc, xmin=id-0.45,xmax=id+0.45,ymin=end,ymax=start))

balance$type <- factor(balance$type, levels = c("out","in","net"))
strwr <- function(str) gsub(" ", "\n", str)

p1 <- ggplot(balance, aes(fill = type)) +
  geom_rect(aes(x = desc, xmin = id - 0.45, xmax = id + 0.45, ymin = end, ymax = start)) +
  scale_y_continuous("", formatter = "comma") +
  scale_x_discrete("", breaks = levels(balance$desc), labels = strwr(levels(balance$desc))) +
  opts(legend.position = "none")

p2<-p1 +
  geom_text(subset = .(type == "in"), aes(id, end, label = comma(amount)), vjust = 1, size = 3) +
  geom_text(subset = .(type == "out"), aes(id, end, label = comma(amount)), vjust = -0.3, size = 3) +
  geom_text(data = subset(balance, type == "net" & id == min(id)), aes(id, end, colour = type, label = comma(end), vjust = ifelse(end < start, 1, -0.3)), size = 3.5) +
  geom_text(data = subset(balance, type == "net" & id == max(id)), aes(id, start, colour = type, label = comma(start), vjust = ifelse(end < start, -0.3, 1)), size = 3.5) 

