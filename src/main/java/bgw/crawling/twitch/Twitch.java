package bgw.crawling.twitch;

import bgw.crawling.Crawling;
import bgw.crawling.CrawlingVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Twitch implements Crawling {
    private static final String[] categoryArr = {"league-of-legends","just-chatting","lost-ark","teamfight-tactics"};

    private boolean pageSettingFlag = false;

    @Override
    public List<CrawlingVO> crawling()  {
        List<CrawlingVO> crawlingVOList = new ArrayList<>();
        WebDriver driver = new ChromeDriver();
        for (String category : categoryArr) {
            categoryCrawling(driver, crawlingVOList,category);
        }


        driver.quit();
        return crawlingVOList;
    }

    private void categoryCrawling(WebDriver driver, List<CrawlingVO> crawlingVOList, String category) {
        driver.get("https://www.twitch.tv/directory/category/"+category+"?sort=VIEWER_COUNT");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
        pageSetting(driver);

        List<WebElement> contents = driver.findElement(By.className("iHafKo")).findElements(By.className("dUClxi"));

        for (WebElement content : contents) {
            TwitchVO twitchVO = new TwitchVO();
            String text = content.findElement(By.tagName("a")).getText();
            String views = content.findElement(By.className("tw-media-card-stat")).getText();
            twitchVO.textExtraction(text);
            twitchVO.setStringConverterViews(views);
            crawlingVOList.add(twitchVO);
        }
    }

    //페이지 한국어 설정으로 셋팅
    private void pageSetting(WebDriver driver) {
        if(pageSettingFlag) return;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement element = driver.findElement(By.cssSelector(".Layout-sc-1xcs6mc-0.fCJgZU.directory-header__filters"));
        List<WebElement> elements = element.findElements(By.className("ScCoreButton-sc-ocjdkq-0"));
        elements.get(0).click();
        WebElement langElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ScTransitionBase-sc-hx4quq-0.jJXXMJ.tw-transition")));
        List<WebElement> option = langElement.findElements(By.className("tw-checkbox"));
        for (WebElement e : option) {
            if (e.getText().equals("한국어")) {
                e.click();
            }
            break;
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-target='directory-container']")));
        pageSettingFlag = true;
        //sort(driver, elements, wait);


    }

    private static void sort(WebDriver driver, List<WebElement> elements, WebDriverWait wait) {

        elements.get(1).click();
        List<WebElement> elements1 = driver.findElement(By.cssSelector(".Attached-sc-go6eno-0.gNEuqV")).findElements(By.cssSelector(".Layout-sc-1xcs6mc-0.jNrYjU"));
        elements1.forEach(e ->{
            if(e.getText().equals("시청자 수 (높은 순)")){
                e.click();
            }
        });
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div[data-target='directory-container']")));
    }


}
