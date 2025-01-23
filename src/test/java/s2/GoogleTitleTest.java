package s2;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
//ok
@Listeners(TestListener.class)
public class GoogleTitleTest {
	@Test
    public void verifyGoogleURL() {
	WebDriverManager.chromedriver().driverVersion("132.0.6834.84").setup();
    // Initialize WebDriver (it uses ChromiumDriver internally)
    WebDriver driver = new ChromeDriver();  // Create a new ChromeDriver instance
    driver.manage().window().maximize();
   try {
       // Open a website
       driver.get("https://www.google.com");
     //  driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);

       // Print the page title
       System.out.println("Page Title: " + driver.getTitle());
   } catch (Exception e) {
       e.printStackTrace();
   } finally {
       // Close the driver
       if (driver != null) {
           driver.quit();
       }
}}
}
//ok