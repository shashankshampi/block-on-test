package io.nethermind.utils

import io.gatling.core.controller.inject.open.OpenInjectionStep

import scala.collection.JavaConverters.asScalaBufferConverter

object LoadInjections {
    def getOpenModelFromJson: List[OpenInjectionStep] = {
        SimulationPatternSetup.openInjectors().asScala.toList
    }
}
