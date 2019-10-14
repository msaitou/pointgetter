package pointGet.mission.gen;

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
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHirameki;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerPittango;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerTasuuketu;
import pointGet.mission.parts.AnswerZukan;

public class GENMorikoreEnk extends GENBase {
  final String url = "http://www.gendama.jp/";
  boolean skipCapFlag = false;
  WebDriver driver = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;
  AnswerColum Colum = null;
  AnswerPittango Pittango = null;
  AnswerShindan Shindan = null;
  /* アンケートクラス　写真 */
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerManga Manga = null;
  AnswerHirameki Hirameki = null;

  /**
   * @param logg
   */
  public GENMorikoreEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "モリコレ");
    Tasuuketu = new AnswerTasuuketu(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
    Colum = new AnswerColum(logg);
    Pittango = new AnswerPittango(logg);
    Shindan = new AnswerShindan(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Cooking = new AnswerCooking(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Manga = new AnswerManga(logg);
    Hirameki = new AnswerHirameki(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "section#ftrlink li>a[href*='/cl/?id=134610&u=6167192']";
    String enkLinkSele = "a>img[alt='モリモリ診断']", //
        a = "";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 10000); // 遷移
      changeCloseWindow(driver);

      if (isExistEle(driver, enkLinkSele)) {
        clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
        changeCloseWindow(driver);
        Utille.url(driver, "http://gendama.agri-hatake.fun/square/columns", logg);
        for (int k = 0; k < 5; k++) {
          if (k == 1) {
            Utille.url(driver, "http://gendama.agri-hatake.fun/square/pittango", logg);
          }
          else if (k == 2) {
            Utille.url(driver, "http://gendama.agri-hatake.fun/square/surveys", logg);
          }
//          else if (k == 5) {
//            Utille.url(driver, "http://gendama.agri-hatake.fun/square/pittango", logg);
//          }
          else if (k == 3) {
            Utille.url(driver, "http://gendama.agri-hatake.fun/square/votes", logg);
          }
          else if (k == 4) {
            Utille.url(driver, "http://gendama.agri-hatake.fun/square/diagnoses", logg);
          }

          Utille.sleep(3000);
          selector = "div.enquete_box a[href]>dl";
          int skip = 1, beforeSize = 0;
          String sele1_ = "iframe.question_frame", //
              sele1 = "form>input[type='submit']", //
              sele3 = "form>input[type='submit']", //
              sele9 = "a.start__button", overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
              sele6 = "form>input.next_bt", // コラム用
              sele10 = "form>input[type='image']", // 回答する 漫画用
              sele4 = "a.submit-btn", b = "";
          while (true) {
            checkOverlay(driver, overlaySele, false);
            if (!isExistEle(driver, selector)) {
              break;
            }
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size = eleList.size(), targetIndex = size - skip;
            if (beforeSize == size) {
              skip++;
            }
            if (size > targetIndex &&
                targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
              String wid = driver.getWindowHandle();
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
              changeWindow(driver, wid);
              Utille.sleep(3000);
              String cUrl = driver.getCurrentUrl();
              logg.info("cUrl[" + cUrl + "]");
              String isShindanText = "", isShindanSele = "h3.start__title";
              if (isExistEle(driver, isShindanSele)) {
                isShindanText = driver.findElement(By.cssSelector(isShindanSele)).getText();
              }

              if (cUrl.indexOf("http://gameapp.galaxymatome.com") >= 0) {
                String close = "div.feature__button span.button--close", //
                    mSele2 = "a[onclick*='entrance']", //
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
                  Utille.sleep(3000);
                  if (isExistEle(driver, mSele2)) {
                    clickSleepSelector(driver, mSele2, 3000); // 遷移
                  }
                }
                skip++;
                driver.close();
                driver.switchTo().window(wid);
              }
              else if (isExistEle(driver, sele9)) {
                Tasuuketu.answer(driver, sele9, wid);
              }
              else if ("この診断について".equals(isShindanText)
                  && isExistEle(driver, sele3)) {
                AdShindan.answer(driver, sele3, wid);
              }
              else if (!skipCapFlag && (cUrl.indexOf("ad/enq/") >= 0
                  || cUrl.indexOf("beautypress.tokyo") >= 0
                  || cUrl.indexOf("good-question.jp") >= 0
                  || cUrl.indexOf("credit-card.link") >= 0)

                  && true) {
//                if (isExistEle(driver, sele1_)) {
//                }
                // $('iframe').contents().find("div>input[type='submit']")
                if (!AdEnq.answer(driver, sele1, wid)) {
                  break;
                }
              }
              else if (!skipCapFlag &&
                  (cUrl.indexOf("syouhisya-kinyu.com/agw3") >= 0)
                  && isExistEle(driver, sele4)) {
                Shindan.answer(driver, sele4, wid);
              }
              else if ((cUrl.indexOf("cosmelife.com/animal") >= 0
                  //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                  || cUrl.indexOf("/animal/") >= 0)
                  && isExistEle(driver, sele6)) {
                Zukan.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("cosmelife.com/observation") >= 0
                  || cUrl.indexOf("/observation/") >= 0
              //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
              )
                  && isExistEle(driver, sele6)) {
                Kansatu.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("cosmelife.com/map") >= 0
                  || cUrl.indexOf("/map/") >= 0
              //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
              )
                  && isExistEle(driver, sele6)) {
                Hyakkey.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("cosmelife.com/cooking") >= 0
                  || cUrl.indexOf("/cooking/") >= 0
              //                || cUrl.indexOf("eyelashes-fashion.com") >= 0
              )
                  && isExistEle(driver, sele6)) {
                Cooking.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("photo-enquete") >= 0
                  || cUrl.indexOf("/photo/") >= 0
                  || cUrl.indexOf("cosmelife.com/photo") >= 0
                  || cUrl.indexOf("eyelashes-fashion.com") >= 0
                  || cUrl.indexOf("natural-vegetables.com") >= 0
                  || cUrl.indexOf("cosmeticsstyle.com") >= 0)
                  && isExistEle(driver, sele6)) {
                PhotoEnk.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("XXXXXXXXXXXXXXX") >= 0
                  || cUrl.indexOf("/hirameki/") >= 0) && isExistEle(driver, sele6)) {
                Hirameki.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("column-enquete") >= 0
                  || cUrl.indexOf("/column/") >= 0
                  || cUrl.indexOf("beautynail-design.com") >= 0
                  || cUrl.indexOf("style-cutehair.com") >= 0
                  || cUrl.indexOf("eyelashes-fashion.com") >= 0
                  || cUrl.indexOf("fashion-cosmelife.com") >= 0)
                  && isExistEle(driver, sele6)) {
                Colum.answer(driver, sele6, wid);
              }
              else if ((cUrl.indexOf("http://pittango.net/") >= 0
                  || cUrl.indexOf("pittango.jp") >= 0
              //                || cUrl.indexOf("beautynail-design.com") >= 0
              //                || cUrl.indexOf("fashion-cosmelife.com") >= 0
              )
                  && isExistEle(driver, sele3)) {
                Pittango.answer(driver, sele3, wid);
              }
              // 漫画
              else if (isExistEle(driver, sele10)) {
                Manga.answer(driver, sele10, wid);
              }
              else {
                driver.close();
                driver.switchTo().window(wid);
              }
              beforeSize = size;
              Utille.refresh(driver, logg);
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
}
