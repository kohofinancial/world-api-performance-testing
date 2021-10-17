import scala.concurrent.duration._

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._

class StatusSimulation extends Simulation { 

  val httpProtocol = http
    .baseUrl("<URL>") 
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")

  val scn = scenario("Authorization service is alive") 
    .exec(
      http("GET - STATUS") 
        .get("_status")
    ) 
    .pause(5) 

  setUp(
    scn.inject(atOnceUsers(500)) 
  ).protocols(httpProtocol) 
}
