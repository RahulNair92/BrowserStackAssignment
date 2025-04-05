package org.elPais.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class BasePage {

    protected WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void downloadImage(String imageUrl, String fileName) {
        try {
            URL url = new URL(imageUrl);
            Path targetPath = Path.of("images", fileName);
            Files.createDirectories(targetPath.getParent());
            Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image saved: " + targetPath);
        } catch (IOException e) {
            System.out.println("Failed to download image: " + e.getMessage());
        }
    }
}
