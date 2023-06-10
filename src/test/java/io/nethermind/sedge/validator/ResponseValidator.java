package io.nethermind.sedge.validator;


import io.nethermind.Service.pojo.response.EthGetBlockByNumberRes;
import io.nethermind.Service.pojo.response.EthSyncingRes;
import io.restassured.response.Response;
import java.util.logging.Logger;
import org.apache.http.HttpStatus;
import org.testng.asserts.SoftAssert;

public class ResponseValidator {

  private static final Logger logger = Logger.getLogger(ResponseValidator.class.getName());

  public void validateEthSync(Response response, SoftAssert softAssert) {
    EthSyncingRes ethSyncingRes = response.as(EthSyncingRes.class);
    softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "validating status code " + HttpStatus.SC_OK);
    softAssert.assertEquals(ethSyncingRes.getResult().toString(), "false", "validating result as false");
//    softAssert.assertEquals(ethSyncingRes.getResult().getIsSyncing(), Boolean.FALSE, "validating isSyncing as " + Boolean.FALSE);

  }

  public void validateEthGetBlockByNumber(Response response, SoftAssert softAssert) {
    EthGetBlockByNumberRes ethGetBlockByNumberRes = response.as(EthGetBlockByNumberRes.class);
    softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "validating status code " + HttpStatus.SC_OK);

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult(), "validating block result in not empty");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getAuthor(), "validating value getAuthor");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getDifficulty(), "validating value getDifficulty");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getExtraData(), "validating value getExtraData");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getGasLimit(), "validating value getGasLimit");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getGasUsed(), "validating value getGasUsed");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getHash(), "validating value getHash");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getLogsBloom(), "validating value getLogsBloom");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getMiner(), "validating value getMiner");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getMixHash(), "validating value getMixHash");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getNonce(), "validating value getNonce");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getNumber(), "validating value getNumber");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getParentHash(), "validating value getParentHash");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getReceiptsRoot(), "validating value getReceiptsRoot");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getSha3Uncles(), "validating value getSha3Uncles");

//    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getSignature(), "validating value getSignature");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getSize(), "validating value getSize");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getStateRoot(), "validating value getStateRoot");

//    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getStep(), "validating value getStep");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getTotalDifficulty(), "validating value getTotalDifficulty");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getTimestamp(), "validating value getTimestamp");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getBaseFeePerGas(), "validating value getBaseFeePerGas");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getTransactions(), "validating value getTransactions");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getTransactionsRoot(), "validating value getTransactionsRoot");

    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getUncles(), "validating value getUncles");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getWithdrawals(), "validating value getWithdrawals");
    softAssert.assertNotNull(ethGetBlockByNumberRes.getResult().getWithdrawalsRoot(), "validating value getWithdrawalsRoot");
  }

  public void validateEthChainId(Response response, SoftAssert softAssert) {
    EthSyncingRes ethChainId = response.as(EthSyncingRes.class);
    softAssert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "validating status code " + HttpStatus.SC_OK);
    softAssert.assertNotNull(ethChainId.getResult(), "validating result not null");

  }
}
