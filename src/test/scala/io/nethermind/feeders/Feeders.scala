package io.nethermind.feeders

import io.gatling.core.Predef._

object Feeders {
    val datafile = csv("data/data.csv").circular

}
