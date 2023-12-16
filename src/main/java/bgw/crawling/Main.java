package bgw.crawling;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {
        AfricaTV<List<AfricaVO>> africaTV = new AfricaTV<>();

        List<AfricaVO> africaVOList = africaTV.crawling();
        StringBuilder sb = new StringBuilder();
        africaVOList.forEach(africaVO -> sb.append(africaVO.toString()).append("\n")  );
        System.out.println(sb);


    }
}