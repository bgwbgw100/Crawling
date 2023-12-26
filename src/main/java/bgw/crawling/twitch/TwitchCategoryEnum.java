package bgw.crawling.twitch;

import java.util.HashMap;
import java.util.Map;

public enum TwitchCategoryEnum {
    마인크래프트("마인크래프트","minecraft")
    ,스타("스타","starcraft")
    ,LoL("LoL","league-of-legends")
    ,배틀그라운드("배틀그라운드","pubg-battlegrounds")
    ,메이플스토리("메이플스토리","maplestory")
    ,메이플스토리월드("메이플스토리","maplestory-worlds")
    ,TFT("TFT","teamfight-tactics")
    ,로스트아크("로스트아크","lost-ark")
    ,저스트채팅("라디오","just-chatting")
    ,이터널리턴("이터널 리턴","eternal-return")
    ,ALL("ALL","all");
    private final String tag;
    private final String directory;

    private static final Map<String , String> tagMap = new HashMap<>();

    static {
        TwitchCategoryEnum[] values = TwitchCategoryEnum.values();
        for (TwitchCategoryEnum value : values) {
            tagMap.put(value.directory,value.tag);
        }
    }

    TwitchCategoryEnum(String tag,String directory) {
        this.tag = tag;
        this.directory = directory;
    }
    public static String tagConverterByDirectory(String directory) {
        String result = tagMap.get(directory);
        result = result == null ? directory : result;
        result = result == null ? "non" : result;
        return result;
    }


}
