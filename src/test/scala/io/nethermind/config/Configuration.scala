package io.nethermind.config

import com.typesafe.config.ConfigFactory

object Configuration {
    // -D params from mvn test command
    var environment = System.getProperty("env")
    print("Starting test on environment::" + environment +"\n")
    // Load our own config values from the default location, src/test/resources/config -> <env>.conf
    var conf = ConfigFactory.load(environment.toLowerCase() + ".conf");
    
    def getBaseUrl(): String = {
        return conf.getString("baseUrl")
    }
    def getKey(key:String): String = {
        return conf.getString(key)
    }
}
