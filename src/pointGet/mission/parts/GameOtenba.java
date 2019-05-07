package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameOtenba extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameOtenba(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String aBtnStart = "div.btn_start>a>img";
    String aBtnWaitStart = "div.btn_start>a>img[alt='ボタン 前回の結果']";
    String seleCourse = "a.select_course_btn>img"; // 方向セレクタ
    String nextSele = "div.btn_next>a>img";
    String boxSele = "div.unlock_collected_box>ul>li>a";

    clearOverlay(driver);
    clearFootOverlay(driver);
    if (isExistEle(driver, aBtnWaitStart, false)) {
      logg.info("次までまて");
      return;
    }
    if (isExistEle(driver, aBtnStart)) {
      for (int i = 0; i < 2; i++) {
        // スタート 2回
        if (isExistEle(driver, aBtnStart)) {
          clickSleepSelector(driver, aBtnStart, 4000);
          clearOverlay(driver);
          clearFootOverlay(driver);
        }
      }
      if (isExistEle(driver, seleCourse)) {
        // 探索開始
        for (boolean roopEndFlag = false; !roopEndFlag;) {
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(seleCourse));
          int choiceies = eleList2.size();
          int choiceNum = Utille.getIntRand(choiceies);
          if (isExistEle(eleList2, choiceNum)) {
            clickSleepSelector(driver, eleList2, choiceNum, 5000);
            clearOverlay(driver);
            clearFootOverlay(driver);
            if (isExistEle(driver, nextSele)) {
              // 何かみつかる?
              clickSleepSelector(driver, nextSele, 4000);
              clearOverlay(driver);
              clearFootOverlay(driver);
              if (isExistEle(driver, nextSele, false)) {
                // 探索終了
                roopEndFlag = true;
                clickSleepSelector(driver, nextSele, 4000);
                clearOverlay(driver);
                clearFootOverlay(driver);
              }
            }
          }
        }
        //
        if (isExistEle(driver, aBtnStart)) {
          // 戦い開始
          clickSleepSelector(driver, aBtnStart, 4000);
          for (boolean roopEndFlag = false; !roopEndFlag;) {
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(seleCourse));
            int choiceies = eleList2.size();
            int choiceNum = Utille.getIntRand(choiceies);
            if (isExistEle(eleList2, choiceNum)) {
              clickSleepSelector(driver, eleList2, choiceNum, 5000);
              clearOverlay(driver);
              clearFootOverlay(driver);
              if (isExistEle(driver, nextSele)) {
                // 倒せる？
                clickSleepSelector(driver, nextSele, 4000);
                clearOverlay(driver);
                clearFootOverlay(driver);
                if (isExistEle(driver, nextSele, false)) {
                  // 探索終了
                  roopEndFlag = true;
                  clickSleepSelector(driver, nextSele, 4000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                }
              }
            }
          }

          // 宝箱開ける
          if (isExistEle(driver, boxSele)) {
            for (boolean roopEndFlag = false; !roopEndFlag;) {
              if (isExistEle(driver, boxSele, false)) {
                List<WebElement> eleList2 = driver.findElements(By.cssSelector(boxSele));
                int choiceies = eleList2.size();
                if (eleList2.size() == 0) {
                  break;
                }
                int choiceNum = Utille.getIntRand(choiceies);
                if (isExistEle(eleList2, choiceNum)) {
                  clickSleepSelector(driver, eleList2, choiceNum, 5000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                  if (isExistEle(driver, nextSele)) {
                    // 空いた？
                    clickSleepSelector(driver, nextSele, 4000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                    if (isExistEle(driver, nextSele, false)) {
                      // 空いた結果
                      //                      roopEndFlag = true;
                      clickSleepSelector(driver, nextSele, 4000);
                      clearOverlay(driver);
                      clearFootOverlay(driver);
                    }
                  }
                }
              }
              else {
                // たぶんALLはずれ
                logg.info("全部はずれOneMore!");
                for (int i =0;i<2;i++) {
                  if (isExistEle(driver, nextSele, false)) {
                    clickSleepSelector(driver, nextSele, 4000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                  }
                }
                break;
              }
            }
            clearAfterOverlay(driver);
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
