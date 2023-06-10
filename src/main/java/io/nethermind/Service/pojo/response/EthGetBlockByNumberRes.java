package io.nethermind.Service.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EthGetBlockByNumberRes {

  private String jsonrpc;
  @JsonProperty("result")
  private ResultBlockByNumberRes result;
  private Integer id;

}
