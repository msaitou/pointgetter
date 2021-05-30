package pointGet.mission.sug.old;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHirameki;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerIjin;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerMix;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerZukan;
import pointGet.mission.sug.SUGBase;

public class SUGAnkPark extends SUGBase {
  final String url = "https://www.sugutama.jp/survey";
  boolean skipCapFlag = false;
  WebDriver driver = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;
  AnswerManga Manga = null;
  AnswerHirameki Hirameki = null;
  AnswerMix Mix = null;
  AnswerIjin Ijin = null;

  /**
   * @param logg
   */
  public SUGAnkPark(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "SUGアンケートパーク");
    Cooking = new AnswerCooking(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Colum = new AnswerColum(logg);
    Manga = new AnswerManga(logg);
    Hirameki = new AnswerHirameki(logg);
    Mix = new AnswerMix(logg);
    Ijin = new AnswerIjin(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String enkLinkSele = "img[src='//static.sugutama.jp/ssp_site/e1044c42364c3279c63eb081d5c4c8ee.png']", //
    a = "";

    if (isExistEle(driver, enkLinkSele)) {
      clickSleepSelector(driver, enkLinkSele, 4000); // 遷移
      changeCloseWindow(driver);

      Utille.sleep(6000);
      selector = "div.enquete-list tr>td.status>a[href*='enquete']";
      int skip = 0, beforeSize = 0;
      String sele1_ = "iframe.question_frame", //
      sele1 = "form>input[type='submit']", //
      sele3 = "form>input[type='submit']", //
      sele9 = "div>a.start__button", //
      overlaySele = "div#meerkat-wrap div#overlay img.ad_close", //
      sele6 = "form>input.next_bt", // コラム用
      sele4 = "a.submit-btn", sele10 = "form>input[type='image']", // 回答する 漫画用
          sele11 = "input.enquete_nextbt.next_bt",
      b = "";
      while (true) {
        checkOverlay(driver, overlaySele, false);
        if (!isExistEle(driver, selector)) {
          break;
        }
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size(), 
//            targetIndex = size - skip;
            targetIndex = skip;
        if (beforeSize == size) {
          skip++;
          targetIndex = skip;
        }
        if (size > targetIndex &&
            targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
          String wid = driver.getWindowHandle();
//          Utille.mouseOverByScript(driver, seleMouseOver, logg);
          Utille.scrolledPage(driver, eleList.get(targetIndex));
          Utille.sleep(2000);
          Actions actions = new Actions(driver);
          actions.keyDown(Keys.CONTROL);
          actions.click(eleList.get(targetIndex));
          actions.perform();
          Utille.sleep(5000);

          changeWindow(driver, wid);
//          clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
//          changeWindow(driver, wid);
          String cUrl = driver.getCurrentUrl();
          logg.info("cUrl[" + cUrl + "]");
          if (isExistEle(driver, sele9)) {
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
              || cUrl.indexOf("/map/") >= 0)
              && isExistEle(driver, sele6)) {
            Hyakkey.answer(driver, sele6, wid);
          }
          else if ((cUrl.indexOf("cosmelife.com/cooking") >= 0
              || cUrl.indexOf("/cooking/") >= 0)
              && isExistEle(driver, sele6)) {
            Cooking.answer(driver, sele6, wid);
          }
          else if ((cUrl.indexOf("XXXXXXXXXXXXXXX") >= 0
              || cUrl.indexOf("/hirameki/") >= 0
              ) && isExistEle(driver, sele6)) {
            Hirameki.answer(driver, sele6, wid);
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
              || cUrl.indexOf("/column/") >= 0
              || cUrl.indexOf("beautynail-design.com") >= 0
              || cUrl.indexOf("style-cutehair.com") >= 0
              || cUrl.indexOf("eyelashes-fashion.com") >= 0
              || cUrl.indexOf("fashion-cosmelife.com") >= 0)
              && isExistEle(driver, sele6)) {
            Colum.answer(driver, sele6, wid);
          }
          // 漫画
          else if (
              (cUrl.indexOf("/manga/") >= 0)
              && isExistEle(driver, sele10)) {
            Manga.answer(driver, sele10, wid);
          }
          // 偉人
          else if ((cUrl.indexOf("/ijin/") >= 0)
              && isExistEle(driver, sele6)) {
            Ijin.answer(driver, sele6, wid);
          }
          // MIX
          else if (
              (cUrl.indexOf("/mix/") >= 0)
              && isExistEle(driver, sele11)) {
            Mix.answer(driver, sele11, wid);
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
