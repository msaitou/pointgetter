package pointGet.mission.dmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saitou
 *
 */
public class DMYChirachi extends DMYBase {
  final String url = "http://mypage.ameba.jp/";

  /**
   * @param log
   */
  public DMYChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "div.c-media_body>p[ng-if='item.title']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
      changeCloseWindow(driver);
      selector = "li.chirashi>div>a.list-btn";
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size && i < 2; i++) {
        if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
          clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 2000);
          driver.navigate().back();
        }
      }
    }
  }
}
