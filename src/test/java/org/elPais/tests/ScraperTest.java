package org.elPais.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.elPais.pages.*;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ScraperTest extends BaseTest {

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
                } catch (WebDriverException e) {
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
                .header("X-RapidAPI-Host", RapidAPIKeys.TRANSLATE_API_HOST)
                .header("Content-Type", "application/json")
                .header("X-RapidAPI-Key", RapidAPIKeys.ACCESS_KEY)
                .body(Map.of("text", text, "from", "es", "to", "en"))
                .post(RapidAPIKeys.TRANSLATE_API_URL);
        return response.jsonPath().getString("trans");
    }

    private void printRepeatedWords(List<String> titles) {
        System.out.println("\n======= REPEATED WORDS MORE THAN 2 IN TITLES =======");
        Map<String, Long> wordCount = titles.stream()
                .flatMap(title -> Arrays.stream(title.toLowerCase().split("\\s+")))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
        wordCount.entrySet().stream().filter(entry -> entry.getValue() > 2)
                .forEach(entry -> System.out.println("* " + entry.getKey() + ": " + entry.getValue()));
    }


}
