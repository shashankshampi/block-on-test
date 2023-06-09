package io.nethermind.utils;

import io.gatling.core.controller.inject.open.OpenInjectionStep;
import org.json.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import scala.concurrent.duration.FiniteDuration;

public class SimulationPatternSetup {

    public static String patternJson = null;
    private final static String simulationPatternFile =
            System.getProperty("user.dir")
                    + File.separator + "src/test/resources"
                    + File.separator + "SimulationPattern.json";

    public static List<OpenInjectionStep> openInjectors() {

        if (System.getProperty("simulationPattern").isEmpty()) {
            try {
                patternJson = new String(Files.readAllBytes(Paths.get(simulationPatternFile)));
            } catch (Exception e) {
                System.out.println("Exception while reading simulation Pattern File:: \n");
                e.printStackTrace();
            }
        } else
            patternJson = System.getProperty("simulationPattern");

        ArrayList<OpenInjectionStep> openInjectionStep = new ArrayList<>();
        JSONObject obj = new JSONObject(patternJson);
        JSONObject pattern;
        JSONArray patternArr = obj.getJSONArray("pattern");

        for (Object objPattern : patternArr) {
            pattern = (JSONObject) objPattern;
            switch (pattern.get("type").toString().toUpperCase()) {
                case "NOTHING_FOR": {
                    System.out.println("NOTHING_FOR:: " + pattern);
                    long dur = Long.parseLong(pattern.get("duration").toString());
                    FiniteDuration duration = FiniteDuration.create(dur, TimeUnit.SECONDS);
                    openInjectionStep.add(io.gatling.core.Predef.nothingFor(duration));
                    break;
                }
                case "AT_ONCE_USERS": {
                    System.out.println("AT_ONCE_USERS:: " + pattern);
                    int users = Integer.parseInt(pattern.get("users").toString());
                    openInjectionStep.add(io.gatling.core.Predef.atOnceUsers(users));
                    break;
                }
                case "RAMP_USERS": {
                    System.out.println("RAMP_USERS:: " + pattern);
                    int users = Integer.parseInt(pattern.get("users").toString());
                    long dur = Long.parseLong(pattern.get("duration").toString());
                    FiniteDuration duration = FiniteDuration.create(dur, TimeUnit.SECONDS);
                    openInjectionStep.add(io.gatling.core.Predef.rampUsers(users).during(duration));
                    break;
                }
                case "RAMP_USERS_PER_SEC": {
                    System.out.println("RAMP_USERS_PER_SEC:: " + pattern);
                    int users = Integer.parseInt(pattern.get("users").toString());
                    int to = Integer.parseInt(pattern.get("toUser").toString());
                    long dur = Long.parseLong(pattern.get("duration").toString());
                    FiniteDuration duration = FiniteDuration.create(dur, TimeUnit.SECONDS);
                    openInjectionStep.add(io.gatling.core.Predef.rampUsersPerSec(users).to(to).during(duration));
                    break;
                }
                case "CONSTANT_USERS_PER_SEC": {
                    System.out.println("CONSTANT_USERS_PER_SEC:: " + pattern);
                    int users = Integer.parseInt(pattern.get("users").toString());
                    long dur = Long.parseLong(pattern.get("duration").toString());
                    FiniteDuration duration = FiniteDuration.create(dur, TimeUnit.SECONDS);
                    openInjectionStep.add(io.gatling.core.Predef.constantUsersPerSec(users).during(duration));
                    break;
                }
                default:
                    System.out.println("NOT A VALID SIMULATION TYPE:: " + pattern.get("type").toString().toUpperCase() + " Or Belongs To Closed Injection Model");
                    break;
            }
        }
        return openInjectionStep;
    }
}
