package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerAdShindan;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerTasuuketu;

public class GENMorikoreEnk extends GENBase {
  final String url = "http://www.gendama.jp/";
  WebDriver driver = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public GENMorikoreEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "モリコレ");
    Tasuuketu = new AnswerTasuuketu(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "section#ftrlink li>a[href*='/cl/?id=134610&u=6167192']";
    String enkLinkSele = "div.playlist__clear>div>a[href='/ad/enq/list']", //    
    a = "";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000); // 遷移
      changeCloseWindow(driver);
      
      if (isExistEle(driver, enkLinkSele)) {
        clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
        changeCloseWindow(driver);
        Utille.sleep(3000);
        selector = "div.enqueteBox a[href]>dl";
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
          int size = eleList.size(), targetIndex = skip;
          if (size > targetIndex && isExistEle(eleList, targetIndex)) {
            String wid = driver.getWindowHandle();
            Utille.scrolledPage(driver, eleList.get(targetIndex));
            clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
            changeWindow(driver, wid);
            String cUrl = driver.getCurrentUrl();
            logg.info("cUrl["+cUrl+"]");
            if (isExistEle(driver, sele9)) {
              Tasuuketu.answer(driver, sele9, wid);
              skip++;
            }
            else if (cUrl.indexOf("ad/enq/") >= 0
                && isExistEle(driver, sele1_)) {
              // $('iframe').contents().find("div>input[type='submit']")
              AdEnq.answer(driver, sele1, wid);
            }
            else if (cUrl.indexOf("diagnosis.media-ad.jp/") >= 0
                && isExistEle(driver, sele3)) {
              AdShindan.answer(driver, sele3, wid);
              skip++;
            }
            else if (cUrl.indexOf("column-enquete") >= 0
                && isExistEle(driver, sele6)) {
              Colum.answer(driver, sele6, wid);
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
      }
    }
  }
}
