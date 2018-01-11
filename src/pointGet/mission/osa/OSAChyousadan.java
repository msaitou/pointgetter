package pointGet.mission.osa;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;

public class OSAChyousadan extends OSABase {
  final String url = "http://osaifu.com/coinland/";
  AnswerAdEnq AdEnq = null;

  /**
   * @param logg
   */
  public OSAChyousadan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "調査団");
    AdEnq = new AnswerAdEnq(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    selector = "li>a>img[alt='お財布Watch']";
    driver.get(url);
    String seleFirst = "a>img[alt='reado']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(3000);
      if (isExistEle(driver, seleFirst)) {
        clickSleepSelector(driver, seleFirst, 3000); // 調査一覧へ
        changeCloseWindow(driver);
        Utille.sleep(3000);
        int skip = 1;
        String sele1_ = "iframe.question_frame", //
        sele1 = "form>input[type='submit']", //
        b = "";
        selector = "div.enquete_box>a";
        int cn = 0;
        while (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = size - skip;
          if (size > targetIndex && isExistEle(eleList, targetIndex)) {
            String wid = driver.getWindowHandle();
            Utille.scrolledPage(driver, eleList.get(targetIndex));
//            clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex), 6000); // アンケートスタートページ
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
//            // 回数を制限する
//            if (cn++ > 2) {
//              break;
//            }
          }
          else {
            break;
          }
        }
      }
    }
  }
}
