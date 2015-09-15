install.packages("caret")
library(caret)

mnist <- read.csv("train.csv",stringsAsFactors=F)
dim(mnist)
tail(mnist[1])
nrow(unique(mnist[1]))

## taking a copy of mnist
mnist_copy <- mnist

######--------- Applying PCA --------#############
pca.res <- prcomp(mnist[2:785], retx=TRUE)
pca.res
summary(pca.res)

# detailed output of PCA:
pca.res$sdev     # standard deviations of the principal components
pca.res$rotation # matrix of variable loadings
pca.res$x        # if scores=TRUE, scores of the data on the 
# principal components

# rotate data by hand: [y1,y2] = [x1,x2] %*% pca.res$rotation 
rot <- pca.res$rotation
pca1_weight <- unlist(rot[,1])
pca2_weight <- unlist(rot[,2])

pca1_result <-  rep(0,nrow(mnist))
pca2_result <-  rep(0,nrow(mnist))

for(i in 1:nrow(mnist)){
  pca1_result[i] <- sum(pca1_weight*mnist[i,2:785])  
  pca2_result[i] <- sum(pca2_weight*mnist[i,2:785])  
}

mnist$pca1 <- pca1_result
mnist$pca2 <- pca2_result

print(paste("Scored pca::",sqrt(var(mnist$pca1)),sqrt(var(mnist$pca2)),sep=", "))
print(paste("From pca model::",pca.res$sdev[1],pca.res$sdev[2],sep=","))


## Samples
set.seed(123)
sample_size <- 50

## Sample for 3
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='3',])), size=sample_size)
sample_3 <- (mnist[mnist$label=='3',])[sample_ind,]

## Sample for 5
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='5',])), size=sample_size)
sample_5 <- (mnist[mnist$label=='5',])[sample_ind,]

## Sample for 8
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='8',])), size=sample_size)
sample_8 <- (mnist[mnist$label=='8',])[sample_ind,]
sample_8[1]

#### Plot with Pca 1 and 2
sample_358 <- data.frame(sample_3)
sample_358 <- rbind(sample_358,sample_5)
sample_358 <- rbind(sample_358,sample_8)

plot(sample_358$pca1, sample_358$pca2, 
     pch=as.character(factor(unlist(sample_358[1]))),
     col = c("green","red","blue"))

#### For labels 1,7 and 9
## Sample for 1
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='1',])), size=sample_size)
sample_1 <- (mnist[mnist$label=='1',])[sample_ind,]

## Sample for 7
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='7',])), size=sample_size)
sample_7 <- (mnist[mnist$label=='7',])[sample_ind,]

## Sample for 9
sample_ind <- sample(seq_len(nrow(mnist[mnist$label=='9',])), size=sample_size)
sample_9 <- (mnist[mnist$label=='9',])[sample_ind,]
sample_9[1]

#### Plot with Pca 1 and 2
sample_179 <- data.frame(sample_1)
sample_179 <- rbind(sample_179,sample_7)
sample_179 <- rbind(sample_179,sample_9)

plot(sample_179$pca1, sample_179$pca2, 
     pch=as.character(factor(unlist(sample_179[1]))),
     col = c("green","red","blue"))

######--------- End of PCA --------#############

######--------- Applying Fisher LDA --------#############
library(MASS)

mnistForLda <- nearZeroVar(mnist, saveMetrics = T)

mnist.lda <- lda(label ~ ., data=mnistForLda)

mnistForLda[mnistForLda[,"zeroVar"] > 0, ] 

mnistForLda[mnistForLda[,"zeroVar"] + mnistForLda[,"nzv"] > 0, ] 

# library(car)
# install.packages('rattle')
# data(wine, package='rattle')
# attach(wine)
# head(wine)

######--------- End of Fisher LDA --------#############