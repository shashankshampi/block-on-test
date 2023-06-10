package sedgetest.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.DataProvider;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */
public class EthDP {

  @DataProvider(name = "eth_syncing")
  public Object[][] eth_syncing() {
    Map<String, Object> sTestContext = new HashMap<>();
    List<Object> list = new ArrayList<>();
    sTestContext.put("method", "eth_syncing");
    sTestContext.put("params", list);
    sTestContext.put("id", "1");
    sTestContext.put("jsonrpc", "2.0");
    return new Object[][]{{sTestContext}};
  }

  @DataProvider(name = "eth_blockNumber")
  public Object[][] eth_blockNumber() {
    Map<String, Object> sTestContext = new HashMap<>();
    List<Object> list = new ArrayList<>();
    sTestContext.put("method", "eth_blockNumber");
    sTestContext.put("params", list);
    sTestContext.put("id", "1");
    sTestContext.put("jsonrpc", "2.0");
    return new Object[][]{{sTestContext}};
  }

  @DataProvider(name = "eth_chainId")
  public Object[][] eth_chainId() {
    Map<String, Object> sTestContext = new HashMap<>();
    List<Object> list = new ArrayList<>();
    sTestContext.put("method", "eth_chainId");
    sTestContext.put("params", list);
    sTestContext.put("id", "1");
    sTestContext.put("jsonrpc", "2.0");
    return new Object[][]{{sTestContext}};
  }

}
