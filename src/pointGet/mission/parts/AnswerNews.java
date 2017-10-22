package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerNews extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerNews(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    clickSleepSelector(driver, startSele, 3000);

    String agreeSele = "div.terms_info label", //
    readEndSele = "div.news_footer>label", //
    overLayNone = "div#inter-ad-content[style*='display: none;'] div#inter-ad-close", //
    overLay = "div#inter-ad-content div#inter-ad-close", //
    a = "";

    if (isExistEle(driver, agreeSele)) {
      clickSleepSelector(driver, agreeSele, 4000);
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 3000);
        //        if (isExistEle(driver, startSele)) {
        //          clickSleepSelector(driver, startSele, 3000);
        //        }
      }
    }

    //    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    for (int g = 0; g < 10; g++) {
      if (isExistEle(driver, readEndSele)) {
        //        waitTilReady(driver);
        clickSleepSelector(driver, readEndSele, 3000); // 遷移　問開始するよ
        if (isExistEle(driver, startSele)) {
          clickSleepSelector(driver, startSele, 6000);
        }
      }
    }
    checkOverlay(driver, overLay);
    Utille.clickRecaptha(driver, logg);
    String finishSele = "input[alt='進む']";
    if (isExistEle(driver, finishSele)) {
      clickSleepSelector(driver, finishSele, 10000);
    }
    if (!isExistEle(driver, overLayNone, false)) {
      checkOverlay(driver, overLay);
      Utille.clickRecaptha(driver, logg);
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 6000);
      }
    }
    //    driver.close();
    //    driver.switchTo().window(wid);
  }
}
