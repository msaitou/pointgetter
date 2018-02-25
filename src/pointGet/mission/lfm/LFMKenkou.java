package pointGet.mission.lfm;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerKenkou;

public class LFMKenkou extends LFMBase {
  final String url = "http://lifemedia.jp/game/";
  AnswerKenkou Kenkou = null;

  /**
   * @param logg
   */
  public LFMKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "健康");
    Kenkou = new AnswerKenkou(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    String uranaiSelector = "div>img[alt='さらさら健康コラム']";
    if (isExistEle(driver, uranaiSelector)) {
      clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
      String wid = driver.getWindowHandle();
      changeWindow(driver, wid);
      Utille.sleep(10000);
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
