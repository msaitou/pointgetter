package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerKenkou;

public class GENKenkou extends GENBase {
  final String url = "http://www.gendama.jp/";
  WebDriver driver = null;
  /* アンケートクラス　健康 */
  AnswerKenkou Kenkou = null;

  /**
   * @param logg
   */
  public GENKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "健康");
    Kenkou = new AnswerKenkou(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
//    Utille.url(driver, "http://gendama-plus.gamepark.net/", logg);
    String linkSele = "div#wrapper_rec_game li>a[onclick*='さらさら健康コラム']>img",
    a = "";
    if (isExistEle(driver, linkSele, false)) {
      clickSleepSelector(driver, linkSele, 6000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(10000);
      String wid = driver.getWindowHandle();
      changeWindow(driver, wid);
      selector = "a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[alt='進む']";
      while (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 4000); // 遷移　問開始
        if (isExistEle(driver, seleNextb2)) {
          Kenkou.answer(driver, seleNextb2, null);
        }
        Utille.refresh(driver, logg);
        // アラートをけして
        checkAndAcceptAlert(driver);
        Utille.sleep(10000);
      }
    }
  }
}
