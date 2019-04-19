package pointGet.mission.dmy;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerAdShindan;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerTasuuketu;

public class DMYPointResearch extends DMYBase {
  final String url = "https://d-money.jp/mall";
  WebDriver driver = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public DMYPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントアンケート");
    Tasuuketu = new AnswerTasuuketu(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    selector = "i.c-dmoney_icon_21_reward";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "img[src*='questionnaire.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000);
      changeCloseWindow(driver);
    }
    Utille.sleep(4000);
    //    selector = "li.js_labelEnquete";
    //    String enkLinkSele = "div#js-rwenquete>a>img", //
    //    a = "";
    //    if (isExistEle(driver, selector)) {
    //      clickSleepSelector(driver, selector, 3000); // 遷移
    //      if (isExistEle(driver, enkLinkSele)) {
    //        clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
    //        Utille.sleep(3000);

    selector = "div.enquete_box a[href]>dl";
    int skip = 1;
    String sele1_ = "iframe.question_frame", //
    sele1 = "form>input[type='submit']", //
    sele3 = "form>input[type='submit']", //
    sele9 = "a.start__button", overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
    sele6 = "form>input.next_bt", // コラム用
    b = "";
    while (true) {
      checkOverlay(driver, overlaySele, false);
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (size > targetIndex &&
          targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
        String wid = driver.getWindowHandle();
        Utille.scrolledPage(driver, eleList.get(targetIndex));
        clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
        changeWindow(driver, wid);
        Utille.sleep(3000);
        String cUrl = driver.getCurrentUrl();
        logg.info("cUrl[" + cUrl + "]");
        if (isExistEle(driver, sele9)) {
          Tasuuketu.answer(driver, sele9, wid);
          skip++;
        }
        else if (cUrl.indexOf("ad/enq/") >= 0
//            && isExistEle(driver, sele1_)
            ) {
          // $('iframe').contents().find("div>input[type='submit']")
          if (!AdEnq.answer(driver, sele1, wid)) {
            skip++;
            driver.close();
            driver.switchTo().window(wid);
//            break;
          }
        }
        else if ((cUrl.indexOf("/dgss/question/") >= 0
            || cUrl.indexOf("salamander.site/dgss/question/") >= 0
            ) && isExistEle(driver, sele3)) {
          AdShindan.answer(driver, sele3, wid);
          skip++;
        }
        else if ((cUrl.indexOf("column-enquete") >= 0
            || cUrl.indexOf("beautynail-design.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("fashion-cosmelife.com") >= 0
            || cUrl.indexOf("style-cutehair.com") >= 0
            )
            && isExistEle(driver, sele6)) {
          Colum.answer(driver, sele6, wid);
        }
        else {
          skip++;
          driver.close();
          driver.switchTo().window(wid);
        }
        Utille.refresh(driver, logg);
        Utille.sleep(5000);
      }
      else {
        break;
      }
    }
    //      }
  }
}
