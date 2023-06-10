var stats = {
    type: "GROUP",
name: "Global Information",
path: "",
pathFormatted: "group_missing-name-b06d1",
stats: {
    "name": "Global Information",
    "numberOfRequests": {
        "total": "60000",
        "ok": "48056",
        "ko": "11944"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "1",
        "ko": "6"
    },
    "maxResponseTime": {
        "total": "10477",
        "ok": "10477",
        "ko": "6539"
    },
    "meanResponseTime": {
        "total": "667",
        "ok": "311",
        "ko": "2099"
    },
    "standardDeviation": {
        "total": "1744",
        "ok": "1337",
        "ko": "2351"
    },
    "percentiles1": {
        "total": "11",
        "ok": "8",
        "ko": "597"
    },
    "percentiles2": {
        "total": "146",
        "ok": "33",
        "ko": "3196"
    },
    "percentiles3": {
        "total": "6404",
        "ok": "629",
        "ko": "6448"
    },
    "percentiles4": {
        "total": "7013",
        "ok": "7029",
        "ko": "6504"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 45856,
    "percentage": 76
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 64,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 2136,
    "percentage": 4
},
    "group4": {
    "name": "failed",
    "count": 11944,
    "percentage": 20
},
    "meanNumberOfRequestsPerSecond": {
        "total": "937.5",
        "ok": "750.875",
        "ko": "186.625"
    }
},
contents: {
"req_eth-getblockbyn-86cc4": {
        type: "REQUEST",
        name: "eth_getBlockByNumber",
path: "eth_getBlockByNumber",
pathFormatted: "req_eth-getblockbyn-86cc4",
stats: {
    "name": "eth_getBlockByNumber",
    "numberOfRequests": {
        "total": "60000",
        "ok": "48056",
        "ko": "11944"
    },
    "minResponseTime": {
        "total": "1",
        "ok": "1",
        "ko": "6"
    },
    "maxResponseTime": {
        "total": "10477",
        "ok": "10477",
        "ko": "6539"
    },
    "meanResponseTime": {
        "total": "667",
        "ok": "311",
        "ko": "2099"
    },
    "standardDeviation": {
        "total": "1744",
        "ok": "1337",
        "ko": "2351"
    },
    "percentiles1": {
        "total": "12",
        "ok": "8",
        "ko": "597"
    },
    "percentiles2": {
        "total": "146",
        "ok": "33",
        "ko": "3196"
    },
    "percentiles3": {
        "total": "6404",
        "ok": "629",
        "ko": "6448"
    },
    "percentiles4": {
        "total": "7013",
        "ok": "7029",
        "ko": "6504"
    },
    "group1": {
    "name": "t < 800 ms",
    "count": 45856,
    "percentage": 76
},
    "group2": {
    "name": "800 ms < t < 1200 ms",
    "count": 64,
    "percentage": 0
},
    "group3": {
    "name": "t > 1200 ms",
    "count": 2136,
    "percentage": 4
},
    "group4": {
    "name": "failed",
    "count": 11944,
    "percentage": 20
},
    "meanNumberOfRequestsPerSecond": {
        "total": "937.5",
        "ok": "750.875",
        "ko": "186.625"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
