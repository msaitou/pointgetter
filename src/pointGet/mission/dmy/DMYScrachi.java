package pointGet.mission.dmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class DMYScrachi extends DMYBase {
  final String url = "https://d-money.jp/mall";

  /**
   * @param log
   */
  public DMYScrachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "スクラッチ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "i.c-dmoney_icon_21_reward";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "img[src*='scratch.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000);
      selector = "ul.item-box>li.item>img";
      if (isExistEle(driver, selector)) {
        for (int i = 0; i < 3; i++) {
          if (isExistEle(driver, selector)) {
            int size = getSelectorSize(driver, selector);
            int ran = Utille.getIntRand(size);
            if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
              clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
            }
          }
        }
        Utille.sleep(3000);
      }
    }
  }
}
