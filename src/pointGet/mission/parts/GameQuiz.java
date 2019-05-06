package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameQuiz extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameQuiz(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    String aBtnStart = "a.so-quiz-btn[href='/quiz/check']";
    String aBtnStart2 = "p>a.btn_text";
    String aBtnStart3 = "div>a.so-quiz-btn";
    String qOpenSele = "div>a#quiz_start.so-quiz-btn";
    String answerSele = "a[class*='so-quiz-answer-";
    String toTopSele = "div>a[href='/quiz']";
    String waitStartSele = "span.so-quiz-btn-off";

    String existGetPointSele = "span.so-quiz-badge";
    String confirmPointSele = "\"a#trigger_reward_detail_title";
    String getPointSele = "a#btn_recieve";
    String pointConfirmClose = "button#cboxClose";

    clearFootOverlay(driver);
    clearOverlay(driver);
    if (isExistEle(driver, existGetPointSele, false)) {
      if (isExistEle(driver, confirmPointSele)) {
        clickSleepSelector(driver, confirmPointSele, 2000);
        if (isExistEle(driver, getPointSele)) {
          clickSleepSelector(driver, getPointSele, 2000);
        }
        if (isExistEle(driver, pointConfirmClose)) {
          clickSleepSelector(driver, pointConfirmClose, 2000);
        }
      }
    }

    if (isExistEle(driver, waitStartSele, false)) {
      logg.info("しばらくお待ち下さい");
      return;
    }
    if (isExistEle(driver, aBtnStart, false)) {
      clickSleepSelector(driver, aBtnStart, 4000);
      clearFootOverlay(driver);
      clearOverlay(driver);
      clearAfterOverlay(driver);
      if (isExistEle(driver, aBtnStart2)) {
        clickSleepSelector(driver, aBtnStart2, 4000);
        clearFootOverlay(driver);
        clearOverlay(driver);
        clearAfterOverlay(driver);
        if (isExistEle(driver, aBtnStart3)) {
          clickSleepSelector(driver, aBtnStart3, 4000);
          clearFootOverlay(driver);
          clearOverlay(driver);
          clearAfterOverlay(driver);
          for (int i = 0; i < 10; i++) {
            if (isExistEle(driver, qOpenSele)) {
              clickSleepSelector(driver, qOpenSele, 4000);
              clearFootOverlay(driver);
              clearOverlay(driver);
              clearAfterOverlay(driver);
              if (isExistEle(driver, answerSele)) {
                // 選択問題
                List<WebElement> eleList2 = driver.findElements(By.cssSelector(answerSele));
                int choiceies = eleList2.size();
                int choiceNum = Utille.getIntRand(choiceies);
                if (isExistEle(eleList2, choiceNum)) {
                  clickSleepSelector(driver, eleList2, choiceNum, 3000);
                  clearFootOverlay(driver);
                  clearOverlay(driver);
                  clearAfterOverlay(driver);
                }
              }
              // 次の問題
              if (isExistEle(driver, aBtnStart3)) {
                clickSleepSelector(driver, aBtnStart3, 4000);
                clearFootOverlay(driver);
                clearOverlay(driver);
                clearAfterOverlay(driver);
              }
              // TODO 抜けるタイミング
              // TODO　レベルアップ時
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
