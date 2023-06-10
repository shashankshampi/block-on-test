package sedgetest.script;


import example.Service.ServiceHelper;
import example.Utils.CommonHelpers;
import io.restassured.builder.RequestSpecBuilder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;
import sedgetest.validator.ResponseValidator;

public class BaseTests {

  protected SoftAssert softAssert;
  protected ResponseValidator responseValidator;
  protected CommonHelpers commonHelpers;
  protected ServiceHelper serviceHelper;
  protected RequestSpecBuilder requestSpecBuilder;

  @BeforeMethod
  public void intiBefore() {
    softAssert = new SoftAssert();
    requestSpecBuilder = serviceHelper.getReqSpecBuilder();
  }

  @BeforeSuite
  public void initSuits() {
    responseValidator = new ResponseValidator();
    commonHelpers = new CommonHelpers();
    serviceHelper = new ServiceHelper();
  }
}
