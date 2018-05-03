package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.MissCommon;

public class AnswerComicNews extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerComicNews(Logger log) {
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

    String agreeSele = "label[for='action-checkbox']", //
    goSele = "img[alt='進む']", // startSele
    nextSele = "img[alt='次へ']", //
    nextButtonSele = "div#next-button img", //
    overLay = "div#inter-ad-content div#inter-ad-close", //
    a = "";

    if (isExistEle(driver, agreeSele)) {
      clickSleepSelector(driver, agreeSele, 4000);
      if (isExistEle(driver, nextButtonSele)) {
        clickSleepSelector(driver, nextButtonSele, 3000);
      }
    }
    //    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    for (int g = 0; g < 10; g++) {
      if (isExistEle(driver, agreeSele)) {
        //        waitTilReady(driver);
        clickSleepSelector(driver, agreeSele, 3000); // 遷移　問開始するよ
        if (isExistEle(driver, nextSele)) {
          clickSleepSelector(driver, nextSele, 6000);
        }
      }
    }
    checkOverlay(driver, overLay);
    if (isExistEle(driver, goSele)) {
      clickSleepSelector(driver, goSele, 3000);
      checkOverlay(driver, overLay);
      String finishSele = "img[alt='終了する']";
      if (isExistEle(driver, finishSele)) {
        clickSleepSelector(driver, finishSele, 3000);
      }
    }
  }
}
