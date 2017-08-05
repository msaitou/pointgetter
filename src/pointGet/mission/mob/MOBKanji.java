package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.parts.PlayKanji;

/**
 *
 * @author saitou 0時、12時開催
 */
public class MOBKanji extends MOBBase {
  final String url = "http://pc.mtoku.jp/contents/";
  WebDriver driver = null;
  PlayKanji Kanji = null;

  /**
   * @param logg
   */
  public MOBKanji(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "漢字テスト");
    Kanji = new PlayKanji(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "img[src='https://pc-assets.mtoku.jp/common/img/contents/item_kanji.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      changeCloseWindow(driver);
      finsishFlag = Kanji.answer(driver, "", null);
    }
  }
}
