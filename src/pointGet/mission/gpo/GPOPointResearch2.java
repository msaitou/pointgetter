package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqNstk;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerPointResearch;
import pointGet.mission.parts.AnswerSurveyEnk;

public class GPOPointResearch2 extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  WebDriver driver = null;
  AnswerEnkShopQP EnkShopQP = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerEnqNstk EnqNstk = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;

  /**
   * @param logg
   */
  public GPOPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    EnkShopQP = new AnswerEnkShopQP(logg);
    SurveyEnk = new AnswerSurveyEnk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    EnqNstk = new AnswerEnqNstk(logg);
    PointResearch = new AnswerPointResearch(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon", //
    selector2 = "a[onclick*='otokuenquete']";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      if (isExistEle(driver, selector2)) {
        clickSleepSelector(driver, selector2, 5000);
        // アラートをけして
        checkAndAcceptAlert(driver);
        Utille.sleep(2000);
        changeCloseWindow(driver);
        selector = "td.status>a.ui-button"; // アンケート一覧の回答するボタン
        Utille.sleep(5000);
        String sele8 = "form>input.next_bt";
        int skip = 0;
        String
        //    sele1 = "div.ui-control.type-fixed>a.ui-button",
        sele6 = "form>input.next_bt", // コラム用
        sele2 = "form>input[alt='進む']", // 回答する 漫画用
        sele1_ = "input[type='button']", //
        sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
        sele4 = "div#buttonArea>input[name='next']", // shop-qp用(4択) // 回答する y2at用(〇×)// rsch用
        sele7 = "div.btn>button[type='submit']", //
        a = "";
        while (true) {
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = 0 + skip;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
              Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
              Actions actions = new Actions(driver);
              actions.keyDown(Keys.CONTROL);
              actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
              actions.perform();
              Utille.sleep(5000);

              String wid = driver.getWindowHandle();
              changeWindow(driver, wid);
              String cUrl = driver.getCurrentUrl();
              logg.info("url[" + cUrl + "]");
              if (isExistEle(driver, sele1_)) {
                PointResearch.answer(driver, sele1_, wid);
              }
              else if (isExistEle(driver, sele3)) {
                // http://mini.surveyenquete.net
                SurveyEnk.answer(driver, sele3, wid);
                //            skip++;
              }
              else if (cUrl.indexOf("enq.shop-qp.com") >= 0
                  && isExistEle(driver, sele8)) {
                EnkShopQP.answer(driver, sele8, wid);
              }
              else if ((cUrl.indexOf("enq.y2at.com") >= 0
                  || cUrl.indexOf("enq.torixchu.com") >= 0)
                  && isExistEle(driver, sele8)) {
                EnqY2at.answer(driver, sele8, wid);
              }
              else if ((cUrl.indexOf("enq.nstk-4.com") >= 0
                  || cUrl.indexOf("enq.gourmet-syokusai.com") >= 0)
                  && isExistEle(driver, sele8)) {
                EnqNstk.answer(driver, sele8, wid);
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
          else {
            break;
          }
        }
      }
    }
  }
}
