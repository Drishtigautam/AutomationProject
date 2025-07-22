package io.auto.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.restassured.response.Response;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.fasterxml.jackson.databind.ObjectMapper;

public class APITestListener implements ITestListener {

    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    static {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports-api.html");
        extentReports.attachReporter(sparkReporter);
    }

    public static ExtentTest getTest() {
        return extentTest.get();
    }


    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getMethod().getMethodName());
        extentTest.set(test);
        test.info("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        if(extentTest.get() != null) {
            Response apiResponse= (Response)result.getTestContext().getAttribute("ApiResponse");
            extentTest.get().pass("Test Passed");
            extentTest.get().info("Response Body: "+apiResponse.getBody().asString());
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().fail("Test Failed: " + result.getThrowable());

            //Retrieve Api request and response from the context
            String apiRequest= (String) result.getTestContext().getAttribute("ApiRequest");
            Response apiResponse= (Response)result.getTestContext().getAttribute("ApiResponse");

            if(apiRequest != null && apiResponse!= null){
                //log the api request and response details
                extentTest.get().info("API Request" + apiRequest);
                extentTest.get().info("Response Status" + apiResponse.getStatusCode());
                extentTest.get().info("Response Body" + formatJSON(apiResponse.getBody().asString()));
            }else{
                extentTest.get().info("Api Request/Response Data not available");
            }
        }
    }


    @Override
    public void onTestSkipped(ITestResult result) {
        extentTest.get().skip("Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extentReports.flush();
    }

    @Override
    public void onStart(ITestContext context) {
        //No Implementation needed
    }

    //utility method to format the json better readability
    private String formatJSON(String jsonString) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(mapper.readTree(jsonString));
        } catch (Exception e) {
            throw new RuntimeException("Failed to format JSON", e);
        }
    }

}

