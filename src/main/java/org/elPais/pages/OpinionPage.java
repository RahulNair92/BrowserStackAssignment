package org.elPais.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OpinionPage extends BasePage{

    private By article = By.tagName("article");

    public OpinionPage(WebDriver driver) {
        super(driver);
    }

    public List<String> getArticleLinks() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            Thread.sleep(2000); //occational stale exception on firefox
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(article));
        List<String> articleLinks = new ArrayList<>();

        for (WebElement element : elements) {
            try {
                String dataWord = element.getAttribute("data-word");
                if (dataWord != null && !dataWord.isEmpty() && Integer.parseInt(dataWord) > 0) {
                    String href = element.findElement(By.cssSelector("h2 a")).getAttribute("href");
                    articleLinks.add(href);
                }
            } catch (StaleElementReferenceException e) {

                elements = driver.findElements(article);
                continue;
            }
        }

        return articleLinks;
    }

    public ArticlePage openArticle(WebElement articleLink) {
        new Actions(driver).scrollToElement(articleLink).click().build().perform();
        return new ArticlePage(driver);
    }

}
