package example.Service.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthBlockNumberRes {

  private String jsonrpc;
  private String result;
  private Integer id;

}
