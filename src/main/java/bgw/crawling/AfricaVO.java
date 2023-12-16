package bgw.crawling;

public class AfricaVO {
    private String replaceInt = "[^0-9]";
    private int views;
    private String title;
    private String userId;
    private String name;

    public AfricaVO() {
    }

    public void setStringConverterViews(String views) {

        String replaceViews = views.replaceAll(replaceInt, "");

        if (replaceViews.equals("")) setViews(0);
        else setViews(Integer.parseInt(replaceViews));

    }

    public String getReplaceInt() {
        return this.replaceInt;
    }

    public int getViews() {
        return this.views;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUserId() {
        return this.userId;
    }

    public String getName() {
        return this.name;
    }

    public void setReplaceInt(String replaceInt) {
        this.replaceInt = replaceInt;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AfricaVO)) return false;
        final AfricaVO other = (AfricaVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$replaceInt = this.getReplaceInt();
        final Object other$replaceInt = other.getReplaceInt();
        if (this$replaceInt == null ? other$replaceInt != null : !this$replaceInt.equals(other$replaceInt))
            return false;
        if (this.getViews() != other.getViews()) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final Object this$userId = this.getUserId();
        final Object other$userId = other.getUserId();
        if (this$userId == null ? other$userId != null : !this$userId.equals(other$userId)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof AfricaVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $replaceInt = this.getReplaceInt();
        result = result * PRIME + ($replaceInt == null ? 43 : $replaceInt.hashCode());
        result = result * PRIME + this.getViews();
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $userId = this.getUserId();
        result = result * PRIME + ($userId == null ? 43 : $userId.hashCode());
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        return result;
    }

    public String toString() {
        return "AfricaVO(replaceInt=" + this.getReplaceInt() + ", views=" + this.getViews() + ", title=" + this.getTitle() + ", userId=" + this.getUserId() + ", name=" + this.getName() + ")";
    }
}
