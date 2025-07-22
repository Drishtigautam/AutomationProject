package io.auto.listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import io.auto.utilis.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.*;

public class TestListener implements ITestListener {

    private static final ExtentReports extentReports = new ExtentReports();
    private static final ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    static {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports.html");
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
            extentTest.get().pass("Test Passed");
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        if (extentTest.get() != null) {
            extentTest.get().fail("Test Failed: " + result.getThrowable());

            WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driver");

            if (driver != null) {
                String screenShotPath = ScreenshotUtil.captureScreenshot(driver, result.getMethod().getMethodName());
                extentTest.get().addScreenCaptureFromPath(screenShotPath.replace("target",""));
            } else {
                extentTest.get().info("WebDriver instance is null. Unable to capture screenshot.");
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
}

