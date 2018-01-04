package pointGet.mission.hap;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerHiroba;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerKotsuta;
import pointGet.mission.parts.AnswerMinnanosur;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerShopping;
import pointGet.mission.parts.AnswerSurveyEnk;
import pointGet.mission.parts.AnswerTasuuketu;
import pointGet.mission.parts.AnswerZukan;

public class HAPPointResearch extends HAPBase {
  final String url = "http://hapitas.jp/enquete/";
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
  AnswerMinnanosur Minnanosur = null;
  AnswerKansatu Kansatu = null;

  /**
   * @param logg
   */
  public HAPPointResearch(Logger logg, Map<String, String> cProps) {
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
    Minnanosur = new AnswerMinnanosur(logg);
    Kansatu = new AnswerKansatu(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "tbody#easyenquete td>a>img";
    int skip = 0;
    String sele2 = "div.page-content-button>input.button.btn-next", // 回答する 漫画用
    sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
    sele4 = "div>input[type='submit']", //
    sele4_ = "#iframe", //
    sele5 = "div#shindan", //
    sele6 = "form>input.next_bt", // コラム用
    sele7 = "div>button[type='submit']", //
    sele8 = "form>input.next_bt", //
    sele9 = "a.start__button", //
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
        // メール参照の場合スキップ
        WebElement judgeEle = eleList.get(targetIndex).findElement(By.xpath("../../../td[4]")); // 直前のtd
        logg.info("text[" + judgeEle.getText() + "]");
        if ("メール参照".equals(judgeEle.getText())) {
          skip++;
          continue;
        }
        clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
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
            && isExistEle(driver, sele7, false)) {  // キャプチャで見えないので
          Hiroba.answer(driver, sele7, wid);
        }
        else if (cUrl.indexOf("minnanosurvey.com") >= 0
            && isExistEle(driver, sele7)) {
          Minnanosur.answer(driver, sele7, wid);
        }
        else if (isExistEle(driver, sele4_)) {
          // $('iframe').contents().find("div>input[type='submit']")
          if (!Shopping.answer(driver, sele4, wid)) {
            skip++;
          }
        }
        else if ((cUrl.indexOf("column-enquete") >= 0
            || cUrl.indexOf("beautynail-design.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("fashion-cosmelife.com") >= 0
            || cUrl.indexOf("style-cutehair.com") >= 0
            )
            && isExistEle(driver, sele6)) {
          Utille.sleep(3000);
          Colum.answer(driver, sele6, wid);
        }
        else if ((cUrl.indexOf("photo-enquete") >= 0
            || cUrl.indexOf("cosmetic-brand.com") >= 0
                || cUrl.indexOf("eyelashes-fashion.com") >= 0
            || cUrl.indexOf("natural-vegetables.com") >= 0
            || cUrl.indexOf("cosmeticsstyle.com") >= 0)
            && isExistEle(driver, sele8)) {
          Utille.sleep(3000);
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
            || cUrl.indexOf("healthy-cookinglife.com") >= 0
            )
            && isExistEle(driver, sele8)) {
          Kansatu.answer(driver, sele8, wid);
        }
        else if (isExistEle(driver, sele9)) {
          Tasuuketu.answer(driver, sele9, wid);
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
