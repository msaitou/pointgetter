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
  final String url = "http://d-moneymall.jp/daily.html?content=pricesearch&fromid=money_header&internalid=gnavi";

  /**
   * @param log
   */
  public DMYChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "li.not-login>a.btn-login";
    if (isExistEle(driver, selector, false)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "li.js_labelShufoo";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
      selector = "div#js-shufoo a.js-goRequest";
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 5000);
        selector = "li.chirashi>div>a.list-btn";
        if (isExistEle(driver, selector)) {
          int size = getSelectorSize(driver, selector);
          for (int i = 0; i < size && i < 2; i++) {
            if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
              clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 4000);
              break; // 1発で終了　戻れない
//              driver.navigate().back();
//              // アラートをけして
//              checkAndAcceptAlert(driver);
            }
          }
        }
      }
    }
  }
}
