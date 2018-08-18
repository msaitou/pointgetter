package pointGet.mission.rin;

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
public class RINClickBanner extends RINBase {
  final String url = "https://www.rakuten-card.co.jp/e-navi/members/point/click-point/index.xhtml?l-id=enavi_top_info-personal_click-point";

  /**
   * @param log
   */
  public RINClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "クリックバナー(楽天)");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(4000);
    String selectorOverLay = "div.js-coreppo-notice-close";
    String selectorOverLayNone = "div#js-coreppo-notice[style*='display: none']>div.js-coreppo-notice-close";
    if (!isExistEle(driver, selectorOverLayNone, false)
        && isExistEle(driver, selectorOverLay)) {
      clickSleepSelector(driver, selectorOverLay, 3000); // 遷移
    }
    
    String[] selectorList = { "div.topArea.clearfix", "span#middleArea>ul>li" };
    for (int j = 0; j < selectorList.length; j++) {
      if (isExistEle(driver, selectorList[j])) {
        String selector2 = "[alt='Check']";
        int size = driver.findElements(By.cssSelector(selectorList[j])).size();
        for (int i = 0; i < size; i++) {
          // document.querySelectorAll('[alt="Check"]')[1].parentNode.parentNode.parentNode.querySelectorAll('div.bnrBox
          // img')[0];
          WebElement e = driver.findElements(By.cssSelector(selectorList[j])).get(i);
          if (isExistEle(e, selector2)) {
            e.findElement(By.cssSelector("a>img")).click();
            Utille.sleep(2000);
            closeOtherWindow(driver);
//            driver.navigate().refresh();
          }
        }
      }
    }
  }
}
