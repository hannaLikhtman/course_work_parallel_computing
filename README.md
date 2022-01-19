## Done by Hanna Likhtman

## Description

Model builds an inverted index in a _parallel_ on study purposes.

Database to work with can be found
on [GitHub gizenmtl](https://github.com/gizenmtl/IMDB-Sentiment-Analysis-and-Text-Classification/tree/master/aclImdb)

## Installation

git clone and run as Idea project or mvn clean compile assembly:single then go to ./target and java -cp ${jarName}.jar
Main

## Run
If you want run it in parallel, enter number of threads you wish in threadNum in Main.java
**Example:** `threadNum = {5}`

If you want run it serial, just run it in 1 thread.
**Example:** `threadNum = {1}`

Also you can run with multiple different threads serially. Just pass it to threadNum
**Example:** `int[] threadNum = {1, 5, 10, 50, 100}`

