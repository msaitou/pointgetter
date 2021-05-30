package pointGet.mission.osa;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqNstk2;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerPointResearch;
import pointGet.mission.parts.AnswerSurveyEnk;

public class OSAPointResearch extends OSABase {
  final String url = "https://osaifu.com/enquete/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;
  AnswerEnkShopQP EnkShopQP = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerManga Manga = null;
  AnswerEnqNstk2 EnqNstk2 = null;

  /**
   * @param logg
   */
  public OSAPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");
    PointResearch = new AnswerPointResearch(logg);
    EnkShopQP = new AnswerEnkShopQP(logg);
    SurveyEnk = new AnswerSurveyEnk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    Manga = new AnswerManga(logg);
    EnqNstk2 = new AnswerEnqNstk2(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "a.a-btn-primary";
    int skip = 1;
    String sele1 = "div.ui-control.type-fixed>a.ui-button", // pointResearch用
    sele2 = "form>input[type='image']", // 回答する 漫画用
    sele3 = "div>button[type='submit']", // 回答する surveyenk用
    sele4 = "div#buttonArea>input[name='next']"; // shop-qp用(4択) // 回答する y2at用(〇×)// rsch用
    while (true) {
      if (!isExistEle(driver, selector)) {
        // 対象がなくなったら終了
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (targetIndex > -1 && size > targetIndex
          && isExistEle(eleList, targetIndex)) { // 古い順にやる
        String sikibetuSele = "td.enquete__cell__num";
        if (isExistEle(driver, sikibetuSele)) {
          String ankNo = driver.findElements(By.cssSelector(sikibetuSele)).get(targetIndex).getText();
          logg.info("ankNo： " + ankNo);
          //					// osaオンリー
          //					if (ankNo.length() > 5) {
          //						mainus++;
          //						continue;
          //					}
        }
        clickSleepSelector(driver, eleList, targetIndex, 6000); // アンケートスタートページ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        logg.info("url[" + cUrl + "]");
        if (isExistEle(driver, sele1)) {
          PointResearch.answer(driver, sele1, wid);
        }
        // 漫画
        else if (isExistEle(driver, sele2)) {
//          _answerManga(sele2, wid);
          Manga.answer(driver, sele2, wid);
          logg.info("manngayたんと動く？");
        }
        // surveyenk
        else if (isExistEle(driver, sele3)) {
          SurveyEnk.answer(driver, sele3, wid);
          skip++;
        }
        // shop-qp
        else if (cUrl.indexOf("enq.shop-qp.com") >= 0
            && isExistEle(driver, sele4)) {
          EnkShopQP.answer(driver, sele4, wid);
        }
        else if ((cUrl.indexOf("enq.y2at.com") >= 0
            || cUrl.indexOf("enq.torixchu.com") >= 0)
            && isExistEle(driver, sele4)) {
          EnqY2at.answer(driver, sele4, wid);
        }
        else if ((cUrl.indexOf("enq.nstk-4.com") >= 0
            || cUrl.indexOf("enq.gourmet-syokusai.com") >= 0)
            && isExistEle(driver, sele4)) {
          EnqNstk2.answer(driver, sele4, wid);
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
