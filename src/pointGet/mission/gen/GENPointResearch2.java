package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqNstk;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerHiroba;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerKotsuta;
import pointGet.mission.parts.AnswerMinnanosur;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerPointResearch;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerShopping;
import pointGet.mission.parts.AnswerSurveyEnk;
import pointGet.mission.parts.AnswerTasuuketu;
import pointGet.mission.parts.AnswerZukan;

public class GENPointResearch2 extends GENBase {
  final String url = "http://www.gendama.jp/survey";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;
  AnswerEnkShopQP EnkShopQP = null;

  AnswerSurveyEnk SurveyEnk = null;
  AnswerKotsuta Kotsuta = null;
  AnswerColum Colum = null;
  AnswerShindan Shindan = null;
  AnswerHiroba Hiroba = null;
  AnswerShopping Shopping = null;
  /* アンケートクラス　写真 */
  AnswerPhotoEnk PhotoEnk = null;
  /* アンケートクラス　図鑑 */
  AnswerZukan Zukan = null;
  /* アンケートクラス　多数決 */
  AnswerTasuuketu Tasuuketu = null;
  AnswerMinnanosur Minnanosur = null;
  AnswerEnqNstk EnqNstk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerKansatu Kansatu = null;

  /**
   * @param logg
   */
  public GENPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート2");
    PointResearch = new AnswerPointResearch(logg);
    EnkShopQP = new AnswerEnkShopQP(logg);

    SurveyEnk = new AnswerSurveyEnk(logg);
    Kotsuta = new AnswerKotsuta(logg);
    Colum = new AnswerColum(logg);
    Shindan = new AnswerShindan(logg);
    Hiroba = new AnswerHiroba(logg);
    Shopping = new AnswerShopping(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Zukan = new AnswerZukan(logg);
    Tasuuketu = new AnswerTasuuketu(logg);
    Minnanosur = new AnswerMinnanosur(logg);
    EnqNstk = new AnswerEnqNstk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    Kansatu = new AnswerKansatu(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "a dd.survey_answer";
    int skip = 1;
    String
    //    sele1 = "div.ui-control.type-fixed>a.ui-button", // pointResearch用
    sele2 = "div.page-content-button>input.button.btn-next", // 回答する 漫画用
    sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
    sele4 = "div>input[type='submit']", //
    sele4_ = "#iframe", //
    sele5 = "div#shindan", //
    sele6 = "form>input.next_bt", // コラム用
    sele7 = "div>button[type='submit']", //
    sele8 = "form>input.next_bt", sele9 = "a.start__button", sele10 = "div#buttonArea>input[name='next']", // shop-qp用(4択) // 回答する y2at用(〇×)// rsch用
    a = "";
    while (true) {
      if (!isExistEle(driver, selector)) {
        // 対象がなくなったら終了
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (targetIndex > -1 && size > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
        clickSleepSelector(eleList, targetIndex, 5000); // アンケートスタートページ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        logg.info("url[" + cUrl + "]");
        if (isExistEle(driver, sele2)) {
          Kotsuta.answer(driver, sele2, wid);
        }
        // surveyenk
        else if (isExistEle(driver, sele3)) {
          SurveyEnk.answer(driver, sele3, wid);
        }
        else if (isExistEle(driver, sele5)) {
          Shindan.answer(driver, sele5, wid);
        }
        else if ((cUrl.indexOf("question-hiroba") >= 0
            || cUrl.indexOf("kenko-gourmet.com") >= 0)
            && isExistEle(driver, sele7, false)) {
          Hiroba.answer(driver, sele7, wid);
        }
        else if (cUrl.indexOf("minnanosurvey.com") >= 0
            && isExistEle(driver, sele7)) {
          Minnanosur.answer(driver, sele7, wid);
        }
        else if (isExistEle(driver, sele4_)) {
          // $('iframe').contents().find("div>input[type='submit']")
          Shopping.answer(driver, sele4, wid);
        }
        else if ((cUrl.indexOf("column-enquete") >= 0
            || cUrl.indexOf("beautynail-design.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("style-cutehair.com") >= 0
            || cUrl.indexOf("fashion-cosmelife.com") >= 0)
            && isExistEle(driver, sele6)) {
          Colum.answer(driver, sele6, wid);
        }
        else if ((cUrl.indexOf("photo-enquete") >= 0
            || cUrl.indexOf("cosmetic-brand.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("natural-vegetables.com") >= 0
            || cUrl.indexOf("cosmeticsstyle.com") >= 0)
            && isExistEle(driver, sele8)) {
          PhotoEnk.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("cosme-beaute.com/picturebook") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("haircare-choice.com") >= 0
            )
            && isExistEle(driver, sele8)) {
          Zukan.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("eyemake-beauty.com") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("healthy-cookinglife.com") >= 0
            )
            && isExistEle(driver, sele8)) {
          Kansatu.answer(driver, sele8, wid);
        }
        else if (cUrl.indexOf("vote.media-ad.jp/") >= 0) {
          if (!Tasuuketu.answer(driver, sele9, wid))
            skip++;
        }
        else if (cUrl.indexOf("enq.nstk-4.com") >= 0
            && isExistEle(driver, sele10)) {
          EnqNstk.answer(driver, sele10, wid);
        }
        else if (cUrl.indexOf("enq.shop-qp.com") >= 0
            && isExistEle(driver, sele10)) {
          EnkShopQP.answer(driver, sele10, wid);
        }
        else if (cUrl.indexOf("enq.y2at.com") >= 0
            && isExistEle(driver, sele10)) {
          EnqY2at.answer(driver, sele10, wid);
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
        // 対象がなくなったら終了
        break;
      }
    }
  }
}
