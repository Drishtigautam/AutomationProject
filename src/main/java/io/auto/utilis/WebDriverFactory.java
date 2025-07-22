package io.auto.utilis;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.chrome.ChromeOptions;



import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class WebDriverFactory {

    private static String remote = System.getProperty("remote");// use the system property to determine remote Execution
    private static String hubUrl = "http://localhost:4444/wb/hub";

    private WebDriverFactory() {

    }

    //function which create driver instance for
    public static WebDriver createDriver(Browser browser) {
        WebDriver driver;
        if ("true".equalsIgnoreCase(remote)) {
            driver = getRemoteWebDriver(browser);
        } else {
            driver = getLocalWebDriver(browser);
        }
        return driver;
    }

    //local
    public static WebDriver getLocalWebDriver(Browser browser) {
        WebDriver driver;

        //local WebDriver Setup
        switch (browser) {
            case EDGE:
                driver = new EdgeDriver();
                break;
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            default:
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-save-password-bubble");
                options.setExperimentalOption("prefs", Map.of(
                        "credentials_enable_service", false,
                        "profile.password_manager_enabled", false
                ));
                driver = new ChromeDriver(options);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    //remote
    public static WebDriver getRemoteWebDriver(Browser browser) {

        WebDriver driver;
        //remote driver setup
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(browser.getBrowserName());
        try {
            driver = new RemoteWebDriver(new URL(hubUrl).toURI().toURL(), capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        return driver;
    }

}



