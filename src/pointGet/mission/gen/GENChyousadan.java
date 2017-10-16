package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;

public class GENChyousadan extends GENBase {
  final String url = "http://www.gendama.jp/";
  AnswerAdEnq AdEnq = null;

  /**
   * @param logg
   */
  public GENChyousadan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "調査団");
    AdEnq = new AnswerAdEnq(logg);
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    // div#dropmenu01
    driver.get(url);
    selector = "div#dropmenu01";
    String seleFirst = "dl>dt>img[src*='kumakumachosa']";
    if (isExistEle(driver, selector, false)) {
      int size0 = getSelectorSize(driver, selector);
      for (int i = 0; i < size0; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "a[onclick*='CMくじ']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
          driver.get(cmPageUrl); // CMpage
          Utille.sleep(3000);
          if (!isExistEle(driver, seleFirst)) {
            break;
          }
          clickSleepSelector(driver, seleFirst, 3000); // 遷移 全体へ
          changeCloseWindow(driver);
          Utille.sleep(10000);
          int skip = 1;
          String sele1_ = "iframe.question_frame", //
          sele1 = "form>input[type='submit']", //
          b = "";
          selector = "div.enquete_box a dd.title>strong";
          int cn = 0;
          while (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size = eleList.size(), targetIndex = size - skip;
            if (size > targetIndex && isExistEle(eleList, targetIndex)) {
              String wid = driver.getWindowHandle();
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
              changeWindow(driver, wid);
              String cUrl = driver.getCurrentUrl();
              logg.info("url[" + cUrl + "]");
              if (cUrl.indexOf("ad/enq/") >= 0
                  && isExistEle(driver, sele1_)) {
                // $('iframe').contents().find("div>input[type='submit']")
                AdEnq.answer(driver, sele1, wid);
              }
              else {
                skip++;
                driver.close();
                driver.switchTo().window(wid);
              }
              driver.navigate().refresh();
              Utille.sleep(5000);
              // 回数を制限する
              if (cn++ > 2) {
                break;
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
