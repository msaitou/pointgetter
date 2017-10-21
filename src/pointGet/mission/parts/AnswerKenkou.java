package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerKenkou extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerKenkou(Logger log) {
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

    String readEndSele = "p.end_reading>input", //
    seleEnd = "form>input[type='image']", //
    overLay = "div#interstitial[style*='display: block']>div>div#inter-close", //
    seleNext2 = "div>input.column_nextbt[value='次へ']", //　次へセレクター
    a = "";

    for (int g = 0; g < 2; g++) {
      if (isExistEle(driver, seleEnd)) {
        clickSleepSelector(driver, seleEnd, 3000);
      }
    }
    for (int g = 0; g < 10; g++) {
      if (isExistEle(driver, readEndSele)) {
        //        waitTilReady(driver);
        clickSleepSelector(driver, readEndSele, 3000); // 遷移　問開始するよ
        if (isExistEle(driver, seleNext2)) {
          clickSleepSelector(driver, seleNext2, 6000);
        }
      }
    }

    checkOverlay(driver, overLay);
    Utille.clickRecaptha(driver, logg);
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 5000);
    }
    checkOverlay(driver, overLay);
    Utille.clickRecaptha(driver, logg);
    if (isExistEle(driver, seleEnd)) {
      clickSleepSelector(driver, seleEnd, 6000);
    }
    //    driver.close();
    //    driver.switchTo().window(wid);
  }
}
