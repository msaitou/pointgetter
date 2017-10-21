package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqNstk;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerSurveyEnk;

public class GPOPointResearch2 extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  WebDriver driver = null;
  AnswerEnkShopQP EnkShopQP = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerEnqNstk EnqNstk = null;

  /**
   * @param logg
   */
  public GPOPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    EnkShopQP = new AnswerEnkShopQP(logg);
    SurveyEnk = new AnswerSurveyEnk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    EnqNstk = new AnswerEnqNstk(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "li>a.answer_btn";
    int skip = 0;
    String sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
        sele8 = "div#buttonArea>input[name='next']"; // enq.shop-qp.com,enq.y2at.com用
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "li.menu02>a";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      if (isExistEle(driver, selector2)) {
        clickSleepSelector(driver, selector2, 5000);

        while (true) {
          Utille.sleep(5000);
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size = eleList.size();
            int targetIndex = size - 1 - skip; // 順番はサイト毎に変更可能だが、変数を使う
            logg.info("size:" + size + " targetIndex:" + targetIndex + " skip:" + skip);
            if (targetIndex > -1 && size > targetIndex
                && isExistEle(eleList, targetIndex)) {
              String wid = driver.getWindowHandle();
              clickSleepSelector(eleList, targetIndex, 5000); // アンケートスタートページ
              changeWindow(driver, wid);
              String cUrl = driver.getCurrentUrl();
              logg.info("url[" + cUrl + "]");
              if (isExistEle(driver, sele3)) {
                // http://mini.surveyenquete.net
                SurveyEnk.answer(driver, sele3, wid);
                //            skip++;
              }
              else if (cUrl.indexOf("enq.shop-qp.com") >= 0
                  && isExistEle(driver, sele8)) {
                EnkShopQP.answer(driver, sele8, wid);
              }
              else if (cUrl.indexOf("enq.y2at.com") >= 0
                  && isExistEle(driver, sele8)) {
                EnqY2at.answer(driver, sele8, wid);
              }
              else if (cUrl.indexOf("enq.nstk-4.com") >= 0
                  && isExistEle(driver, sele8)) {
                EnqNstk.answer(driver, sele8, wid);
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
  }
}