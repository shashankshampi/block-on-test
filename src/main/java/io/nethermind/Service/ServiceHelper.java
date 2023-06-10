package io.nethermind.Service;


import com.github.dzieciou.testing.curl.CurlHandler;
import com.github.dzieciou.testing.curl.CurlRestAssuredConfigFactory;
import io.nethermind.Utils.LoggerUtil;
import io.nethermind.Utils.restassured.RequestMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.event.Level;

public class ServiceHelper {

//  private static final Logger LOGGER = Logger.getLogger(ServiceHelper.class.getName());
private static final LoggerUtil LOGGER = new LoggerUtil(ServiceHelper.class);

  public HashMap<String, String> createHeader() {
    HashMap<String, String> header = new HashMap();
    header.put("Content-Type", "application/json");
    return header;
  }

  public Map<String, Object> getQueryParams(Map<String, Object> sTestContext, List<String> query) {
    Map<String, Object> queryParams = new HashMap<>();
    for (String name : query) {
      queryParams.put(name, sTestContext.get(name));
    }
    return queryParams;
  }

  public ThreadLocal<RequestSpecBuilder> getReqSpecBuilderThread() {
    ThreadLocal<RequestSpecBuilder> requestSpecBuilder = ThreadLocal.withInitial(RequestSpecBuilder::new);
    requestSpecBuilder.get().addHeader("Content-Type", "application/json");
    requestSpecBuilder.get().addHeader("accept", "application/json");
    return requestSpecBuilder;
  }

  public RequestSpecBuilder getReqSpecBuilder() {
    RequestSpecBuilder requestSpecBuilder = getReqSpecBuilderThread().get();
      List<CurlHandler> handlers = Collections.singletonList(LOGGER);
      RestAssuredConfig config = CurlRestAssuredConfigFactory.createConfig(RequestMapper.getOption(Level.DEBUG), handlers);
      requestSpecBuilder.addFilters(RequestMapper.getRequestFilter()).setConfig(config);

    return requestSpecBuilder;
  }
}
