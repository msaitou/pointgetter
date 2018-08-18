package pointGet.mission.dmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class DMYChirachi extends DMYBase {
  final String url = "https://d-money.jp/mall";

  /**
   * @param log
   */
  public DMYChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "i.c-dmoney_icon_21_reward";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "img[src*='shufoo.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);

      String postInputSele = "form.zipform1_0--form>input[type='text']";
      if (isExistEle(driver, postInputSele)) {
        WebElement ele = driver.findElement(By.cssSelector(postInputSele));
        ele.clear();
        ele.sendKeys("1640002");
        String registSele = "form.zipform1_0--form>input[type='submit']";
        if (isExistEle(driver, registSele)) {
          clickSleepSelector(driver, registSele, 3000);
        }
      }
        
      
      selector = "a.flyer1_0--link";
      if (isExistEle(driver, selector)) {
        int size = getSelectorSize(driver, selector);
        for (int i = 0; i < size && i < 2; i++) {
          if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 4000);
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
