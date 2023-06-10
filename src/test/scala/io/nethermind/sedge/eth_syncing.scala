package io.nethermind.sedge

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.nethermind.feeders.Feeders

object eth_syncing {

  def ethSyncing = {
    scenario("eth_syncing API")
//      .feed(Feeders.datafile)
      .exec(http("eth_syncing")
        .post("/")
        .body(StringBody(
          """{
            |    "method": "eth_syncing",
            |    "params": [],
            |    "id": 1,
            |    "jsonrpc": "2.0"
            |}""".stripMargin))
        .asJson
        .check(status.is(200))
//        .check(jsonPath("$.result").saveAs("result"))
        .check(jsonPath("$.result").is("false"))
      )
  }
}
