package io.nethermind.Service.pojo.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Data;

/**
 * @author shashanksanket
 * @Date 10/06/23
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultBlockByNumberRes {
  private String author;
  private String difficulty;
  private String extraData;
  private String gasLimit;
  private String gasUsed;
  private String hash;
  private String logsBloom;
  private String miner;
  private String number;
  private String mixHash;
  private String nonce;
  private String parentHash;
  private String receiptsRoot;
  private String sha3Uncles;
  private String signature;
  private String size;
  private String stateRoot;
  private Integer step;
  private String totalDifficulty;
  private String timestamp;
  private String baseFeePerGas;
  private List<Object> transactions;
  private String transactionsRoot;
  private List<Object> uncles;
  private List<Withdrawal> withdrawals;
  private String withdrawalsRoot;
}
