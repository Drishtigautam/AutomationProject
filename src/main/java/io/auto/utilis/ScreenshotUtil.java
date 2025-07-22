package io.auto.utilis;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    // Private constructor to prevent instantiation
    private ScreenshotUtil() {
    }

    public static String captureScreenshot(WebDriver driver, String screenshotName) {
        // Capture screenshot as a File
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Generate file path with timestamp
        String filePath = getFilePath(screenshotName);

        // Create destination file object
        File destFile = new File(filePath);

        // Copy file from source to destination
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace(); // Optional: log this with logger
        }

        return filePath;
    }

    private static String getFilePath(String name) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return "target/screenshots/" + name + "_" + timestamp + ".png";
    }
}
