import scala.concurrent.duration._

import io.gatling.core.Predef._ 
import io.gatling.http.Predef._
import pdi.jwt.{Jwt, JwtAlgorithm, JwtHeader, JwtClaim, JwtOptions}

class AuthorizationSimulation extends Simulation { 
  val nbUsers = Integer.getInteger("users", 20)
  val accountNumbers = csv("account_numbers.csv").eager.random

  val token = Jwt.encode("""{"user":1}""", "apple.banana.cherry", JwtAlgorithm.HS256)

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
    .feed(accountNumbers)
    .exec(
      http("POST - AUTHORIZATION")
        .post("partners/galileo/Authorization")
        .header(HttpHeaderNames.ContentType, HttpHeaderValues.ApplicationJson)
        .header(HttpHeaderNames.Accept, HttpHeaderValues.ApplicationJson)
        .header("Authorization", token:String)
        .body(ElFileBody("authorization.json")).asJson
        .check(status.is(200))
        .check(jsonPath("$.response_code").exists)
        .check(jsonPath("$.response_code").in("00","51"))
    )

  setUp(
    scn.inject(
      nothingFor(4.seconds), 
      atOnceUsers(nbUsers),
      rampUsers(50).during(1.minutes),
      rampUsersPerSec(10).to(20).during(1.minutes).randomized
    ) 
  ).protocols(httpProtocol) 
}
