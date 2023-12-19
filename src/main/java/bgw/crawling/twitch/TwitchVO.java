package bgw.crawling.twitch;

import bgw.crawling.CrawlingVO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TwitchVO extends CrawlingVO {
    String patternString = "\\(([^)]+)\\)";

    Pattern pattern = Pattern.compile(patternString);


    public void textExtraction(String text){
        String[] extraction = text.split("\n");
        String title = extraction[0];
        String user = extraction[1];
        Matcher matcher = this.pattern.matcher(user);

        setTitle(title);
        if(matcher.find()){
            this.setName(user.split(" ")[0]);
            this.setUserId(matcher.group(1));
            return;
        }
        setUserId(user);
        setName(user);

    }
    @Override
    public String getReplaceInt() {
        return super.getReplaceInt();
    }

    @Override
    public int getViews() {
        return super.getViews();
    }

    @Override
    public String getTitle() {
        return super.getTitle();
    }

    @Override
    public String getUserId() {
        return super.getUserId();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setReplaceInt(String replaceInt) {
        super.setReplaceInt(replaceInt);
    }

    @Override
    public void setViews(int views) {
        super.setViews(views);
    }

    @Override
    public void setTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public void setUserId(String userId) {
        super.setUserId(userId);
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public void setStringConverterViews(String views) {
        super.setStringConverterViews(views);
    }
}
