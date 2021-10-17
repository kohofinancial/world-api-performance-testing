import scala.concurrent.duration._

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._

class StatusSimulation extends Simulation { 
  val nbUsers = Integer.getInteger("users", 1)

  val httpProtocol = http
    .baseUrl("<URL>") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("Authorization service is alive") 
    .exec(
      http("GET - STATUS") 
        .get("_status")
        .check(
          status.is(200),
          bodyString.is("\"OK\"\n")
        )
    )
    .pause(5) 

  setUp(
    scn.inject(atOnceUsers(nbUsers)) 
  ).protocols(httpProtocol) 
}
