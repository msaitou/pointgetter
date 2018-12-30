package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdShindan;

public class GPOMatome extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";

  AnswerAdShindan AdShindan = null;
  /**
   * @author saitou 0時、12時開催
   */
  public GPOMatome(Logger log, Map<String, String> cProps) {
    super(log, cProps, "まとめ");
    AdShindan = new AnswerAdShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.bnr>[alt='たまとめ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      changeCloseWindow(driver);
      String okSele = "input[alt='OK']";

      if (isExistEle(driver, okSele)) {
        clickSleepSelector(driver, okSele, 5000); // 遷移
        int skip = 0, beforeSize = 0;
        selector = "div.article>ul>li>a";
        while (true) {
//          checkOverlay(driver, overlaySele, false);
          if (!isExistEle(driver, selector)) {
            break;
          }
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip;
          if (beforeSize == size) {
            skip++;
          }
          if (size > targetIndex &&
              targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
            String wid = driver.getWindowHandle();
            Utille.sleep(3000);
            Utille.scrolledPage(driver, eleList.get(targetIndex));
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL);
            actions.click(eleList.get(targetIndex));
            actions.perform();
            Utille.sleep(5000);
            //                clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
            changeWindow(driver, wid);
            Utille.sleep(5000);
            String cUrl = driver.getCurrentUrl();
            logg.info("cUrl[" + cUrl + "]");
            if (cUrl.indexOf("http://gameapp.galaxymatome.com") >= 0) {
              String close = "div.feature__button span.button--close", //
                  mSele2 = "a[onclick*='entrance']",//
                  mSele = "a.highlight";
              if (isExistEle(driver, close, false)) {
                clickSleepSelector(driver, close, 2000); // 遷移
              }
              if (isExistEle(driver, mSele)) {
                clickSleepSelector(driver, mSele, 4000); // 遷移
                changeCloseWindow(driver);
                if (isExistEle(driver, close, false)) {
                  clickSleepSelector(driver, close, 2000); // 遷移
                }
                if (isExistEle(driver, mSele2)) {
                  clickSleepSelector(driver, mSele2, 3000); // 遷移
                  skip++;
                  driver.close();
                  driver.switchTo().window(wid);
                }
              }
            }
            else {
              skip++;
              driver.close();
              driver.switchTo().window(wid);
            }
            Utille.refresh(driver, logg);
            Utille.sleep(5000);
          }
        }
      }

    }
  }
}
