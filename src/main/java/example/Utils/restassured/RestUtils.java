package example.Utils.restassured;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import lombok.NonNull;
import org.apache.http.HttpStatus;

public class RestUtils {

  private static final Logger logger = Logger.getLogger(RestUtils.class.getName());

  private RestUtils() {
  }

  /**
   * restRedirection handles level 1 redirection which is not supported by RestAssured for non GET/HEAD Api calls
   *
   * @param requestSpecBuilder
   * @param response
   * @param queryParams
   * @param pathParams
   * @return
   */

  private static Response restRedirection(@NonNull RequestSpecBuilder requestSpecBuilder, Response response, Map<String, Object> queryParams, Map<String, Object> pathParams, Method method) {
    String url = RequestMapper.removeParamsFromRequest(requestSpecBuilder, response, queryParams, pathParams);
    RequestSpecification resAssured = RestAssured.given(requestSpecBuilder.build());

    switch (method) {

      case POST:
        return resAssured.post(url).then().extract().response();

      case PUT:
        return resAssured.put(url).then().extract().response();

      case PATCH:
        return resAssured.patch(url).then().extract().response();

      case DELETE:
        return resAssured.delete(url).then().extract().response();

      case GET:
        return resAssured.get(url).then().extract().response();

      default:
        return null;
    }

  }

  /**
   * Get api call with headers, api path, query params, and path params
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @return
   */

  public static Response get(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).get(apiPath).then().extract().response();

     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Get api call with path
   *
   * @param requestSpecBuilder
   * @param apiPath
   * @return
   */
  public static Response get(@NonNull RequestSpecBuilder requestSpecBuilder, @NonNull String apiPath) {
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).get(apiPath).then().extract().response();

     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Get api call with headers and api path
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @return
   */
  public static Response get(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).get(apiPath).then().extract().response();

     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Post api call with all the additional fields like headers, apiPath, queryParams, pathParams and body
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @return
   */

  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Object pojo) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.POST);
    }

     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Post api call with all the additional fields like headers, apiPath, queryParams, pathParams, form param
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @param formParam
   * @return
   */

  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Map<Object, Object> formParam) {

    boolean isMultiPartContentType = false;
    if (formParam != null) {
      for (Map.Entry<Object, Object> entry : formParam.entrySet()) {
        Object key = entry.getKey();
        Object value = entry.getValue();
        if (key != null) {
          if (value.getClass() == File.class) {
            isMultiPartContentType = true;
            String mimeType = RequestMapper.getMimeType((File) value);
            if (mimeType != null) {
              requestSpecBuilder.addMultiPart(key.toString(), (File) value, mimeType);
            } else {
              requestSpecBuilder.addMultiPart(key.toString(), (File) value);
            }
          } else if (value instanceof JsonObject) {
            requestSpecBuilder.addMultiPart(key.toString(), value.toString(), "application/json");
          } else {
            requestSpecBuilder.addFormParam(key.toString(), value);
          }
        }
      }
    }

    if (isMultiPartContentType) {
      requestSpecBuilder.setContentType(ContentType.MULTIPART);

    } else {
      requestSpecBuilder.setContentType(ContentType.URLENC);
    }

    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }

    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.POST);
    }

     logger.info(response.prettyPrint());
    return response;
  }


  /**
   * Post api call with headers, api path and Body as Object
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param pojo
   * @return
   */
  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Object pojo) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.POST);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Post api call with headers,query params, path params, api path and body as string
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param body
   * @return
   */
  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, Map<String, Object> queryParams, Map<String, Object> pathParams, @NonNull String apiPath,
      String body) {

    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    requestSpecBuilder.setBody(body);
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.POST);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, String body) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    requestSpecBuilder.setBody(body);
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.POST);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Post api call with headers and api path
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @return
   */

  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.POST);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Post api call with path and path params
   *
   * @param requestSpecBuilder
   * @param apiPath
   * @param pathParams
   * @return
   */
  public static Response post(@NonNull RequestSpecBuilder requestSpecBuilder, @NonNull String apiPath, Map<String, Object> pathParams) {
    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).post(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, pathParams, Method.POST);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Put api call with all the additional fields like headers, apiPath, queryParams, pathParams
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @return
   */

  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Object pojo) {

    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).put(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.PUT);
    }

//     logger.info(response.prettyPrint());
    return response;
  }


  /**
   * put api call with headers, api path and body as string
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param body
   * @return
   */

  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, String body) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    requestSpecBuilder.setBody(body);
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).put(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.PUT);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Put api call with headers,queryParam,pathParam,formParam that supports multipart form data
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @param formParam
   * @return
   */
  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Map<Object, Object> formParam) {
    if (requestSpecBuilder == null) {
      throw new NullPointerException("requestSpecBuilder is marked non-null but is null");
    } else if (apiPath == null) {
      throw new NullPointerException("apiPath is marked non-null but is null");
    } else {
      boolean isMultiPartContentType = false;
      if (formParam != null) {
        Iterator var7 = formParam.entrySet().iterator();

        label61:
        while (true) {
          while (true) {
            while (true) {
              Object key;
              Object value;
              do {
                if (!var7.hasNext()) {
                  break label61;
                }

                Map.Entry<Object, Object> entry = (Map.Entry) var7.next();
                key = entry.getKey();
                value = entry.getValue();
              } while (key == null);

              if (value.getClass() == File.class) {
                isMultiPartContentType = true;
                String mimeType = RequestMapper.getMimeType((File) value);
                if (mimeType != null) {
                  requestSpecBuilder.addMultiPart(key.toString(), (File) value, mimeType);
                } else {
                  requestSpecBuilder.addMultiPart(key.toString(), (File) value);
                }
              } else if (value instanceof JsonObject) {
                requestSpecBuilder.addMultiPart(key.toString(), value.toString(), "application/json");
              } else {
                requestSpecBuilder.addFormParam(key.toString(), new Object[]{value});
              }
            }
          }
        }
      }

      if (isMultiPartContentType) {
        requestSpecBuilder.setContentType(ContentType.MULTIPART);
      } else {
        requestSpecBuilder.setContentType(ContentType.URLENC);
      }

      if (headers != null) {
        requestSpecBuilder.addHeaders(headers);
      }

      if (pathParams != null) {
        requestSpecBuilder.addPathParams(pathParams);
      }

      if (queryParams != null) {
        requestSpecBuilder.addQueryParams(queryParams);
      }

      requestSpecBuilder.setRelaxedHTTPSValidation();
      RequestSpecification requestSpecification = requestSpecBuilder.build();
      Response response = (Response) ((ValidatableResponse) ((Response) RestAssured.given(requestSpecification).put(apiPath, new Object[0])).then()).extract().response();
      if (response.getStatusCode() == 307) {
        response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.PUT);
      }

//       logger.info(response.prettyPrint());
      return response;
    }
  }

  /**
   * Put api call with only api path
   *
   * @param requestSpecBuilder
   * @param apiPath
   * @return
   */
  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, @NonNull String apiPath, Map<String, Object> pathParams) {
    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).put(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, pathParams, Method.PUT);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Put api call with headers, api path and headers
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @return
   */

  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).put(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.PUT);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Put api call with headers, api path and body as object
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param pojo
   * @return
   */

  public static Response put(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Object pojo) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }
    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).put(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.PUT);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Patch api call with headers, path params, query params, api path and body as object
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param pojo
   * @return
   */

  public static Response patch(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Object pojo) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }

    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).patch(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, null, Method.PATCH);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Patch api call with headers, path params, query params, api path and body as form data
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param formParam
   * @return
   */
  public static Response patch(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Map<Object, Object> formParam) {

    boolean isMultiPartContentType = false;
    for (Map.Entry<Object, Object> entry : formParam.entrySet()) {
      Object key = entry.getKey();
      Object value = entry.getValue();
      if (key != null) {
        if (value.getClass() == File.class) {
          isMultiPartContentType = true;
          String mimeType = RequestMapper.getMimeType((File) value);
          if (mimeType != null) {
            requestSpecBuilder.addMultiPart(key.toString(), (File) value, mimeType);
          } else {
            requestSpecBuilder.addMultiPart(key.toString(), (File) value);
          }
        } else {
          requestSpecBuilder.addFormParam(key.toString(), value);
        }
      }
    }

    if (isMultiPartContentType) {
      requestSpecBuilder.setContentType(ContentType.MULTIPART);
    } else {
      requestSpecBuilder.setContentType(ContentType.URLENC);
    }

    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }

    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).patch(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.PATCH);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Patch api call with headers, path and body as object
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param pojo
   * @return
   */
  public static Response patch(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams,
      Object pojo) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    if (pojo != null) {
      requestSpecBuilder.setBody(RequestMapper.mapPojoObjectAndGetString(pojo));
    }

    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).patch(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.PATCH);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Delete api call with headers,path,queryParams,pathParams
   *
   * @param requestSpecBuilder
   * @param headers
   * @param apiPath
   * @param queryParams
   * @param pathParams
   * @return
   */

  public static Response delete(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> queryParams, Map<String, Object> pathParams) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).delete(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, pathParams, Method.DELETE);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Delete api call with api path, headers and path Params
   *
   * @param requestSpecBuilder
   * @param apiPath
   * @param headers
   * @param pathParams
   * @return
   */
  public static Response delete(@NonNull RequestSpecBuilder requestSpecBuilder, Map<String, String> headers, @NonNull String apiPath, Map<String, Object> pathParams) {
    if (headers != null) {
      requestSpecBuilder.addHeaders(headers);
    }

    if (pathParams != null) {
      requestSpecBuilder.addPathParams(pathParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build().log().all();

    Response response = RestAssured.given(requestSpecification).delete(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, null, pathParams, Method.DELETE);
    }

//     logger.info(response.prettyPrint());
    return response;
  }

  /**
   * Delete api call with query params
   *
   * @param requestSpecBuilder
   * @param apiPath
   * @param queryParams
   * @return
   */
  public static Response delete(@NonNull RequestSpecBuilder requestSpecBuilder, @NonNull String apiPath, Map<String, Object> queryParams) {

    if (queryParams != null) {
      requestSpecBuilder.addQueryParams(queryParams);
    }
    requestSpecBuilder.setRelaxedHTTPSValidation();
    RequestSpecification requestSpecification = requestSpecBuilder.build();

    Response response = RestAssured.given(requestSpecification).delete(apiPath).then().extract().response();

    if (response.getStatusCode() == HttpStatus.SC_TEMPORARY_REDIRECT) {
      response = restRedirection(requestSpecBuilder, response, queryParams, null, Method.DELETE);
    }

//     logger.info(response.prettyPrint());
    return response;
  }
}