package pointGet.mission.gmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saitou
 *
 */
public class GMYChirachi extends GMYBase {
  final String url = "http://dietnavi.com/pc/";

  /**
   * @param log
   */
  public GMYChirachi(Logger log, Map<String, String> cProps) {
    super(log, cProps, "チラシ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
    selector = "a[href='http://dietnavi.com/pc/chirashi/']>span";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      selector = "p.thum>span>img";
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
