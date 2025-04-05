package org.elPais.tests;



import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    WebDriver driver;
    String website = "https://elpais.com/";

    @BeforeMethod
    public void setup() throws MalformedURLException {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        Object deviceName = ((RemoteWebDriver)driver)
                .getCapabilities()
                .getCapability("deviceName");

        if (deviceName == null) {
            System.out.println("Running on a desktop environment. Maximizing window.");
            driver.manage().window().maximize();
        }

        driver.get(website);
    }

    @AfterMethod
    public void teardown(){
        driver.quit();
    }

}
