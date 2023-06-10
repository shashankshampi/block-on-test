package example.Service.pojo.request;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */

import java.util.List;
import lombok.Data;

@Data
public class EthGetBlockByNumber {

  public String method;
  public List<Object> params;
  public Integer id;
  public String jsonrpc;

}
