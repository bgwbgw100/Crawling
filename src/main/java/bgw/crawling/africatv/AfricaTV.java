package bgw.crawling.africatv;

import bgw.crawling.Crawling;
import bgw.crawling.CrawlingVO;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

@Slf4j
public  class AfricaTV implements Crawling {

    private static final  List<String> categoryArr =  Arrays.asList("game","bora","all") ;
    private static final String[] gameTags = {"마인크래프트","스타","LoL","배틀그라운드","메이플스토리","TFT","로스트아크","종합게임","이터널 리턴"};


    public List<CrawlingVO> crawling(){
        List<CrawlingVO>  africaVOS = new ArrayList<>();

        categoryArr.parallelStream().forEach( category ->{
            if("game".equals(category)){

                    tagCrawling(africaVOS,category);

            }else {
                categoryCrawling(africaVOS,category);
            }

        });
        return africaVOS;
    }

    private void categoryCrawling(List<CrawlingVO> africaVOS,String category) {
        WebDriver driver = null;
        try {
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            driver.get("https://www.afreecatv.com/?hash=" + category);
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(1000));
            driver.findElement(By.className("btn-more")).findElement(By.tagName("button")).click();
            getBrodList(africaVOS, driver, wait, category);
        } catch (Exception e) {
            log.error("ElementNotInteractableException " ,e);
        }finally {
            driver.quit();
        }
    }
    private void tagCrawling(List<CrawlingVO> africaVOS,String category) {
        String tags[] = category.equals("game") ?  gameTags : null;
        if(tags == null) return;



        Arrays.stream(tags).parallel().forEach(tag -> {

            WebDriver driver = null;
            try {
                driver = new ChromeDriver();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

                driver.get("https://www.afreecatv.com/?hash=" + category);
                driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

                WebElement titleArea = driver.findElement(By.id("title_area"));
                List<WebElement> li = titleArea.findElements(By.tagName("li"));
                for (WebElement element : li) {
                    if ("텍스트형".equals(element.getText())) element.click();
                }

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("subCategory")));

                WebElement subCategory = driver.findElement(By.id("subCategory"));

                WebElement ul = subCategory.findElement(By.className("category_ul"));
                List<WebElement> categoryList = ul.findElements(By.tagName("li"));

                for (WebElement categoryLi : categoryList) {
                    WebElement a = categoryLi.findElement(By.tagName("a"));

                    if (tag.equals(a.getText())) {
                        log.debug(a.getText());
                        a.click();
                        break;
                    }

                }

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
                getBrodList(africaVOS, driver, wait, tag);

            } catch (Exception e) {
                log.error("ElementNotInteractableException ", e);
            } finally {
                driver.quit();
            }

        });




    }

    private static void getBrodList(List<CrawlingVO> africaVOS, WebDriver driver,WebDriverWait wait,String category) {

        WebElement textBox = driver.findElement(By.id("broadlist_area"));

        List<WebElement> li = textBox.findElements(By.tagName("li"));



        for (WebElement element: li) {
            CrawlingVO africaVO = new AfricaVO();
            wait.until(ExpectedConditions.visibilityOf(element));


            WebElement userElement = element.findElement(By.className("details")).findElement(By.tagName("a"));
            String userId =userElement.getAttribute("user_id");
            String name = userElement.getText();
            String views = element.findElement(By.className("views")).getText();
            String title = element.findElement(By.className("title")).getText();
            africaVO.setUserId(userId);
            africaVO.setName(name);
            africaVO.setStringConverterViews(views);
            africaVO.setTitle(title);
            africaVO.setTag(category);
            africaVOS.add(africaVO);
        }
    }









}
