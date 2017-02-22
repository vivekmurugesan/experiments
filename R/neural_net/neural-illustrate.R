###############
###Illustration code for running neural network
###############
library(e1071); library(ggplot2)
install.packages("neuralnet");library(neuralnet)
ggplot() + geom_point(data=iris, aes(x=Petal.Length,y=Petal.Width,shape=Species, colour=Species))

iris1 <- iris
iris1$der <- iris1$Petal.Length^2-iris1$Petal.Length
ggplot() + geom_point(data=iris1, aes(x=Petal.Length,y=Petal.Width,shape=Species, colour=Species))+geom_line(data=iris1, aes(x=Petal.Length,y=der, colour="red"))

### Creating binary indicator variables variables
iris1 <- iris
iris1 <- cbind(iris1, iris1$Species=="setosa")
iris1 <- cbind(iris1, iris1$Species=="versicolor")
iris1 <- cbind(iris1, iris1$Species=="virginica")

colnames(iris1)[6] <- "setosa"
colnames(iris1)[7] <- "versicolor"
colnames(iris1)[8] <- "virginica"

nn <- neuralnet(setosa+versicolor+virginica ~ Petal.Length+Petal.Width+Sepal.Length+Sepal.Width, data=iris1, hidden=c(3,2))
plot(nn)

### Prediction
mypredict <- compute(nn, iris[-5])$net.result
# Put multiple binary output to categorical output
maxidx <- function(arr) {
  return(which(arr == max(arr)))
}
idx <- apply(mypredict, c(1), maxidx)
prediction <- c('setosa', 'versicolor', 'virginica')[idx]
table(prediction, iris1$Species)

### Prediction for grid
x1_min <- min(iris1$Petal.Length)-0.2; x1_max <- max(iris1$Petal.Length)+0.2
x2_min <- min(iris1$Petal.Width)-0.2; x2_max <- max(iris1$Petal.Width)+0.2
x3_min <- min(iris1$Sepal.Length)-0.2; x3_max <- max(iris1$Sepal.Length)+0.2
x4_min <- min(iris1$Sepal.Width)-0.2; x4_max <- max(iris1$Sepal.Width)+0.2
hs <- 0.1
grid <- as.matrix(expand.grid(seq(x1_min, x1_max, by = hs), seq(x2_min, x2_max, by =hs),
                              seq(x3_min, x3_max, by = hs), seq(x4_min, x4_max, by =hs)))
grid.predict <- compute(nn, grid)$net.result
# Put multiple binary output to categorical output
maxidx <- function(arr) {
  return(which(arr == max(arr)))
}
Z <- apply(grid.predict, c(1), maxidx)
#prediction <- c('setosa', 'versicolor', 'virginica')[idx]
#table(prediction, iris1$Species)

# x_min <- min(iris1$Petal.Length)-0.2; x_max <- max(iris1$Petal.Length)+0.2
# y_min <- min(iris1$Petal.Width)-0.2; y_max <- max(iris1$Petal.Width)+0.2
# hs <- 0.01
# grid <- as.matrix(expand.grid(seq(x_min, x_max, by = hs), seq(y_min, y_max, by =hs)))
#Z <- nnetPred(grid, nn) 
ggplot()+  
  geom_tile(aes(x = grid[,1],y = grid[,2],fill=as.character(Z)), alpha = 0.3, show.legend = F)+   
  geom_point(data = iris1, aes(x=Sepal.Length, y=Sepal.Width, color = as.character(as.integer(Species)), size = 2) + 
  theme_bw(base_size = 15) +  ggtitle('Neural Network Decision Boundary') +  coord_fixed(ratio = 0.8) +   
  theme(axis.ticks=element_blank(), panel.grid.major = element_blank(), panel.grid.minor = element_blank(),         
        axis.text=element_blank(), axis.title=element_blank(), legend.position = 'none')
  )

ggplot()+  
  geom_tile(aes(x = grid[,1],y = grid[,2],fill=as.character(Z)), alpha = 0.3, show.legend = F)+ 
  geom_point(data=iris1, aes(x=Petal.Length,y=Petal.Width,shape=Species))
             
   ggplot()+  
     geom_tile(aes(x = grid[,3],y = grid[,4],fill=as.character(Z)), alpha = 0.3, show.legend = F)+ 
     geom_point(data=iris1, aes(x=Sepal.Length,y=Sepal.Width,shape=Species))
