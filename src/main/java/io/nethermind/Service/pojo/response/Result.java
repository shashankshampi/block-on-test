package io.nethermind.Service.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result {

  private String startingBlock;
  private String currentBlock;
  private String highestBlock;
  private Boolean isSyncing;
}
