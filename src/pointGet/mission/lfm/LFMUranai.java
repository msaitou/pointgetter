package pointGet.mission.lfm;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerUranai;

public class LFMUranai extends LFMBase {
  final String url = "http://lifemedia.jp/game/";
  AnswerUranai Uranai = null;

  /**
   * @param logg
   */
  public LFMUranai(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "星座");
    Uranai = new AnswerUranai(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    String uranaiSelector = "div>img[alt='今日の12星座占い']";
    if (isExistEle(driver, uranaiSelector)) {
      clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
      changeCloseWindow(driver);
      Utille.sleep(10000);
      String selector1 = "div#parts-slide-button__action a>img";
      String wid = driver.getWindowHandle();
      if (isExistEle(driver, selector1)) {
        Uranai.answer(driver, selector1, wid);
      }
    }
  }
}
