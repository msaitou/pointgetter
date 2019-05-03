package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerTenki;

/**
 * @author saitou
 *
 */
public class GPOTenki extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";
  AnswerTenki Tenki = null;

  /**
   * @param logg
   */
  public GPOTenki(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "天気");
    Tenki = new AnswerTenki(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String selector2 = "img[alt='今日の天気予報']";
    if (isExistEle(driver, selector2)) {
      String wid = driver.getWindowHandle();
      clickSleepSelector(driver, selector2, 5000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      changeWindow(driver, wid);
      Tenki.answer(driver, null, wid);
    }
  }
}
