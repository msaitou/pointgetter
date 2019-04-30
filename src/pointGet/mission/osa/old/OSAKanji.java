package pointGet.mission.osa.old;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.osa.OSABase;
import pointGet.mission.parts.PlayKanji;

/**
 *
 * @author saitou 0時、12時開催
 */
public class OSAKanji extends OSABase {
  final String url = "http://osaifu.com/contents/coinland/";
  WebDriver driver = null;
  PlayKanji Kanji = null;

  /**
   * @param logg
   */
  public OSAKanji(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "漢字テスト");
    Kanji = new PlayKanji(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "li>a>img[alt='漢字テスト']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
//      changeCloseWindow(driver);
      finsishFlag = Kanji.answer(driver, "", null);
    }
  }
}
