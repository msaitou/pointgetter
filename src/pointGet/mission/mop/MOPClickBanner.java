package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class MOPClickBanner extends MOPBase {
  final String url = "https://pc.moppy.jp/";

  /**
   * @param log
   */
  public MOPClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "クリックで貯める");
  }

  @Override
  public void privateMission(WebDriver driver) {
    // TOPバナー
    Utille.url(driver, url, logg);
    selector = "div#site-jack a img";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000);
    }

    Utille.url(driver, "https://pc.moppy.jp/gamecontents/", logg);
    String[] seleList = { "div[data-block-title='クリックで貯める']>ul>li>a" };
    String cushionSele = "div.modal__content>div#modal_detail div>a[rel*='noopener']", //
    cushionNoSele = "div.modal__content[style*='display: none']>div#modal_detail";
    for (String sele : seleList) {
      selector = sele;
      if (isExistEle(driver, selector)) {
        int size = getSelectorSize(driver, selector);
        for (int i = 0; i < size; i++) {
          if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 2000);
            if (!isExistEle(driver, cushionNoSele, false)
                && driver.findElement(By.cssSelector(cushionSele)).isDisplayed()
                && isExistEle(driver, cushionSele)) {
              clickSleepSelector(driver, cushionSele, 2000); // 遷移
            }
            closeOtherWindow(driver);
            Utille.refresh(driver, logg);

          }
        }
      }
    }
  }
}
