package bgw.crawling.africatv;

import bgw.crawling.twitch.TwitchCategoryEnum;

import java.util.HashMap;
import java.util.Map;

public enum AfricaTVEnum {

    마인크래프트("마인크래프트","마인크래프트")
    ,스타("스타","스타")
    ,LoL("LoL","LoL")
    ,배틀그라운드("배틀그라운드","배틀그라운드")
    ,메이플스토리월드("메이플스토리","메이플스토리")
    ,TFT("TFT","TFT")
    ,로스트아크("로스트아크","로스트아크")
    ,보라("라디오","bora")
    ,이터널리턴("이터널 리턴","이터널 리턴")
    ,종합게임("종합게임","종합게임")
    ,ALL("ALL","all");
    private final String tag;
    private final String directory;

    private static final Map<String , String> tagMap = new HashMap<>();

    static {
        AfricaTVEnum[] values = AfricaTVEnum.values();
        for (AfricaTVEnum value : values) {
            tagMap.put(value.directory,value.tag);
        }
    }

    AfricaTVEnum(String tag,String directory) {
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
