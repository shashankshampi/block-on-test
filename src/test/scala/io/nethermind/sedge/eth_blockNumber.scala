package io.nethermind.sedge

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.nethermind.feeders.Feeders

object eth_blockNumber {

  def ethBlockNumber = {
    scenario("Test 2: JSON-RPC Benchmark")
//      .feed(Feeders.datafile)
/*      .exec(http("eth_blockNumber")
        .post("/")
        .body(StringBody(
          """{
            |    "method": "eth_blockNumber",
            |    "params": [],
            |    "id": 1,
            |    "jsonrpc": "2.0"
            |}""".stripMargin))
        .asJson
        .check(jsonPath("$.result").exists)
        .check(jsonPath("$.error").notExists))*/

//      .pause(1) // Add a pause between requests

      .exec(http("eth_getBlockByNumber")
        .post("/")
        .body(StringBody(
          """{
            |    "method": "eth_getBlockByNumber",
            |        "params": [
            |        "0x430032",
            |        true
            |    ],
            |    "id": 1,
            |    "jsonrpc": "2.0"
            |}""".stripMargin))
        .asJson
        .check(jsonPath("$.result").exists)
        .check(jsonPath("$.error").notExists))
  }
}
