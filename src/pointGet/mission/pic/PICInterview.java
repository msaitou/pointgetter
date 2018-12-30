package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerAdShindan;
import pointGet.mission.parts.AnswerTasuuketu;

public class PICInterview extends PICBase {
  final String url = "https://pointi.jp/contents/research/";
  WebDriver driver = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;

  /**
   * @param logg
   */
  public PICInterview(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "インタビュー");
    Tasuuketu = new AnswerTasuuketu(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "img[src='img/interview.png']";
    String enkLinkSele = "a>dl>dd>strong", //
    a = "";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000); // 遷移
      changeCloseWindow(driver);
      if (isExistEle(driver, enkLinkSele)) {
        //        clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
        String[] seleList = { 
            "a>dl>dd>strong", 
            "div#article>ul>li>a" };
        int skip = 0; // tasuuketuyatte
        String sele1_ = "iframe.question_frame", //
        sele1 = "form>input[type='submit']", //
        sele3 = "form>input[type='submit']", //
        sele9 = "a.start__button", overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
        b = "";
        for (int i = 0; i < seleList.length; i++, skip = 0) {
          selector = seleList[i];
          while (true) {
            //          checkOverlay(driver, overlaySele, false);
            if (!isExistEle(driver, selector)) {
              break;
            }
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size = eleList.size(), targetIndex = skip;
            if (size > targetIndex && isExistEle(eleList, targetIndex)) {
              String wid = driver.getWindowHandle();
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
              changeWindow(driver, wid);
              String cUrl = driver.getCurrentUrl();
              logg.info("url[" + cUrl + "]");
              if (cUrl.indexOf("http://gameapp.galaxymatome.com") >= 0) {
                String close = "div.feature.bomb span.button--close", //
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
              else if (isExistEle(driver, sele9)) {
                Tasuuketu.answer(driver, sele9, wid);
                skip++;
              }
              else if (cUrl.indexOf("ad/enq/") >= 0
                  && isExistEle(driver, sele1_)) {
                // $('iframe').contents().find("div>input[type='submit']")
                boolean isSuccess = true;
                do {
                  try {
                    if (!AdEnq.answer(driver, sele1, wid)) {
                      skip++;
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
              else if ((cUrl.indexOf("diagnosis.media-ad.jp/") >= 0
                  || cUrl.indexOf("salamander.site/dgss/question/") >= 0
                  || cUrl.indexOf("sheep.seikaku-checker.club") >= 0
                  || cUrl.indexOf("diagnosis.media-ad.jp/question") >= 0)
                  && isExistEle(driver, sele3)) {
                AdShindan.answer(driver, sele3, wid);
                //                skip++;
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
          String stampSele = "div#pointFarmBox>a#btn";
          if (isExistEle(driver, stampSele)) {
            clickSleepSelector(driver, stampSele, 4000); // 遷移
          }

        }
      }
    }
  }
}
