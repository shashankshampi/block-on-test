package io.nethermind.simulations


import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.nethermind.config.Configuration
import io.nethermind.sedge.eth_blockNumber.ethBlockNumber
import io.nethermind.sedge.eth_syncing.ethSyncing
import io.nethermind.utils.LoadInjections


class SedgeSimulation extends Simulation {

  var headers = Map(
    "Content-Type" -> "application/json"
  )

  val httpProtocol = http
    .baseUrl(Configuration.getKey("baseUrl"))
    .acceptHeader("application/json")
    .headers(headers);

  var testSetup =
    setUp(
//      ethSyncing.inject(LoadInjections.getOpenModelFromJson),
      ethBlockNumber.inject(LoadInjections.getOpenModelFromJson)
    ).protocols(httpProtocol)
}

