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



## Step 2 - Ping me

Let's create our first Simulation by pinging one of our service, to be sure we can connect to our service without any issues.


1. Create a new file `StatusSimulation.scala`

```
touch src/test/scala/StatusSimulation.scala

class StatusSimulation extends Simulation { 

}
```

2. Adding the HTTP Protocol 

```
  val httpProtocol = http
    .baseUrl("<URL>") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

```

3. Adding the Scenario

```
val scn = scenario("Authorization service is alive") 
    .exec(
      http("GET - STATUS") 
        .get("_status")
    ) 
    .pause(5) 
```

4. Adding the Scenario

```
  setUp(
    scn.inject(atOnceUsers(1)) 
  ).protocols(httpProtocol) 
```

5. Run it! 

```
mvn gatling:test
```

6. Let's look to the report

## Step 3 - Real time informations

1. Enable `graphite` reporting in the `src/test/resources/gatling.conf`

2. Create the `docker-compose.yml`

3. Start all dockers

```
docker-compose up -d
```

4. Go to the influxdb url `http://localhost:8086` and import the dashboard `gatling.json`

5. Run it!

```
mvn gatling:test
```

6. Data are coming to your dashboard in realtime :party:

## Step 4 - Check and Environment!

1. Gatling supports a lot of checks around your request

```
          status.is(200),
          bodyString.is("\"OK\"\n")
```

2. We can also use variable for the number of users in our simulation


## Step 5 - Real use case!

1. Adding a feeder

```
  val accountNumbers = csv("account_numbers.csv").eager.random
```

2. Adding a custom body with variable

```
    .body(ElFileBody("authorization.json")).asJson
```
