package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerUranai;

public class MOPUranai extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";
  AnswerUranai Uranai = null;
  /**
   * @param logg
   */
  public MOPUranai(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "星座");
    Uranai = new AnswerUranai(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "div.game_btn>div.icon>img[alt='CMくじ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(9000);
      String uranaiSelector = "dl>dt>img[src*='kumakumaseiza_pc']";
      if (isExistEle(driver, uranaiSelector)) {
        clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
        String wid = driver.getWindowHandle();
        changeCloseWindow(driver);
        Utille.sleep(15000);
        String selector1 = "section>div>form>input[type=image]";
        Uranai.answer(driver, selector1, wid);
      }
    }
  }
}
