package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerKenkou;

/**
 * @author saitou
 *
 */
public class GPOKenkou extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";
  AnswerKenkou Kenkou = null;

  /**
   * @param logg
   */
  public GPOKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "さらさら健康コラム");
    Kenkou = new AnswerKenkou(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(3000);
    selector = "div.bnr>[alt='さらさら健康コラム']";
    String selector2 = "div.bnr>[alt='さらさら健康コラム']";
    if (isExistEle(driver, selector2)) {
      String wid = driver.getWindowHandle();
      clickSleepSelector(driver, selector2, 5000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(10000);
      changeWindow(driver, wid);
      selector = "a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[alt='進む']";
      while (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 4000); // 遷移　問開始
        if (isExistEle(driver, seleNextb2)) {
          Kenkou.answer(driver, seleNextb2, null);
        }
        driver.navigate().refresh();
        // アラートをけして
        checkAndAcceptAlert(driver);
        Utille.sleep(10000);
      }
    }
  }
}
