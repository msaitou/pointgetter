package pointGet.mission.parts;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class PlayKanji extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public PlayKanji(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public boolean answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String noSele = "div.ui-item-no", //
    titleSele = "h2.ui-item-title", //
    submitSele = "div.ui-control>form>input[name='submit']", //
    selectYoubi = "", //
    selectId = "", //
    itemBodySele = "ul.ui-item-body", // 曜日要素全体
    selector2 = "input[name='submit'].ui-button.ui-button-b.ui-button-answer.quake", //
    selector3 = "input[name='submit'].ui-button.ui-button-b.ui-button-result.quake", //
    selector4 = "input[name='submit'].ui-button.ui-button-b.ui-button-end.quake", //
    closeSele = "div.fx-control>a.ui-button.ui-button-a.ui-button-close.quake", //
    popCloseSele = "div.overlay-popup a.button-close";

    checkOverlay(driver, popCloseSele);
    // finish condition
    String finishSelector = "p.ui-timer";
    if (isExistEle(driver, finishSelector)) {
//      finsishFlag = true;
      return true;
    }
    Utille.sleep(4000);
    if (isExistEle(driver, submitSele)) {
      clickSelector(driver, submitSele);
      for (int i = 0; i < 10; i++) {
        Utille.sleep(4000);
        if (isExistEle(driver, noSele)) {
          String qNo = driver.findElement(By.cssSelector(noSele)).getText();
          String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qNo + " " + qTitle);
        }
        selectYoubi = String.valueOf(Utille.getIntRand(4) + 1);
        if (isExistEle(driver, itemBodySele)) {
          // label.ui-label-radio.ui-circle-button[for='radio-1']
          selectId = "label.ui-label-radio[for='radio-" + selectYoubi + "']";
          if (isExistEle(driver, selectId)) {
            clickSleepSelector(driver, selectId, 4000); // 遷移
            int ranSleep = Utille.getIntRand(6);
            Utille.sleep(ranSleep * 1000);
            //              waitTilReady(driver);
            if (isExistEle(driver, selector2)) {
              //                waitTilReady(driver);
              clickSleepSelector(driver, selector2, 4000); // 遷移
              checkOverlay(driver, popCloseSele);
              if (isExistEle(driver, selector3)) {
                clickSleepSelector(driver, selector3, 5000); // 遷移
                checkOverlay(driver, popCloseSele);
                if (isExistEle(driver, selector4)) {
                  clickSleepSelector(driver, selector4, 5000); // 遷移
                  checkOverlay(driver, popCloseSele);
                }
              }
            }
          }
        }
      }
      logg.info(this.mName + "]kuria?");
      if (isExistEle(driver, closeSele)) {
        clickSleepSelector(driver, closeSele, 3000);
        waitTilReady(driver);
      }
    }
    else {
      logg.warn(this.mName + "]獲得済み");
      return true;
    }
    return false;
  }
}
