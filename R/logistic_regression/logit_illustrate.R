## Illustration of Logistic regression with a tiny dataset.
## Author: Vivek Murugesan

ch_data <- read.table("coronary.dat",header=T, stringsAsFactors=F)
#ch_data1 <- filter(ch_data, ch_data$AGE>40 && ch_data$CHD==1)
ch_data1 <- ch_data[(ch_data$AGE>40 & ch_data$CHD==1) | (ch_data$AGE<36 & ch_data$CHD==0),]
ch_data1 <- ch_data1[ch_data1$CHD==1,]
plot(ch_data1$CHD ~ ch_data1$AGE, col="blue", 
     main="Coronary disease Y/N vs Age", xlab="Age", ylab="Coronary (Y/N)")
abline(lm(ch_data1$CHD ~ ch_data1$AGE), col="red")

plot(ch_data1$CHD ~ ch_data1$AGE, col="blue", 
     main="Coronary disease Y/N vs Age", xlab="Age", ylab="Coronary (Y/N)")
abline(lm(ch_data1$CHD ~ ch_data1$AGE), col="red")
abline(h=0.5,v=41.5,col="green")

# ch_data2 <- rbind(ch_data1, c(101,129,1))
# plot(ch_data2$CHD ~ ch_data2$AGE, col="blue", 
#      main="Coronary disease Y/N vs Age", xlab="Age", ylab="Coronary (Y/N)")
# abline(lm(ch_data2$CHD ~ ch_data2$AGE), col="red")
# abline(h=0.5,v=41.5,col="green")

lm1 <- lm(ch_data1$CHD ~ ch_data1$AGE)
zs <- seq(from=-6,to=6,by=0.01)
df1 <- data.frame(zs,1/(1+exp(0-zs)))
colnames(df1) <- c("z","sigmoid")
plot(df1$sigmoid~zs,type="l",col="blue", main="Sigmoid function", ylab="Sigmoid", xlab="z")
abline(v=0,h=0.5, col="red")


###########################################
###### Liner classifier illustration
######
###########################################
install.packages("e1071")
library(e1071); library(ggplot2)

liner_ill1 <- read.csv("logit-liner.csv", header=T, stringsAsFactors=F)
liner_ill1$y <- as.factor(liner_ill1$y)

qplot(x1,x2,colour=y,shape=y, data=liner_ill1,main="Classes with linear classifier")+geom_abline(intercept=3,slope=-1,colour="blue")+geom_point(size=8)


###########################################
###### Non-Liner classifier illustration
######
###########################################
circular_ill <- read.csv("logit-circular.csv", header=T, stringsAsFactors=F)
circular_ill$y <- as.factor(circular_ill$y)

qplot(x1,x2,colour=y,shape=y, data=circular_ill,main="Classes with non-linear classifier")+geom_point(size=8)

####### Simulating points for drwaing a circle
org_x <- 2
org_y <- 2.25
radius <- 0.75
t <- seq(from=0,to=2*pi,length.out = 500)
x <- org_x + radius * cos(t)
y <- org_y + radius * sin(t)
circle_df <- data.frame(x,y)

ggplot() + geom_point(data=circular_ill, aes(x=x1,y=x2, shape=y, colour=y, size=8)) + geom_point(data=circle_df, aes(x=x,y=y,colour="blue", size=0.5))

circ_ill_1 <- data.frame(circular_ill$x1-org_x, circular_ill$x2-org_y,circular_ill$y)
colnames(circ_ill_1) <- c("x1","x2","y")
ggplot() + geom_point(data=circ_ill_1, aes(x=x1,y=x2, shape=y, colour=y, size=8))

####### Simulating points for drwaing a circle
org_x <- 0
org_y <- 0
radius <- 0.75
t <- seq(from=0,to=2*pi,length.out = 500)
x <- org_x + radius * cos(t)
y <- org_y + radius * sin(t)
circle_df <- data.frame(x,y)

ggplot() + geom_point(data=circ_ill_1, aes(x=x1,y=x2, shape=y, colour=y, size=8)) + geom_point(data=circle_df, aes(x=x,y=y,colour="blue", size=0.5))+ggtitle("Classes with non-linear classifier")

logit <- glm(CHD ~ AGE, family=binomial, data=ch_data)
summary(logit)
predictions <- predict(logit, newdata=ch_data,type="response")
preds1 <- 1/(1+exp(0-(-5.30945 + 0.11092*ch_data$AGE)))

# m <- nrow(ch_data)
# sum_sqrs <- sum((preds1-ch_data$CHD) *(preds1-ch_data$CHD))/2*m
# 
# thetas <- seq(from=-10, to=10, by=0.01)
# sum_sqrs_arr <- rep(0,length(thetas))
# index <- 1
# for(theta in thetas){
#   preds <- 1/(1+exp(0-( theta*ch_data$AGE)))
#   m <- nrow(ch_data)
#   sum_sqrs <- sum((preds-ch_data$CHD) *(preds-ch_data$CHD))/2*m
#   sum_sqrs_arr[index] <- sum_sqrs
#   index <- index+1
# }
# 
# df <- data.frame(thetas, sum_sqrs_arr)
# colnames(df) <- c("theta", "j_theta")
# 
# plot(j_theta ~ theta, data=df, col="blue",type="l")

## cost function
z <- seq(from=0,to=1, by=0.001)
logz <- -log(z)
df1 <- data.frame(z,logz)
plot(logz~z,data=df1,col="blue", type="l", main="z vs -log(z)")

logz <- -log(1-z)
df2 <- data.frame(z,logz)
plot(logz~z,data=df2,col="blue", type="l", main="z vs -log(1-z)")

par(mfrow=c(1,2))
plot(logz~z,data=df1,col="blue", type="l", main="z vs -log(z)")
plot(logz~z,data=df2,col="blue", type="l", main="z vs -log(1-z)",ylab="-log(1-z)")