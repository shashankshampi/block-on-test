package example.Utils.restassured;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.dzieciou.testing.curl.Options;
import example.Utils.enums.FileTypes;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.response.Response;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.slf4j.event.Level;


public class RequestMapper {

  private static final Logger LOGGER = Logger.getLogger(RequestMapper.class.getName());

  private RequestMapper() {
  }

  public static String mapPojoObjectAndGetString(Object object) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    String payloadBody = null;
    try {
      payloadBody = objectMapper.writeValueAsString(object);
    } catch (Exception e) {
      String s = "Exception while converting pojo to json String";
      LOGGER.info(s);
    }
    return payloadBody;
  }

  public static String removeParamsFromRequest(RequestSpecBuilder requestSpecBuilder, Response response, Map<String, Object> queryParams, Map<String, Object> pathParams) {
    String url = response.then().extract().header("Location");
    if (pathParams != null) {
      for (Map.Entry<String, Object> entry : pathParams.entrySet()) {
        requestSpecBuilder.removePathParam(entry.getKey());
      }
    }

    if (queryParams != null) {
      for (Map.Entry<String, Object> entry : queryParams.entrySet()) {
        requestSpecBuilder.removeQueryParam(entry.getKey());
      }
    }
    return url;
  }

  /**
   * getFileExtension is used to get the file type based on its end extension after dot.
   *
   * @param file
   * @return file extension as .pdf,.jpg
   */
  static String getFileExtension(File file) {
    String name = file.getName();
    int lastIndexOf = name.lastIndexOf(".");
    if (lastIndexOf == -1) {
      return ""; // empty extension
    }
    return name.substring(lastIndexOf+1);
  }

  /**
   * getMimeType is used to decide the Content Type of file
   * @param f
   * @return content type to be used.
   */
  public static String getMimeType(File f) {
    String extension = getFileExtension(f).toUpperCase();
    FileTypes fileTypes= FileTypes.valueOf(extension);
    return fileTypes.getValue();
  }

  public static Options getOption(Level level) {
    Options options = Options.builder()
        .printMultiliner()
        .useLogLevel(level)
        .updateCurl(curl -> curl
            .removeHeader("Host")
            .removeHeader("User-Agent")
            .removeHeader("Connection"))
        .build();
    return options;
  }

  public static List<Filter> getRequestFilter() {
    List<Filter> filters = new ArrayList<>();
    filters.add(new RequestLoggingFilter());
    return filters;
  }
}
