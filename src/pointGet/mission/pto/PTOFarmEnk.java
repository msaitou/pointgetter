package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdEnq;
import pointGet.mission.parts.AnswerAdShindan;
import pointGet.mission.parts.AnswerAdsurvey;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerGameParkEnk;
import pointGet.mission.parts.AnswerHirameki;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerPittango;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerTasuuketu;
import pointGet.mission.parts.AnswerZukan;

public class PTOFarmEnk extends PTOBase {
  final String url = "http://www.pointtown.com/ptu/mypage/top.do";
  boolean skipCapFlag = false;
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerGameParkEnk GameParkEnk = null;
  AnswerAdsurvey Adsurvey = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerAdEnq AdEnq = null;
  AnswerAdShindan AdShindan = null;
  AnswerPittango Pittango = null;
  AnswerShindan Shindan = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;
  AnswerHirameki Hirameki=null;

  /**
   * @param logg
   */
  public PTOFarmEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイント農場");
    Tasuuketu = new AnswerTasuuketu(logg);
    GameParkEnk = new AnswerGameParkEnk(logg);
    Adsurvey = new AnswerAdsurvey(logg);
    AdEnq = new AnswerAdEnq(logg);
    AdShindan = new AnswerAdShindan(logg);
    Pittango = new AnswerPittango(logg);
    Shindan = new AnswerShindan(logg);
    Cooking = new AnswerCooking(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Colum = new AnswerColum(logg);
    Hirameki=new AnswerHirameki(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String enkLinkSele = "a[onclick*='ポイント農場']", //
    a = "";

    if (isExistEle(driver, enkLinkSele)) {
      clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
      //      changeCloseWindow(driver);
      Utille.url(driver, "http://p-town.agri-hatake.fun/square/pittango", logg);
      for (int k = 0; k < 3; k++) {
        if (k == 1) {
          Utille.url(driver, "http://p-town.agri-hatake.fun/square/surveys", logg);
        }
        else if (k == 2) {
          Utille.url(driver, "http://p-town.agri-hatake.fun/square/diagnoses", logg);
        }

        Utille.sleep(3000);
        selector = "div.enqueteBox a[href]>dl";
        int skip = 1, beforeSize = 0;
        String sele1_ = "iframe.question_frame", //
        sele1 = "form>input[type='submit']", //
        sele3 = "form>input[type='submit']", //
        sele9 = "a.start__button", overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
        sele6 = "form>input.next_bt", // コラム用
        sele4 = "a.submit-btn",
        b = "";
        while (true) {
          checkOverlay(driver, overlaySele, false);
          if (!isExistEle(driver, selector)) {
            break;
          }
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size();
          if (beforeSize == size) {
            skip++;
          }
          int targetIndex = size - skip;
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
            }
            else if (!skipCapFlag &&cUrl.indexOf("ad/enq/") >= 0
//                && isExistEle(driver, sele1_)
                ) {
              // $('iframe').contents().find("div>input[type='submit']")
              if (!AdEnq.answer(driver, sele1, wid)) {
                break;
              }
            }
            else if (!skipCapFlag && (cUrl.indexOf("syouhisya-kinyu.com/agw3") >= 0)
                && isExistEle(driver, sele4)) {
              Shindan.answer(driver, sele4, wid);
            }
            else if ((cUrl.indexOf("/dgss/question/") >= 0
                || cUrl.indexOf("lion.seikaku-checker.club/") >= 0
                    || cUrl.indexOf("salamander.site/dgss/question") >= 0
                || cUrl.indexOf("enquetter.com/question") >= 0)
                && isExistEle(driver, sele3)) {
              AdShindan.answer(driver, sele3, wid);
            }
            else if ((cUrl.indexOf("http://pittango.net/") >= 0
                || cUrl.indexOf("pittango.jp") >= 0
                //                || cUrl.indexOf("beautynail-design.com") >= 0
                //                || cUrl.indexOf("fashion-cosmelife.com") >= 0
                )
                && isExistEle(driver, sele3)) {
              Pittango.answer(driver, sele3, wid);
            }
            else if ((cUrl.indexOf("XXXXXXXXXXXXXXX") >= 0
                || cUrl.indexOf("/hirameki/") >= 0
                )&& isExistEle(driver, sele6)) {
              Hirameki.answer(driver, sele6, wid);
            }
            else if ((cUrl.indexOf("cosmelife.com/animal") >= 0
                            || cUrl.indexOf("/animal/") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
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
            else if ((cUrl.indexOf("column-enquete") >= 0
                || cUrl.indexOf("column.") >= 0
                || cUrl.indexOf("/column/") >= 0
                || cUrl.indexOf("beautynail-design.com") >= 0
                || cUrl.indexOf("style-cutehair.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
                || cUrl.indexOf("fashion-cosmelife.com") >= 0)
                && isExistEle(driver, sele6)) {
              Colum.answer(driver, sele6, wid);
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
      String stampSele = "a[href='/exchange/point']#btn";
      if (isExistEle(driver, stampSele)) {
        clickSleepSelector(driver, stampSele, 4000); // 遷移
      }
    }

  }
}
