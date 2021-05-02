package util;

import io.restassured.RestAssured;
import org.apache.logging.log4j.Logger;
import org.testng.*;

public class TestListener implements ISuiteListener, ITestListener {
    private static Logger logger = TestLogger.logger;

    public void onStart(ISuite iSuite) {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    public void onFinish(ISuite iSuite) {
        logger.info("Finished running all tests!");
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        logger.info("Starting test " + iTestResult.getName());
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logger.info(iTestResult.getName() + " test PASSED");
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        logger.error(iTestResult.getName() + " test FAILED");
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        logger.warn(iTestResult.getName() + " test SKIPPED");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {

    }

    @Override
    public void onStart(ITestContext iTestContext) {

    }

    @Override
    public void onFinish(ITestContext iTestContext) {

    }
}
