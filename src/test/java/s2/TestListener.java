package s2;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestListener implements ITestListener {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentTest test;
    private static String screenshotDir = "screenshots";
    private static String passDir = "pass";
    private static String failDir = "fail";

    @Override
    public void onStart(ITestContext context) {
        // Setup ExtentReports
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("extent-report.html");
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Initialize ExtentTest for the current test case
        test = extent.createTest(result.getMethod().getMethodName());
        System.out.println("Test Started: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.pass("Test passed successfully");
        captureScreenshot("pass", result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.fail("Test failed");
        captureScreenshot("fail", result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.skip("Test skipped");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        test.fail("Test failed but within success percentage");
    }

    @Override
    public void onFinish(ITestContext context) {
        // Write the final report to the file
        extent.flush();
    }

    private void captureScreenshot(String status, ITestResult result) {
        // Capture screenshot
        if (driver == null) {
            driver = new ChromeDriver(); // Create WebDriver instance if not already initialized
        }

        // Take screenshot
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String dir = status.equals("pass") ? passDir : failDir;
        Path destinationPath = Paths.get(screenshotDir, dir, result.getMethod().getMethodName() + ".png");

        try {
            Files.createDirectories(destinationPath.getParent()); // Create directories if they don't exist
            Files.copy(srcFile.toPath(), destinationPath); // Copy screenshot file
            System.out.println("Screenshot saved to: " + destinationPath.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

