package sedgetest.script;


import example.Service.pojo.request.EthBlockNumber;
import example.Service.pojo.request.EthGetBlockByNumber;
import example.Service.pojo.response.EthBlockNumberRes;
import java.util.ArrayList;
import java.util.List;
import sedgetest.dp.EthDP;
import example.Service.Constants;
import example.Service.EthHelper;
import example.Service.pojo.request.EthSyncing;
import example.Utils.restassured.RestUtils;
import io.restassured.response.Response;
import java.util.Map;
import java.util.logging.Logger;
import org.testng.annotations.Test;

/**
 * @author shashank.sanket
 * SAMPLE CODE FOR TEST
 */


public class EthSedgeTest extends BaseTests {

    private static final Logger logger = Logger.getLogger(EthSedgeTest.class.getName());
    EthHelper ethHelper = new EthHelper();

    @Test(groups = {"functional"}, description = "Verify eth_syncing Data",dataProviderClass = EthDP.class, dataProvider = "eth_syncing",priority = 0)
    public void eth_syncing_test(Map<String, Object> sTestContext ) {
        logger.info("Test to verify eth_syncing call return false");
        requestSpecBuilder.setBaseUri(Constants.url);
        EthSyncing ethSyncing = ethHelper.ethSyncObject(sTestContext);
        Response response = RestUtils.post(requestSpecBuilder,null,"",ethSyncing);
        responseValidator.validateEthSync(response,softAssert);
        softAssert.assertAll();
    }

    @Test(groups = {"functional"}, description = "Verify eth_blockNumber Data",dataProviderClass = EthDP.class, dataProvider = "eth_blockNumber",priority = 1)
    public void eth_blockNumber_test(Map<String, Object> sTestContext ) {
        logger.info("Test to verify eth_blockNumber");
        List<Object> list = new ArrayList<>();

        requestSpecBuilder.setBaseUri(Constants.url);
        EthBlockNumber ethBlockNumber = ethHelper.ethBlockNumberObject(sTestContext);
        logger.info("Retrieve the chain head from a synced node using the eth_blockNumber API call.");
        Response response = RestUtils.post(requestSpecBuilder,null,"",ethBlockNumber);
        EthBlockNumberRes ethBlockNumberRes = response.as(EthBlockNumberRes.class);
        logger.info("Block Number obtained from eth_blockNumber API call: " + ethBlockNumberRes.getResult());

        list.add(ethBlockNumberRes.getResult());
        list.add(true);
        sTestContext.put("params",list);
        sTestContext.put("method","eth_getBlockByNumber");

        EthGetBlockByNumber ethGetBlockByNumber = ethHelper.ethGetBlockByNumberObject(sTestContext);
        logger.info("Obtain block details using the eth_getBlockByNumber endpoint");
        response = RestUtils.post(requestSpecBuilder,null,"",ethGetBlockByNumber);
        responseValidator.validateEthGetBlockByNumber(response,softAssert);
        softAssert.assertAll();
    }


}

