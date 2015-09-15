install.packages("ROCR")
library(ROCR)

install.packages("e1071")
library(e1071)

install.packages("ggvis")
library(ggvis)

install.packages("kknn")
library(kknn)

install.packages("class")
library(class)

setwd("C:/Tuts/CBA/Term3/DM2/Assignment/mnist_data")
mnist_train <- read.csv("mnist_pca_lda_train.csv", stringsAsFactors=F)

mnist_test <- read.csv("mnist_pca_lda_test.csv", stringsAsFactors=F)


attach(mnist_train)
unique((mnist_train[label==0 | label==1,c(1:785)])[0])

logit01_785 <- glm(label~., mnist_train[label==0 | label==1,c(1:785)],
                   family="binomial")

logit01_pca <- glm(label~., data=mnist_train[label==0 | label==1,c(1,795:803)],
                   family="binomial")

logit01_lda <- glm(label~., data=mnist_train[label==0 | label==1,c(1,786:794)],
                   family="binomial")
models_785 <- rep(logit01_785,45)
remove(models_785)

models_785 <- vector(mode="list", length=45)
#models_785[[1]] <- logit01_785

models_pca <- vector(mode="list", length=45)
models_lda <- vector(mode="list", length=45)

mnist_train_cpy <- mnist_train

mnist_train[1] <- factor(mnist_train[1])

index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("model:: PCA:",index,"for",i,"and",j,sep=" "))
      models_pca[[index]] <- glm(factor(label)~., data=mnist_train[label==i | label==j,c(1,795:803)],
                                 family="binomial")
    }
  }
}
print(index)

index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("model:: LDA :",index,"for",i,"and",j,sep=" "))
      models_lda[[index]] <- glm(factor(label)~., data=mnist_train[label==i | label==j,c(1,786:794)],
                                 family="binomial")
    }
  }
}
print(index)

index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("model:: 785 pixels: ",index,"for",i,"and",j,sep=" "))
      models_785[[index]] <- glm(factor(label)~., data=mnist_train[label==i | label==j,c(1:785)],
                                 family="binomial")
    }
  }
}
print(index)

for(i in 1:45){
  models_lda[[i]] <- models_785[[i]]
}

for(i in 1:45){
  models_785[[i]] <- models_pca[[i]]
}

auc <-0
test_p <- with(mnist_test,predict(models_pca[[10]], type="response", 
                  newdata = mnist_test[label==1 | label==2,c(1,795:803)]))
trainY <- with(mnist_test,mnist_test[label==1 | label==2,1])
trainF <- with(mnist_test,factor(mnist_test[label==1 | label==2,1]))

pred<-prediction(test_p,trainF)
perf<-performance(pred,"tpr","fpr")
plot(perf)

auc<-performance(pred,"auc")
auc<-unlist(slot(auc, "y.values"))
auc

auc_pca <- rep(0,45)
auc_lda <- rep(0,45)
auc_785 <- rep(0,45)

### auc value for PCA
pca_df <- data.frame(rep(0,0,0))
colnames(pca_df) <- c("label1", "label2","auc")
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
    index <- index+1
    auc <-0
    test_p <- with(mnist_test,predict(models_pca[[index]], type="response", 
                                      newdata = mnist_test[label==i | label==j,c(1,795:803)]))
    trainY <- with(mnist_test,mnist_test[label==i | label==j,1])
    trainF <- with(mnist_test,factor(mnist_test[label==i | label==j,1]))
    
    pred<-prediction(test_p,trainF)
    perf<-performance(pred,"tpr","fpr")
    #plot(perf)
    
    auc<-performance(pred,"auc")
    auc<-unlist(slot(auc, "y.values"))
    auc_pca[index] <- auc
    print(paste("auc for ",i,j,"is", auc, sep=":"))
    pca_df <- rbind(pca_df, c(i,j,auc))
    }
  }
}
colnames(pca_df) <- c("label1", "label2","auc")

#####  For LDA
lda_df <- data.frame(rep(0,0,0))
colnames(lda_df) <- c("label1", "label2","auc")
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      auc <-0
      test_p <- with(mnist_test,predict(models_lda[[index]], type="response", 
                                        newdata = mnist_test[label==i | label==j,c(1,786:794)]))
      trainY <- with(mnist_test,mnist_test[label==i | label==j,1])
      trainF <- with(mnist_test,factor(mnist_test[label==i | label==j,1]))
      
      pred<-prediction(test_p,trainF)
      perf<-performance(pred,"tpr","fpr")
      #plot(perf)
      
      auc<-performance(pred,"auc")
      auc<-unlist(slot(auc, "y.values"))
      auc_lda[index] <- auc
      print(paste("auc for ",i,j,"is", auc, sep=":"))
      lda_df <- rbind(lda_df, c(i,j,auc))
    }
  }
}
colnames(lda_df) <- c("label1", "label2","auc")



#####  For Pixels
pixels_df <- data.frame(rep(0,0,0))
#colnames(pixels_df) <- c("label1", "label2","auc")
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      auc <-0
      test_p <- with(mnist_test,predict(models_785[[index]], type="response", 
                                        newdata = mnist_test[label==i | label==j,c(1:785)]))
      trainY <- with(mnist_test,mnist_test[label==i | label==j,1])
      trainF <- with(mnist_test,factor(mnist_test[label==i | label==j,1]))
      
      pred<-prediction(test_p,trainF)
      perf<-performance(pred,"tpr","fpr")
      #plot(perf)
      
      auc<-performance(pred,"auc")
      auc<-unlist(slot(auc, "y.values"))
      auc_785[index] <- auc
      print(paste("auc for ",i,j,"is", auc, sep=":"))
      pixels_df <- rbind(pixels_df, c(i,j,auc))
    }
  }
}
colnames(pixels_df) <- c("label1", "label2","auc")

write.csv(pca_df,"pca_logit.csv",row.names=F)
write.csv(lda_df,"lda_logit.csv",row.names=F)
write.csv(pixels_df,"pixels_logit.csv",row.names=F)


## Samples
set.seed(123)
sample_size <- 100

## Sample for 0
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='0',])), size=sample_size)
train_0 <- (mnist_train[mnist_train$label=='0',])[sample_ind,]

## Sample for 1
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='1',])), size=sample_size)
train_1 <- (mnist_train[mnist_train$label=='1',])[sample_ind,]

## Sample for 2
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='2',])), size=sample_size)
train_2 <- (mnist_train[mnist_train$label=='2',])[sample_ind,]

## Sample for 3
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='3',])), size=sample_size)
train_3 <- (mnist_train[mnist_train$label=='3',])[sample_ind,]

## Sample for 4
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='4',])), size=sample_size)
train_4 <- (mnist_train[mnist_train$label=='4',])[sample_ind,]

## Sample for 5
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='5',])), size=sample_size)
train_5 <- (mnist_train[mnist_train$label=='5',])[sample_ind,]

## Sample for 6
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='6',])), size=sample_size)
train_6 <- (mnist_train[mnist_train$label=='6',])[sample_ind,]

## Sample for 7
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='7',])), size=sample_size)
train_7 <- (mnist_train[mnist_train$label=='7',])[sample_ind,]

## Sample for 8
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='8',])), size=sample_size)
train_8 <- (mnist_train[mnist_train$label=='8',])[sample_ind,]

## Sample for 9
sample_ind <- sample(seq_len(nrow(mnist_train[mnist_train$label=='9',])), size=sample_size)
train_9 <- (mnist_train[mnist_train$label=='9',])[sample_ind,]

test0 <- train_0[51:100,]
test1 <- train_1[51:100,]
test2 <- train_2[51:100,]
test3 <- train_3[51:100,]
test4 <- train_4[51:100,]
test5 <- train_5[51:100,]
test6 <- train_6[51:100,]
test7 <- train_7[51:100,]
test8 <- train_8[51:100,]
test9 <- train_9[51:100,]

train0 <- train_0[1:50,]
train1 <- train_1[1:50,]
train2 <- train_2[1:50,]
train3 <- train_3[1:50,]
train4 <- train_4[1:50,]
train5 <- train_5[1:50,]
train6 <- train_6[1:50,]
train7 <- train_7[1:50,]
train8 <- train_8[1:50,]
train9 <- train_9[1:50,]

test <- rbind(test0,test1)

test <- rbind(test,test2)
test <- rbind(test,test3)
test <- rbind(test,test4)
test <- rbind(test,test5)
test <- rbind(test,test6)
test <- rbind(test,test7)
test <- rbind(test,test8)
test <- rbind(test,test9)

train <- rbind(train0,train1)

train <- rbind(train,train2)
train <- rbind(train,train3)
train <- rbind(train,train4)
train <- rbind(train,train5)
train <- rbind(train,train6)
train <- rbind(train,train7)
train <- rbind(train,train8)
train <- rbind(train,train9)

######## KNN with LDA

kvals <- c(1,3,5,7)
results <- rep(0,length(kvals))

index <- 0
for(k in kvals){
  mnist.knn <- kknn(formula = formula(label~.), train=train[,c(1,786:794)],
                    test =test[,c(1,786:794)], k=k, distance=1,
                    kernel = "triangular")
  mnist.fit <- fitted(mnist.knn)
  pca.table <- table(test[,1], round(mnist.fit))
  success <- 0
  for(i in 1:10){
    success <- success + pca.table[i,i]
  }
  print(success)
  accuracy <- success / nrow(test)
  print(accuracy)
  index <- index + 1
  results[index] <- accuracy
}
lda.result <-data.frame(kvals, results)
colnames(lda.result) <- c("k", "accuracy")

######## KNN with PCA

kvals <- c(1,3,5,7)
results <- rep(0,length(kvals))

index <- 0
for(k in kvals){
  mnist.knn <- kknn(formula = formula(label~.), train=train[,c(1,795:803)],
                    test =test[,c(1,795:803)], k=k, distance=1,
                    kernel = "triangular")
  mnist.fit <- fitted(mnist.knn)
  pca.table <- table(test[,1], round(mnist.fit))
  success <- 0
  for(i in 1:10){
    success <- success + pca.table[i,i]
  }
  print(success)
  accuracy <- success / nrow(test)
  print(accuracy)
  index <- index + 1
  results[index] <- accuracy
}
pca.result <-data.frame(kvals, results)
colnames(pca.result) <- c("k", "accuracy")

######## KNN with pixels

kvals <- c(1,3,5,7)
results <- rep(0,length(kvals))

index <- 0
for(k in kvals){
  mnist.knn <- kknn(formula = formula(label~.), train=train[,c(1:785)],
                    test =test[,c(1:785)], k=k, distance=1,
                    kernel = "triangular")
  mnist.fit <- fitted(mnist.knn)
  pca.table <- table(test[,1], round(mnist.fit))
  success <- 0
  for(i in 1:10){
    success <- success + pca.table[i,i]
  }
  print(success)
  accuracy <- success / nrow(test)
  print(accuracy)
  index <- index + 1
  results[index] <- accuracy
}
pixels.result <-data.frame(kvals, results)
colnames(pixels.result) <- c("k", "accuracy")