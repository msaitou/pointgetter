package pointGet.mission.cri;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHiroba;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerKonatab;
import pointGet.mission.parts.AnswerKotsuta;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerMinnanosur;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerShopping;
import pointGet.mission.parts.AnswerSurveyEnk;
import pointGet.mission.parts.AnswerTasuuketu;
import pointGet.mission.parts.AnswerZukan;

public class CRIPointResearch extends CRIBase {
  final String url = "http://www.chobirich.com/mypage/research/";
  WebDriver driver = null;
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
  AnswerManga Manga = null;
  AnswerKonatab Konatab = null;
  AnswerMinnanosur Minnanosur = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;

  /**
   * @param logg
   */
  public CRIPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");

    SurveyEnk = new AnswerSurveyEnk(logg);
    Kotsuta = new AnswerKotsuta(logg);
    Colum = new AnswerColum(logg);
    Shindan = new AnswerShindan(logg);
    Hiroba = new AnswerHiroba(logg);
    Shopping = new AnswerShopping(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Zukan = new AnswerZukan(logg);
    Tasuuketu = new AnswerTasuuketu(logg);
    Manga = new AnswerManga(logg);
    Konatab = new AnswerKonatab(logg);
    Minnanosur = new AnswerMinnanosur(logg);
    Kansatu = new AnswerKansatu(logg);
    Cooking = new AnswerCooking(logg);
    Hyakkey = new AnswerHyakkey(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);

    selector = "div.research_box_03.research_box td>p.btn_answer>a";
    int skip = 1;
    String sele2 = "div.page-content-button>input.button.btn-next", // 回答する 漫画用
    sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
    sele4 = "div>input[type='submit']", //
    sele4_ = "#iframe", //
    sele5 = "div#shindan", //
    sele6 = "form>input.next_bt", // コラム用
    sele7 = "div>button[type='submit']", //
    sele8 = "form>input.next_bt", //
    sele9 = "a.start__button", //
    sele10 = "p#nextBtn>input", //
    tab3 = "li.tab_03>a",
    sele1 = "form>input[type='image']", // 回答する 漫画用
    a = "";

    while (true) {
      if (isExistEle(driver, tab3)) {
        clickSleepSelector(driver, tab3, 5000); // アンケートスタートページ
      }
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
        if ((cUrl.indexOf("kotsuta.com") >= 0
            || cUrl.indexOf("kotsukotsutame-ru.com") >= 0)
            && isExistEle(driver, sele2)) {
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
            || cUrl.indexOf("medicine-good.com") >= 0
            || cUrl.indexOf("kenko-gourmet.com") >= 0
            || cUrl.indexOf("shigoto-hoken.com") >= 0
            )
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
            || cUrl.indexOf("column.") >= 0
            || cUrl.indexOf("column.fingernail.work") >= 0
            || cUrl.indexOf("beautynail-design.com") >= 0
            || cUrl.indexOf("fashion-cosmelife.com") >= 0
            || cUrl.indexOf("cmsite.fitness-health.work/column") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("style-cutehair.com") >= 0
            )
            && isExistEle(driver, sele6)) {
          Colum.answer(driver, sele6, wid);
          skip++;
        }
        else if ((cUrl.indexOf("eyemake-beauty.com") >= 0
            || cUrl.indexOf("observation.") >= 0
            || cUrl.indexOf("observation.trekking-mountain.net/") >= 0
            || cUrl.indexOf("cmsite.fitness-health.work/observation") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("healthy-cookinglife.com") >= 0
            )
            && isExistEle(driver, sele8)) {
          Kansatu.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("photo-enquete") >= 0
            || cUrl.indexOf("natural-vegetables.com") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("cmsite.fitness-health.work/photo") >= 0
            || cUrl.indexOf("eyemake-beauty.com") >= 0
            || cUrl.indexOf("cosmeticsstyle.com") >= 0)
            && isExistEle(driver, sele8)) {
          PhotoEnk.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("cosme-beaute.com") >= 0
            || cUrl.indexOf("animal.") >= 0
            || cUrl.indexOf("animal.marriage-flower.net/") >= 0
            || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("haircare-choice.com") >= 0
            || cUrl.indexOf("cmsite.fitness-health.work/animal") >= 0
            || cUrl.indexOf("eyemake-beauty.com") >= 0
            )
            && isExistEle(driver, sele8)) {
          Zukan.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("natural-cuisine.com") >= 0
            //                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            //                || cUrl.indexOf("haircare-choice.com") >= 0
            //                || cUrl.indexOf("eyemake-beauty.com") >= 0
            || cUrl.indexOf("cooking.") >= 0
            || cUrl.indexOf("cooking.petit-cosme.net") >= 0
            )
            && isExistEle(driver, sele8)) {
          Cooking.answer(driver, sele8, wid);
        }
        else if ((cUrl.indexOf("beautyhair-fashion.com") >= 0
            //              || cUrl.indexOf("eyelashes-fashion.com") >= 0
            //              || cUrl.indexOf("haircare-choice.com") >= 0
            //              || cUrl.indexOf("eyemake-beauty.com") >= 0
            || cUrl.indexOf("map.") >= 0
            || cUrl.indexOf("map.wedding-church.net") >= 0
            )
            && isExistEle(driver, sele8)) {
          Hyakkey.answer(driver, sele8, wid);
        }
        else if (isExistEle(driver, sele9)) {
          Tasuuketu.answer(driver, sele9, wid);
        }
        // 漫画
        else if (isExistEle(driver, sele1)) {
          Manga.answer(driver, sele1, wid);
          skip++; // 残るのでスキップ
        }
        else if (isExistEle(driver, sele10)) {
          Konatab.answer(driver, sele10, wid);
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
        // 対象がなくなったら終了
        break;
      }
    }
  }
}
