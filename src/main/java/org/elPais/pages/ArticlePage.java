package org.elPais.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ArticlePage extends BasePage{
    private By titleSelector = By.cssSelector("h1");
    private By contentSelector = By.cssSelector("article p");
    private By imageSelector = By.cssSelector("article img");

    public ArticlePage(WebDriver driver) {
        super(driver);
    }

    public String getTitle() {
        return new WebDriverWait(driver, Duration.ofSeconds(30))
                .until(ExpectedConditions.presenceOfElementLocated(titleSelector)).getText();
    }

    public String getContent() throws InterruptedException {
        Thread.sleep(2000); //synchronization issue happens when run in mobile
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(contentSelector));

        return driver.findElement(contentSelector).getText();



    }

    public String getImageUrl() {
        return driver.findElement(imageSelector).getAttribute("src");
    }

    public void openArticle(String url){
        driver.get(url);
    }

}



