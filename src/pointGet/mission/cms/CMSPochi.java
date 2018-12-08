package pointGet.mission.cms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerPochi;

/**
 * @author saitou
 *
 */
public class CMSPochi extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/enq/";
   
  AnswerPochi Pochi = null;

  /**
   * @param logg
   */
  public CMSPochi(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ぽちっと");
    Pochi = new AnswerPochi(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String sele2 = "img[alt='ポチッと調査隊']", selector2 = "img[src='img/q_02.png']";
    if (isExistEle(driver, sele2)) {
      clickSleepSelector(driver, sele2, 5000);
      checkAndAcceptAlert(driver);
      Utille.sleep(5000);
      changeCloseWindow(driver);

      int skip = 0, beforeSize = -1;
      selector = "dl#question>dd>a>p"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[type='image']";
      String sele6 = "div.btn_send>a>p", // 
      b;

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
//            Actions actions = new Actions(driver);
//            actions.keyDown(Keys.CONTROL);
//            actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
//            actions.perform();
//            Utille.sleep(10000);
            clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ

            String wid = driver.getWindowHandle();
//            changeWindow(driver, wid);
//            Utille.sleep(1000);
            String cUrl = driver.getCurrentUrl();
            logg.info("url[" + cUrl + "]");
            if (
//            (cUrl.indexOf("cosmelife.com/animal") >= 0
//                || cUrl.indexOf("/animal/") >= 0
//                )
//                &&
            isExistEle(driver, sele6)) {
              Pochi.answer(driver, sele6, wid);
            }
            else {
              break;
            }

            //                beforeSize = size;
//            Utille.refresh(driver, logg);
//            Utille.sleep(5000);
          }
        }
        else {
          break;
        }
      }
    }
  }
}
