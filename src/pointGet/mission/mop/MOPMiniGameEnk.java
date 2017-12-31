package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerAdShindan;
import pointGet.mission.parts.AnswerPittango;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerTasuuketu;

public class MOPMiniGameEnk extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";
  WebDriver driver = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;
  AnswerPittango Pittango = null;
  AnswerShindan Shindan = null;
  
  /**
   * @param logg
   */
  public MOPMiniGameEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "Miniゲームパークアンケート");
    Tasuuketu = new AnswerTasuuketu(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
    Pittango = new AnswerPittango(logg);
    Shindan = new AnswerShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "img[alt='miniゲームパーク']";
    String enkLinkSele = "img[alt='miniゲームパーク']", //
        a = "";

    if (isExistEle(driver, enkLinkSele)) {
      clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
      changeCloseWindow(driver);
//      driver.get("http://minigamepark.moppy.jp/square/pittango");
//      driver.get("http://minigamepark.moppy.jp/square/diagnoses");
      driver.get("http://minigamepark.moppy.jp/square/votes");
      for (int k = 0; k < 4; k++) {
//      for (int k = 0; k < 1; k++) {
        if (k == 1) {
          driver.get("http://minigamepark.moppy.jp/square/diagnoses");
        }
        else if (k == 2) {
          driver.get("http://minigamepark.moppy.jp/square/surveys");
        }
        else if (k == 3) {
          driver.get("http://minigamepark.moppy.jp/square/pittango");
        }

        Utille.sleep(6000);
        selector = "div.enqueteBox a[href]>dl";
//        selector = "div.enquete_box a[href]>dl";
        int skip = 1;
        String sele1_ = "iframe.question_frame", //
            sele1 = "form>input[type='submit']", //
            sele3 = "form>input[type='submit']", //
            sele9 = "div>a>dl>dt", //
            overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
            sele6 = "form>input.next_bt", // コラム用
            sele4 = "a.submit-btn",
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
                && isExistEle(driver, sele1_)) {
              // $('iframe').contents().find("div>input[type='submit']")
              if (!AdEnq.answer(driver, sele1, wid)) {
                break;
              }
            }
            else if ((cUrl.indexOf("syouhisya-kinyu.com/agw3") >= 0)
                && isExistEle(driver, sele4)) {
              Shindan.answer(driver, sele4, wid);
              skip++;
            }
            else if ((cUrl.indexOf("diagnosis.media-ad.jp/") >= 0
                || cUrl.indexOf("lion.seikaku-checker.club/") >= 0
                || cUrl.indexOf("enquetter.com/question") >= 0)
                && isExistEle(driver, sele3)) {
              AdShindan.answer(driver, sele3, wid);
              skip++;
            }
            else if ((cUrl.indexOf("http://pittango.net/") >= 0
            //                || cUrl.indexOf("beautynail-design.com") >= 0
            //                || cUrl.indexOf("fashion-cosmelife.com") >= 0
            )
                && isExistEle(driver, sele3)) {
              Pittango.answer(driver, sele3, wid);
            }
            else {
              skip++;
              driver.close();
              driver.switchTo().window(wid);
            }
            driver.navigate().refresh();
            Utille.sleep(5000);
          }
          else {
            break;
          }
        }
        String stampSele = "a[href='/exchange/point']#btn";
        if (isExistEle(driver, stampSele)) {
          clickSleepSelector(driver, stampSele, 4000); // 遷移
        }
      }
    }
  }
}
