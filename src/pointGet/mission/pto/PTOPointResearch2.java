package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerAdserver;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerHiroba;
import pointGet.mission.parts.AnswerInfopanel;
import pointGet.mission.parts.AnswerKotsuta;
import pointGet.mission.parts.AnswerMinnanosur;
import pointGet.mission.parts.AnswerShindan;
import pointGet.mission.parts.AnswerShopping;
import pointGet.mission.parts.AnswerSurveyEnk;

public class PTOPointResearch2 extends PTOBase {
  final String url = "http://www.pointtown.com/ptu/pointpark/enquete/top.do";
  WebDriver driver = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerAdserver Adserver = null;
  AnswerColum Colum = null;
  AnswerKotsuta Kotsuta = null;
  AnswerInfopanel Infopanel = null;
  AnswerShindan Shindan = null;
  AnswerHiroba Hiroba = null;
  AnswerShopping Shopping = null;
  AnswerMinnanosur Minnanosur = null;

  /**
   * @param logg
   */
  public PTOPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ2!!");
    SurveyEnk = new AnswerSurveyEnk(logg);
    Adserver = new AnswerAdserver(logg);
    Colum = new AnswerColum(logg);
    Kotsuta = new AnswerKotsuta(logg);
    Infopanel = new AnswerInfopanel(logg);
    Shindan = new AnswerShindan(logg);
    Hiroba = new AnswerHiroba(logg);
    Shopping = new AnswerShopping(logg);
    Minnanosur = new AnswerMinnanosur(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    int skip = 0;
    String sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
    sele1 = "div>input.btn_submit", //
    sele4 = "div>input[type='submit']", //
    sele4_ = "#iframe", //
    sele5 = "div#shindan", //
    sele7 = "div.btn>button[type='submit']", //
    sele6 = "form>input.next_bt", // コラム用
    sele2 = "div.page-content-button>input.button.btn-next";
    while (true) {
      Utille.sleep(5000);
      selector = "div#pt-enq1 td.frame04>a.promo_enq_bt";
      if (isExistEle(driver, selector)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size(), targetIndex = size - 1 - skip; // 順番はサイト毎に変更可能だが、変数を使う
        logg.info("size:" + size + " targetIndex:" + targetIndex + " skip:" + skip);
        if (size > targetIndex && targetIndex > -1
            && isExistEle(eleList, targetIndex)) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(eleList, targetIndex, 10000); // アンケートスタートページ
          changeWindow(driver, wid);
          String cUrl = driver.getCurrentUrl();
          logg.info("url[" + cUrl + "]");
          if (isExistEle(driver, sele3)) {
            SurveyEnk.answer(driver, sele3, wid);
            //            skip++;
          }
          else if (isExistEle(driver, sele2)) {
            Kotsuta.answer(driver, sele2, wid);
          }
          else if (isExistEle(driver, sele1)) {
            Infopanel.answer(driver, sele1, wid);
          }
          else if (isExistEle(driver, sele5)) {
            Shindan.answer(driver, sele5, wid);
          }
          else if ((cUrl.indexOf("column-enquete") >= 0
              || cUrl.indexOf("beautynail-design.com") >= 0
              || cUrl.indexOf("fashion-cosmelife.com") >= 0
              )
              && isExistEle(driver, sele6)) {
            Colum.answer(driver, sele6, wid);
          }
          else if (cUrl.indexOf("question-hiroba") >= 0
              && isExistEle(driver, sele7)) {
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
      else {
        break;
      }
    }
  }
}
