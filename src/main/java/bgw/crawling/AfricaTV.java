package bgw.crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public  class AfricaTV<T extends List<AfricaVO>> implements Crawling<T> {
    private final List<AfricaVO>  africaVOS = new ArrayList<>();
    private T  result;
    AfricaTV(){

    };

    public T crawling(){
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.afreecatv.com/?hash=all");
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        WebElement textBox = driver.findElement(By.id("broadlist_area"));

        List<WebElement> li = textBox.findElements(By.tagName("li"));

        for (WebElement element: li) {
            AfricaVO africaVO = new AfricaVO();
            WebElement userElement = element.findElement(By.className("details")).findElement(By.tagName("a"));
            String userId =userElement.getAttribute("user_id");
            String name = userElement.getText();
            String views = element.findElement(By.className("views")).getText();
            String title = element.findElement(By.className("title")).getText();
            africaVO.setUserId(userId);
            africaVO.setName(name);
            africaVO.setStringConverterViews(views);
            africaVO.setTitle(title);
            this.africaVOS.add(africaVO);
        }
        result = (T) africaVOS;
        driver.quit();
        return result;
    }



}
