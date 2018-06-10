package pointGet.mission.ecn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class ECNDron extends ECNBase {
  final String url = "";

  /**
   * @param log
   */
  public ECNDron(Logger log, Map<String, String> cProps) {
    super(log, cProps, "ドロンバナークリック2種");
  }

  @Override
  public void privateMission(WebDriver driver) {
    String[] dronUrlList = { "http://ecnavi.jp/", "http://ecnavi.jp/shopping/#doron" };
    selector = "div#doron a>p>img";
    for (int i = 0; i < dronUrlList.length; i++) {
      driver.get(dronUrlList[i]);
      Utille.sleep(2000);
      if (isExistEle(driver, "div.js_anime.got")) {
        logg.warn(mName + i + "]獲得済み");
      }
      else if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 2000);
      }
    }
  }
}
