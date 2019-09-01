package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdShindan;

public class GPODokiShindan extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";

  AnswerAdShindan AdShindan = null;
  /**
   * @author saitou 0時、12時開催
   */
  public GPODokiShindan(Logger log, Map<String, String> cProps) {
    super(log, cProps, "どきどき診断");
    AdShindan = new AnswerAdShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.bnr>[alt='ドキドキ無料診断♪']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      changeCloseWindow(driver);
      String okSele = "input[alt='OK']";

      if (isExistEle(driver, okSele)) {
        clickSleepSelector(driver, okSele, 5000); // 遷移

        int skip = 0, beforeSize = 0;

        String sele1_ = "iframe.question_frame", //
        sele1 = "form>input[type='submit']", //
        sele3 = "form>input[type='submit']", //
        sele9 = "a.start__button", //
        overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
        sele6 = "form>input.next_bt", // コラム用
        sele10 = "form>input[type='image']", // 回答する 漫画用
        sele4 = "a.submit-btn", b = "";

        selector = "div.diagnosis_box>div>a";
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
            // 選択
            clickSleepSelector(driver, eleList.get(targetIndex), 5000);

//            Utille.scrolledPage(driver, eleList.get(targetIndex));
//            Actions actions = new Actions(driver);
//            actions.keyDown(Keys.CONTROL);
//            actions.click(eleList.get(targetIndex));
//            actions.perform();
            Utille.sleep(5000);
            //                clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
            changeWindow(driver, wid);
            Utille.sleep(5000);
            String cUrl = driver.getCurrentUrl();
            logg.info("cUrl[" + cUrl + "]");
            if ((cUrl.indexOf("/dgss/question/") >= 0
                || cUrl.indexOf("sheep.seikaku-checker.club") >= 0
                || cUrl.indexOf("salamander.site/dgss/question") >= 0
                || cUrl.indexOf("/question/") >= 0)
                && isExistEle(driver, sele3)) {
              AdShindan.answer(driver, sele3, wid);
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
