## Illustration of linear regression and gradient descent with a tiny dataset.
## Author: Vivek Murugesan
data <- read.csv(file="blood_fat_data.csv",header = T, stringsAsFactors=F)
plot(blood_fat~weight, data=data)
plot(blood_fat~age, data=data)
#Computation of squared error cost function with only age 
theta <- seq(from=1,to=16,by=0.1)
errors <- seq(from=1,to=16,by=0.1)
index <- 1
for(i in theta){
  age_theta <- i*data$age
  sq_err <- (age_theta-data$blood_fat)*(age_theta-data$blood_fat)
  #print(sq_err)
  errors[index] <- sum(sq_err)/(2*nrow(data))
  index <- index+1
}

df <- data.frame(theta, errors)

#Plotting the J(theta) vs parameter values.
plot(errors~theta)
plot(theta,errors,type="l",col = "blue", main="J(theta) vs theta values")


#Simple linear regression with change in the feature set.
lm1 <- lm(blood_fat ~ age+weight, data=data)
summary(lm1)

plot(lm1)


lm2 <- lm(blood_fat ~ age, data=data)
summary(lm2)

#Cost function illustration with a function of the form: ax^2+bx+c
theta1 <- seq(from=-15,to=15,by=0.1)
error1 <- 3*theta1*theta1+2*theta1+1
plot(theta1, error1 , type="l", col="blue", main="error1,J(t) = 3*t*t+2*t+1", xlab="theta(t)", ylab="J(t)")
abline(a=0,b=0,h=0,v=-0.3,col="red")
line(x=-0.3)

error1[which.min(error1)]
theta1[which.min(error1)]

#Illustration with alpha 0.05
plot(theta1, error1 , type="l", col="blue", main="Gradient descent with points", xlab="theta(t)", ylab="J(t)")
x<-10
y <- 3*x*x+2*x+1
points(x=x,y=y,pch=15,col="red")

for(i in 1:10){
  alpha <- 0.05
  x <- x-alpha*(6*x+2)
  y <- 3*x*x+2*x+1
  points(x=x,y=y,pch=17,col="green")
  
  print(x)
}

#Illustration with a different starting point.
plot(theta1, error1 , type="l", col="blue", main="Gradient descent with -ve starting point", xlab="theta(t)", ylab="J(t)")
x<--10
y <- 3*x*x+2*x+1
points(x=x,y=y,pch=15,col="red")

for(i in 1:10){
  alpha <- 0.05
  x <- x-alpha*(6*x+2)
  y <- 3*x*x+2*x+1
  points(x=x,y=y,pch=17,col="green")
  
  print(x)
}

#Illustration with high alpha rate.
plot(theta1, error1 , type="l", col="blue", main="Gradient descent with higher alpha rate", xlab="theta(t)", ylab="J(t)")
x<-10
y <- 3*x*x+2*x+1
points(x=x,y=y,pch=15,col="red")

for(i in 1:10){
  print(x)
  alpha <- 0.3
  x <- x-alpha*(6*x+2)
  y <- 3*x*x+2*x+1
  points(x=x,y=y,pch=17,col="green")
  
}
