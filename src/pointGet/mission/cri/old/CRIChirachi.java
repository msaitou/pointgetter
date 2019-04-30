package pointGet.mission.cri.old;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.cri.CRIBase;

/**
 * @author saitou
 *
 */
public class CRIChirachi extends CRIBase {
  final String url = "http://www.chobirich.com/";

  /**
   * @param log
   */
  public CRIChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);

    selector = "div.shufoo_main a[href*='contents/shufoo']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      selector = "p.shopimg>img";
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size && i < 2; i++) {
        if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
          clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 4000);
          closeOtherWindow(driver);
          break;
        }
      }
    }
  }
}
