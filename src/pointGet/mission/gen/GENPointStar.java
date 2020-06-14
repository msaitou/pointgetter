package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 */
public class GENPointStar extends Mission {
  final String url = "http://www.gendama.jp/forest/";

  /**
   * @param log
   */
  public GENPointStar(Logger log, Map<String, String> cProps) {
    super(log, cProps);
    this.mName = "■ポイントの森(star)";
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(2000);
    selector = "a img[src$='star.gif']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000);
    }
    else {
      logg.warn(mName + "]獲得済み");
    }
  }
}
