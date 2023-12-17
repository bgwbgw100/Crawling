package bgw.crawling;

import lombok.Data;

@Data
public class CrawlingVO {
    private String replaceInt = "[^0-9]";
    private int views;
    private String title;
    private String userId;
    private String name;



    public void setStringConverterViews(String views) {

        String replaceViews = views.replaceAll(replaceInt, "");

        if (replaceViews.equals("")) setViews(0);
        else setViews(Integer.parseInt(replaceViews));

    }
}
