install.packages("ggplot2")
install.packages("randomForest")
install.packages("cowplot")
install.packages("e1071")
install.packages("gmodels")
install.packages("rpart")
library(ggplot2)
library(randomForest)
library(cowplot)
library(e1071)
library(gmodels)
library(rpart)


mnist_train <- read.csv("mnist_pca_lda_train.csv", stringsAsFactors=F)

mnist_test <- read.csv("mnist_pca_lda_test.csv", stringsAsFactors=F)

nb_models_785 <- vector(mode="list", length=45)

nb_models_pca <- vector(mode="list", length=45)
nb_models_lda <- vector(mode="list", length=45)

accuracy_pca <- rep(0,45)

start_time <- proc.time()
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("nb_model:: PCA:",index,"for",i,"and",j,sep=" "))
      train <- with(mnist_train, mnist_train[label==i | label==j,c(1,795:803)])
      nb_models_pca[[index]] <- naiveBayes(train,
                                           factor(train$label))
      test <- with(mnist_test, mnist_test[label==i | label==j,c(1,795:803)])
      pred_label <- predict(nb_models_pca[[index]], test)
      #print(pred_label)
      t1 <- table(pred_label, factor(test$label))
      accuracy_pca[i] <- sum(t1[1,1],t1[2,2])/nrow(test)
    }
  }
}
print(index)
print(accuracy_pca)
print(mean(accuracy_pca))

accuracy_pixels <- rep(0,45)
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("nb_model:: pixels:",index,"for",i,"and",j,sep=" "))
      train <- with(mnist_train, mnist_train[label==i | label==j,c(1:785)])
      nb_models_785[[index]] <- naiveBayes(train,
                                           factor(train$label))
      test <- with(mnist_test, mnist_test[label==i | label==j,c(1:785)])
      pred_label <- predict(nb_models_785[[index]], test)
      #print(pred_label)
      t1 <- table(pred_label, factor(test$label))
      accuracy_pixels[i] <- sum(t1[1,1],t1[2,2])/nrow(test)
    }
  }
}
print(index)
print(accuracy_pixels)
print(mean(accuracy_pixels))

accuracy_lda <- rep(0,45)
index <- 0
for(i in 0:9){
  for(j in 1:9){
    if(j>i){
      index <- index+1
      print(paste("nb_model:: LDA:",index,"for",i,"and",j,sep=" "))
      train <- with(mnist_train, mnist_train[label==i | label==j,c(1,786:794)])
      nb_models_lda[[index]] <- naiveBayes(train,
                                           factor(train$label))
      test <- with(mnist_test, mnist_test[label==i | label==j,c(1,786:794)])
      pred_label <- predict(nb_models_lda[[index]], test)
      #print(pred_label)
      t1 <- table(pred_label, factor(test$label))
      accuracy_lda[i] <- sum(t1[1,1],t1[2,2])/nrow(test)
    }
  }
}
print(index)
print(accuracy_lda)
print(mean(accuracy_lda))


end_time <- proc.time()
time_taken <- end_time -start_time
print("time taken")
print(time_taken)




