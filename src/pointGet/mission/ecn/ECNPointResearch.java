package pointGet.mission.ecn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerResearcgEcEnq;

public class ECNPointResearch extends ECNBase {
  final String url = "http://ecnavi.jp/research/";
  WebDriver driver = null;
  AnswerResearcgEcEnq ResearcgEcEnq = null;
//  AnswerSurveyEnk SurveyEnk = null;
//  AnswerKotsuta Kotsuta = null;
//  AnswerColum Colum = null;
//  AnswerShindan Shindan = null;
//  AnswerHiroba Hiroba = null;
//  AnswerShopping Shopping = null;
//  /* アンケートクラス　写真 */
//  AnswerPhotoEnk PhotoEnk = null;
//  /* アンケートクラス　図鑑 */
//  AnswerZukan Zukan = null;
//  /* アンケートクラス　多数決 */
//  AnswerTasuuketu Tasuuketu = null;
//  AnswerManga Manga = null;
//  AnswerKonatab Konatab = null;
//  AnswerMinnanosur Minnanosur = null;

  /**
   * @param logg
   */
  public ECNPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");

    ResearcgEcEnq = new AnswerResearcgEcEnq(logg);
//    SurveyEnk = new AnswerSurveyEnk(logg);
//    Kotsuta = new AnswerKotsuta(logg);
//    Colum = new AnswerColum(logg);
//    Shindan = new AnswerShindan(logg);
//    Hiroba = new AnswerHiroba(logg);
//    Shopping = new AnswerShopping(logg);
//    PhotoEnk = new AnswerPhotoEnk(logg);
//    Zukan = new AnswerZukan(logg);
//    Tasuuketu = new AnswerTasuuketu(logg);
//    Manga = new AnswerManga(logg);
//    Konatab = new AnswerKonatab(logg);
//    Minnanosur = new AnswerMinnanosur(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "div#merchandise tr[style*='table-row']>td.reply>span.gn_orange>a";
    int skip = 2;
    String sele2 = "form.button_form>input[type='image']", //
//    sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
//    sele4 = "div>input[type='submit']", //
//    sele4_ = "#iframe", //
//    sele5 = "div#shindan", //
//    sele6 = "form>input.next_bt", // コラム用
//    sele7 = "div.btn>button[type='submit']", //
//    sele8 = "form>input.next_bt", //
//    sele9 = "a.start__button", //
//    sele10 = "p#nextBtn>input", //
//    sele1 = "form>input[type='image']", // 回答する 漫画用

    a = "";
    while (true) {
      if (!isExistEle(driver, selector)) {
        // 対象がなくなったら終了
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (targetIndex > -1 && size > targetIndex
          && isExistEle(eleList, targetIndex)) { // 古い順にやる
        clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        logg.info("url[" + cUrl + "]");
        if (cUrl.indexOf("research.ecnavi.jp/ar") >= 0
            && isExistEle(driver, sele2)) {
          ResearcgEcEnq.answer(driver, sele2, wid);
          skip++;
        }
//        // surveyenk
//        else if (isExistEle(driver, sele3)) {
//          SurveyEnk.answer(driver, sele3, wid);
//        }
//        else if (isExistEle(driver, sele5)) {
//          Shindan.answer(driver, sele5, wid);
//        }
//        else if (cUrl.indexOf("question-hiroba") >= 0
//            && isExistEle(driver, sele7)) {
//          Hiroba.answer(driver, sele7, wid);
//        }
//        else if (cUrl.indexOf("minnanosurvey.com") >= 0
//            && isExistEle(driver, sele7)) {
//          Minnanosur.answer(driver, sele7, wid);
//        }
//        else if (isExistEle(driver, sele4_)) {
//          // $('iframe').contents().find("div>input[type='submit']")
//          Shopping.answer(driver, sele4, wid);
//        }
//        else if ((cUrl.indexOf("column-enquete") >= 0
//            || cUrl.indexOf("beautynail-design.com") >= 0
//            || cUrl.indexOf("fashion-cosmelife.com") >= 0
//            )
//            && isExistEle(driver, sele6)) {
//          Colum.answer(driver, sele6, wid);
//        }
//        else if (cUrl.indexOf("photo-enquete") >= 0
//            && isExistEle(driver, sele8)) {
//          PhotoEnk.answer(driver, sele8, wid);
//        }
//        else if (cUrl.indexOf("cosme-beaute.com/picturebook") >= 0
//            && isExistEle(driver, sele8)) {
//          Zukan.answer(driver, sele8, wid);
//        }
//        else if (isExistEle(driver, sele9)) {
//          Tasuuketu.answer(driver, sele9, wid);
//        }
//        // 漫画
//        else if (isExistEle(driver, sele1)) {
//          Manga.answer(driver, sele1, wid);
//          skip++; // 残るのでスキップ
//        }
//        else if (isExistEle(driver, sele10)) {
//          Konatab.answer(driver, sele10, wid);
//        }
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
