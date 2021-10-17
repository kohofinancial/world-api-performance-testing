# world-api-performance-testing
World API 2021 - Workshop - Performance Testing with Gatling


## Step 1 - Requirement


1. Installing Maven

```
sudo apt install maven
mvn -version
```

2. Installing Java

```
sudo apt install default-jdk
java -version
```


3. Generate the archetype

```
mvn archetype:generate -DarchetypeGroupId=io.gatling.highcharts -DarchetypeArtifactId=gatling-highcharts-maven-archetype -DgroupId=github.com.kohofinancial -DartifactId=world-api-performance-testing
```

4. Test your project

```
mvn gatling:test

[ERROR] No simulations to run
```