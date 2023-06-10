package io.nethermind.Service;

import io.nethermind.Service.pojo.request.EthBlockNumber;
import io.nethermind.Service.pojo.request.EthGetBlockByNumber;
import io.nethermind.Service.pojo.request.EthSyncing;
import java.util.List;
import java.util.Map;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */
public class EthHelper {

  public EthSyncing ethSyncObject(Map<String, Object> sTestContext){
    EthSyncing ethSyncing = new EthSyncing();
    ethSyncing.setId(Integer.parseInt(sTestContext.get("id").toString()));
    ethSyncing.setParams((List<Object>) sTestContext.get("params"));
    ethSyncing.setMethod(sTestContext.get("method").toString());
    ethSyncing.setJsonrpc(sTestContext.get("jsonrpc").toString());
    return ethSyncing;
  }

  public EthBlockNumber ethBlockNumberObject(Map<String, Object> sTestContext){
    EthBlockNumber ethBlockNumber = new EthBlockNumber();
    ethBlockNumber.setId(Integer.parseInt(sTestContext.get("id").toString()));
    ethBlockNumber.setParams((List<Object>) sTestContext.get("params"));
    ethBlockNumber.setMethod(sTestContext.get("method").toString());
    ethBlockNumber.setJsonrpc(sTestContext.get("jsonrpc").toString());
    return ethBlockNumber;
  }

  public EthGetBlockByNumber ethGetBlockByNumberObject(Map<String, Object> sTestContext){
    EthGetBlockByNumber ethGetBlockByNumber = new EthGetBlockByNumber();
    ethGetBlockByNumber.setId(Integer.parseInt(sTestContext.get("id").toString()));
    ethGetBlockByNumber.setParams((List<Object>) sTestContext.get("params"));
    ethGetBlockByNumber.setMethod(sTestContext.get("method").toString());
    ethGetBlockByNumber.setJsonrpc(sTestContext.get("jsonrpc").toString());
    return ethGetBlockByNumber;
  }

}
