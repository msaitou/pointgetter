package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;

/**
 * @author saitou
 *
 */
public class GPOAnqueate extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  AnswerAdEnq AdEnq = null;

  /**
   * @param logg
   */
  public GPOAnqueate(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");
    AdEnq = new AnswerAdEnq(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(3000);
    String sele2 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon", selector2 = "li.menu08>a";
    if (isExistEle(driver, sele2)) {
      clickSleepSelector(driver, sele2, 4000);
      if (isExistEle(driver, selector2)) {
        //        String wid = driver.getWindowHandle();
        clickSleepSelector(driver, selector2, 5000);
        // アラートをけして
        checkAndAcceptAlert(driver);
        Utille.sleep(5000);
        changeCloseWindow(driver);
        String okSele = "input[alt='OK']";

        if (isExistEle(driver, okSele)) {
          clickSleepSelector(driver, okSele, 5000); // 遷移
          int skip = 0, beforeSize = 0;
          selector = "div.enquete_box>div>a"; // アンケート一覧の回答するボタン
          String seleNextb2 = "form>input[type='image']";
          String sele1_ = "iframe.question_frame", //
              sele1 = "form>input[type='submit']";

          while (isExistEle(driver, selector)) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              int size = eleList.size(), targetIndex = skip;
              if (beforeSize == size) {
                skip++;
              }
              logg.info("size:" + size + " target:" + targetIndex);
              if (size > targetIndex &&
                  targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
                Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
                actions.perform();
                Utille.sleep(5000);

                String wid = driver.getWindowHandle();
                changeWindow(driver, wid);
                String cUrl = driver.getCurrentUrl();
                logg.info("url[" + cUrl + "]");
                if (cUrl.indexOf("ad/enq/") >= 0
                    && isExistEle(driver, sele1_)) {
                  // $('iframe').contents().find("div>input[type='submit']")
                  boolean isSuccess = true;
                  do {
                    try {
                      if (!AdEnq.answer(driver, sele1, wid)) {
//                        skip++;
                        driver.close();
                        driver.switchTo().window(wid);
                        //                    break;
                      }
                    } catch (StaleElementReferenceException e) {
                      logg.warn("StaleElementReferenceException-----------------");
                      isSuccess = false;
                    }
                  } while (!isSuccess);
                }
                else {
                  break;
                }
              }
            }
            else {
              break;
            }
          }
        }
      }
    }
  }
}
