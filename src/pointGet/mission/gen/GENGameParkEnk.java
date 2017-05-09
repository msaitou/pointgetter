package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerAdsurvey;
import pointGet.mission.parts.AnswerGameParkEnk;

public class GENGameParkEnk extends GENBase {
  final String url = "http://www.gendama.jp/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerGameParkEnk GameParkEnk = null;
  AnswerAdsurvey Adsurvey = null;

  /**
   * @param logg
   */
  public GENGameParkEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "GameParkアンケート");
    GameParkEnk = new AnswerGameParkEnk(logg);
    Adsurvey = new AnswerAdsurvey(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    driver.get("http://gendama-plus.gamepark.net/");
    String pop1 = "div#campaignDialog p.btnPdPlay", //
    pop1None = "div#campaignDialog[style*='display: none;'] p.btnPdPlay", //
    pop2Cls = "div.campaignClose>img", //
    pop2ClsNone = "div.campaignClose[style*='display: none;']>img", //
    enkLinkSele = "li.survey>a>img", //
    a = "";
    if (!isExistEle(driver, pop1None, false)
        && isExistEle(driver, pop1)) {
      clickSleepSelector(driver, pop1, 4000); // 遷移
      if (!isExistEle(driver, pop2ClsNone, false)
          && isExistEle(driver, pop2Cls)) {
        clickSleepSelector(driver, pop2Cls, 4000); // 遷移
      }
    }
    if (isExistEle(driver, enkLinkSele)) {
      clickSleepSelector(driver, enkLinkSele, 4000); // 遷移

      selector = "div.qBox>button";
      int skip = 1;
      String sele1 = "form>button#nextBtn", // pointResearch用
      sele2 = "form>input.btn_regular", // 
      sele2_ = "iframe.question_frame", //
      b = "";
      while (true) {
        if (!isExistEle(driver, selector)) {
          break;
        }
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size(), targetIndex = 0;
        if (size > targetIndex && isExistEle(eleList, targetIndex)) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
          changeWindow(driver, wid);
          String cUrl = driver.getCurrentUrl();

          if (cUrl.indexOf("gendama.qpark.jp/enquete/") >= 0
              && isExistEle(driver, sele1)) {
            Utille.sleep(4000);
            GameParkEnk.answer(driver, sele1, wid);
          }
          else if (cUrl.indexOf("adsurvey.media-ad.jp") >= 0
              && isExistEle(driver, sele2_)) {
            // $('iframe').contents().find("div>input[type='submit']")
            Adsurvey.answer(driver, sele2, wid);
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
    }
  }
}
