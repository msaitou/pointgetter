package pointGet.mission.cri.old;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.cri.CRIBase;
import pointGet.mission.parts.AnswerAdsurvey;
import pointGet.mission.parts.AnswerGameParkEnk;

public class CRIGameParkEnk extends CRIBase {
  // 途中TODO
  final String url = "http://www.chobirich.com/game/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerGameParkEnk GameParkEnk = null;
  AnswerAdsurvey Adsurvey = null;

  /**
   * @param logg
   */
  public CRIGameParkEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ゲームパークアンケート");
    GameParkEnk = new AnswerGameParkEnk(logg);
    Adsurvey = new AnswerAdsurvey(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    waitTilReady(driver);
    selector = "img[alt*='ちょびんくんGamePark']";
    String pop1 = "div#campaignDialog div.btDialogBox", //
        pop1None = "div#campaignDialog[style*='display: none;'] div.btDialogBox", //
        pop2Cls = "div.campaignClose>img", //
        pop2ClsNone = "div.campaignClose[style*='display: none;']>img", //
        enkLinkSele = "div>a[href='/survey/summary/']>span", //
        a = "";
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 8000); // 遷移
      changeCloseWindow(driver);
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

        selector = "div.qBox button";
      int skip = 0, beforeSize = 0;
        String sele1 = "form>button#nextBtn", // pointResearch用
            sele2 = "form>input.btn_regular", //
            sele2_ = "iframe.question_frame", //
            b = "";
        while (true) {
          if (!isExistEle(driver, selector)) {
            break;
          }
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip;
//        int skip = 0, beforeSize = 0;
        if (beforeSize == size) {skip++;}
          if (size > targetIndex && isExistEle(eleList, targetIndex)) {
            String wid = driver.getWindowHandle();
            clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
            changeWindow(driver, wid);
            String cUrl = driver.getCurrentUrl();
            logg.info("url[" + cUrl + "]");

            if (cUrl.indexOf("chobirich.qpark.jp/enquete") >= 0) {
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
            beforeSize = size;
            Utille.refresh(driver, logg);
            Utille.sleep(5000);
          }
          else {
            break;
          }
        }
        String stampSele = "div#exchengeButton",
            historySele = "p.iconMdl>a";
        if (isExistEle(driver, historySele)) {
          clickSleepSelector(driver, historySele, 4000); // 遷移
          if (isExistEle(driver, stampSele)) {
            clickSleepSelector(driver, stampSele, 4000); // 遷移
          }
        }
      }
    }
  }
}
