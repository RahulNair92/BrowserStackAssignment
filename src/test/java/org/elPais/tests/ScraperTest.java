package org.elPais.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.elPais.pages.ArticlePage;
import org.elPais.pages.BasePage;
import org.elPais.pages.HomePage;
import org.elPais.pages.OpinionPage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScraperTest extends BaseTest{
    private static final String ACCESS_KEY = "fa86abd3e4mshbba44ac963d5f6ap1a79ccjsnef2a09d207b0";
    private static final String TRANSLATE_API_URL = "https://google-translate113.p.rapidapi.com/api/v1/translator/text";
    private static final String TRANSLATE_API_HOST = "google-translate113.p.rapidapi.com";

//-javaagent:C:\Users\HP\.m2\repository\com\browserstack\browserstack-java-sdk\1.30.9\browserstack-java-sdk-1.30.9.jar
    @Test
    private void scrapperTestRun() {

            HomePage homePage = new HomePage(driver);
            homePage.WaitForBannerAndDismiss();
            if (!homePage.isSpanishLanguage()) {
                Assert.fail("Website is not in Spanish");
                driver.quit();
                return;
            }

        try {
            OpinionPage opinionPage = homePage.openOpinion();
            List<String> titles = new ArrayList<>();
            List<String> articleUrls = opinionPage.getArticleLinks();

            for (int i = 0; i < Math.min(5, articleUrls.size()); i++) {
                try {
                    ArticlePage articlePage = new ArticlePage(driver);
                    articlePage.openArticle(articleUrls.get(i));
                    String title = articlePage.getTitle();
                    String content = articlePage.getContent();

                    System.out.println("========================================");
                    System.out.println("TITLE  --> " + title);
                    System.out.println("----------------------------------------");
                    System.out.println("CONTENT:");
                    System.out.println(content);
                    System.out.println("========================================\n");

                    String imageUrl = articlePage.getImageUrl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        new BasePage(driver).downloadImage(imageUrl, "article_" + i + ".jpg");
                    }
                    titles.add(title);
                    driver.navigate().back();
                }catch (WebDriverException e){
                    e.printStackTrace();
                    continue;
                }
            }

            System.out.println("=========== TRANSLATED TITLES ===========");
            List<String> translatedTitles = new ArrayList<>();
            for (String title : titles) {
                String translatedTitle = translateText(title);
                translatedTitles.add(translatedTitle);
                System.out.println("* " + title + " --> " + translatedTitle);
            }
            printRepeatedWords(translatedTitles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String translateText(String text) {
        Response response = RestAssured.given()
                .header("X-RapidAPI-Host", TRANSLATE_API_HOST)
                .header("Content-Type", "application/json")
                .header("X-RapidAPI-Key", ACCESS_KEY)
                .body(Map.of("text", text, "from", "es", "to", "en"))
                .post(TRANSLATE_API_URL);
        return response.jsonPath().getString("trans");
    }

    private void printRepeatedWords(List<String> titles) {
        System.out.println("\n======= REPEATED WORDS MORE THAN 2 IN TITLES =======");
        Map<String, Long> wordCount = titles.stream()
                .flatMap(title -> Arrays.stream(title.toLowerCase().split("\\s+")))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        wordCount.entrySet().stream().filter(entry -> entry.getValue() > 2)
                .forEach(entry -> System.out.println("* "+ entry.getKey() + ": " + entry.getValue()));
    }


}
