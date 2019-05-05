package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameRace extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameRace(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String aBtnStart = "div.raceBtnArea>p>a>img[alt*='はじめる']";
    String aBtnNext = "div.raceBtnArea>p>a>img[alt*='NEXT']";
    String selectVegi = "div.receSelect img";
    String exchangeSele = "div.raceBtnArea>p>a>img[alt*='メダルを交換する']";
    String aBtnWaitStart = "div.raceBtnCoverTop";


    clearFootOverlay(driver);
    clearOverlay(driver);
    if (isExistEle(driver, aBtnWaitStart, false)) {
      logg.info("次までまて");
      return;
    }
    if (isExistEle(driver, aBtnStart)) {
      clickSleepSelector(driver, aBtnStart, 4000);
      clearFootOverlay(driver);
      clearOverlay(driver);
      clearAfterOverlay(driver);
      for (int i = 0; i < 3; i++) {
        if (isExistEle(driver, aBtnNext)) {
          clickSleepSelector(driver, aBtnNext, 4000);
          clearFootOverlay(driver);
          clearOverlay(driver);
          clearAfterOverlay(driver);
        }
      }

      if (isExistEle(driver, selectVegi)) {
        // 1位予想選択
        List<WebElement> eleList2 = driver.findElements(By.cssSelector(selectVegi));
        int choiceies = eleList2.size();
        int choiceNum = Utille.getIntRand(choiceies);
        if (isExistEle(eleList2, choiceNum)) {
          clickSleepSelector(driver, eleList2, choiceNum, 5000);
          clearFootOverlay(driver);
          clearOverlay(driver);
          clearAfterOverlay(driver);
          if (isExistEle(driver, aBtnStart)) {
            clickSleepSelector(driver, aBtnStart, 15000);
            clearFootOverlay(driver);
            clearOverlay(driver);
            clearAfterOverlay(driver);
            for (int i = 0; i < 5; i++) {
              if (isExistEle(driver, aBtnNext)) {
                clickSleepSelector(driver, aBtnNext, 15000);
                clearFootOverlay(driver);
                clearOverlay(driver);
                clearAfterOverlay(driver);
              }
            }
            clearAfterOverlay(driver);
            if (isExistEle(driver, aBtnNext)) {
              clickSleepSelector(driver, aBtnNext, 4000);
              clearFootOverlay(driver);
              clearOverlay(driver);
              clearAfterOverlay(driver);
            }
            if (isExistEle(driver, exchangeSele, false)) {
              clickSleepSelector(driver, exchangeSele, 4000);
              clearFootOverlay(driver);
              clearOverlay(driver);
              clearAfterOverlay(driver);
            }

          }
        }
      }
    }
    // この画面にほかのゲームのリンクがあるのでそっから遷移するためにウィンドウを消さない
    //    driver.close();
    //    driver.switchTo().window(wid);
  }

  private void clearFootOverlay(WebDriver driver) {
    String footOverlay = "div.ads_overlay span.close_ads_overlay";
    String footOverlayNone = "div.ads_overlay[style*='display: none'] span.close_ads_overlay";
    if (!isExistEle(driver, footOverlayNone, false)
        && isExistEle(driver, footOverlay)) {
      clickSelector(driver, footOverlay);
    }
  }

  private void clearOverlay(WebDriver driver) {
    String finishOverlay = "div#show_adds span.win_show_adds_close";
    String finishNoneOverlay = "div#show_adds[style*='display: none'] span.win_show_adds_close";
    String overlay = "div#show_adds span.win_show_adds_close";
    String overlayNone = "div#show_adds[style*='display: none'] span.win_show_adds_close";
    if (!isExistEle(driver, finishNoneOverlay, false)
        && isExistEle(driver, finishOverlay)) {
      clickSelector(driver, finishOverlay);
    }
    if (!isExistEle(driver, overlayNone, false)
        && isExistEle(driver, overlay)) {
      clickSelector(driver, overlay);
    }
  }

  private void clearAfterOverlay(WebDriver driver) {
    String afterPopup = "div#show_play_bonus_last_window span.win_show_adds_close";
    String afterPopupNone = "div#show_play_bonus_last_window[style*='display: none'] span.win_show_adds_close";
    String afterPopup2 = "div#show_life_window span.win_show_adds_close";
    String afterPopup2None = "div#show_life_window[style*='display: none'] span.win_show_adds_close";
    if (!isExistEle(driver, afterPopupNone, false)
        && isExistEle(driver, afterPopup)) {
      clickSelector(driver, afterPopup);
    }
    if (!isExistEle(driver, afterPopup2None, false)
        && isExistEle(driver, afterPopup2)) {
      clickSelector(driver, afterPopup2);
    }
  }
}
