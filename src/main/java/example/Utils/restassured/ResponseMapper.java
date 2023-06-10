package example.Utils.restassured;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.logging.Logger;

public class ResponseMapper {

  private static final Logger LOGGER = Logger.getLogger(ResponseMapper.class.getName());

  private ResponseMapper() {
  }

  public static Object mapResponseObjectToPojoObject(Object object, Class tClass) {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    Object obj = objectMapper.convertValue(object, tClass);
    return obj;
  }
}

