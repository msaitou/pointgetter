package pointGet.mission.dmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class DMYScrachi extends DMYBase {
  final String url = "http://d-moneymall.jp/daily.html?content=pricesearch&fromid=money_header&internalid=gnavi";

  /**
   * @param log
   */
  public DMYScrachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "スクラッチ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    // なぜか２回目のログイン
    selector = "li.not-login>a.btn-login";
    if (isExistEle(driver, selector, false)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "li.js_labelScratch";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
      selector = "a.scratch-start-btn";
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 5000);

        selector = "ul.scratch-list>li[class='scratch-listItem js-scratchItem";
        if (isExistEle(driver, selector)) {
          for (int i = 0; i < 3; i++) {
            if (isExistEle(driver, selector)) {
              int size = getSelectorSize(driver, selector);
              int ran = Utille.getIntRand(size);
              if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 3000);
              }
            }
          }
          Utille.sleep(3000);
        }
      }
    }
  }
}
