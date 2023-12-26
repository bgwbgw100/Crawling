package bgw.crawling.africatv;

import bgw.crawling.CrawlingVO;

public class AfricaVO extends CrawlingVO {
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
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    protected boolean canEqual(Object other) {
        return super.canEqual(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public AfricaVO() {
        super();
    }

    @Override
    public void setStringConverterViews(String views) {
        super.setStringConverterViews(views);
    }

    @Override
    public String getTag() {
        return super.getTag();
    }

    @Override
    public void setTag(String tag) {
        AfricaTVEnum.tagConverterByDirectory(tag);
        super.setTag(tag);
    }
}
