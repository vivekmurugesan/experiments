Sys.setenv("HADOOP_PREFIX"="/usr/local/hadoop")
Sys.setenv("HADOOP_CMD"="/usr/local/hadoop/bin/hadoop")
Sys.setenv("HADOOP_STREAMING"="/usr/local/hadoop/share/hadoop/tools/lib/hadoop-streaming-2.6.0.jar")
library(rhdfs)
library(rmr2)

hdfs.init()
hdfs.ls("/user/rstudio/data5")
#hdfs.mkdir("data")

## map function
map <- function(k,lines) {
  words.list <- strsplit(lines, '\\s') 
  words <- trimws(tolower(unlist(words.list)))
  #words <- tolower(unlist(words.list))
  #Removing .s from the words
  words <- gsub('\\.','',words)
  words <- gsub('\\,','',words)
  
  return( keyval(words, 1) )
}

## reduce function
reduce <- function(word, counts) { 
  #if(is.numeric(word))
    
  # || is.numeric(word) || length(word) <= 1 
  keyval(word, ifelse((is.element(word,stopwords))
                      ,0,sum(counts)))
}

wordcount <- function (input, output=NULL) { 
  mapreduce(input=input, output=output, input.format="text", 
            map=map, reduce=reduce)
}


## delete previous result if any
##system("/Users/hadoop/hadoop-1.1.2/bin/hadoop fs -rmr wordcount/out")

## Submit job
#hdfs.root <- 'wordcount'
#hdfs.data <- file.path(hdfs.root, '/user/rstudio/data') 
#hdfs.out <- file.path(hdfs.root, '/user/rstudio/out') 
#out <- wordcount(hdfs.data, hdfs.out)

hdfs.data <- file.path('data10') 
hdfs.out <- file.path('out10') 
out <- wordcount(hdfs.data, hdfs.out)


## Fetch results from HDFS
results <- from.dfs(out)

## check top 30 frequent words
results.df <- as.data.frame(results, stringsAsFactors=F) 
colnames(results.df) <- c('word', 'count') 
head(results.df[order(results.df$count, decreasing=T), ], 100)

r1 <- with(results.df, which((nchar(trimws(word))<=1 ),arr.ind=T))
#results.df1 <- results.df[-r,]
#r <- with(results.df1, which(is.numeric(word),arr.ind=T))
#results.df1 <- results.df1[-r,]
r2 <- with(results.df, which(count<10, arr.ind=T))
#results.df1 <- results.df1[-r,]
r <- union(r1,r2)
results.df1 <- results.df[-r,]
head(results.df1[order(results.df1$count, decreasing=T), ], 1000)
dim(results.df1)

results.sorted <- results.df1[order(results.df1$count, decreasing=T), ]
write.csv(results.sorted, file="word_count_10.csv",row.names=F)

print("RHadoop test completed successfully.!!!")


