package org.elPais.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage{

    private By opinionLink = By.linkText("Opinión");
    private By acceptBtn =
            By.xpath("//button[@id= 'didomi-notice-agree-button'] | //a[text()='Accept and continue']");
    private By langSpanish = By.xpath("//html[@lang='es-ES']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isSpanishLanguage() {
        return driver.findElements(langSpanish).size()>0;
    }

    public OpinionPage navigateToOpinion() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement opinion = wait.until(ExpectedConditions.presenceOfElementLocated(opinionLink));
        js.executeScript("arguments[0].scrollIntoView(true);", opinion); // Scroll to element
        js.executeScript("arguments[0].click();", opinion);
        return new OpinionPage(driver);
    }

    public OpinionPage openOpinion() {
        driver.navigate().to(driver.getCurrentUrl()+"/opinion/");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.tagName("article")));
        return new OpinionPage(driver);
    }

    public void WaitForBannerAndDismiss() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        try {

            wait.until(ExpectedConditions.elementToBeClickable(acceptBtn))
                    .click();

        }catch (TimeoutException e){

        }

    }

}
