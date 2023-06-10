package io.nethermind.Service.pojo.request;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */
import java.util.List;
import lombok.Data;


@Data
public class EthBlockNumber {

  private String method;
  private List<Object> params;
  private Integer id;
  private String jsonrpc;

}
