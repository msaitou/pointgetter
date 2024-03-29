package pointGet.mission.mop.old;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.mop.MOPBase;

/**
 * @author saitou
 *
 */
public class MOPChirachi extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";

  /**
   * @param log
   */
  public MOPChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.icon>img[alt='モッピーチラシ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000); // 遷移
      changeCloseWindow(driver);
      selector = "figure.flyer__item__thumbnail>img";
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size && i < 2; i++) {
        if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
          clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 2000);
          closeOtherWindow(driver);
//          break;
        }
      }
    }
  }
}
