package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameJewel extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameJewel(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    String aBtnStart = "a.btn_start>img";
    String aBtnText = "p>a.btn_text";
    String seleCourse = "a.btn_select_course>img"; // 方向セレクタ
    String seleEventClear = "div#item_get>a.btn_next>img";
    String seleEventHole = "div#event_hole>a.btn_next>img";
    String seleGameOver = "div#gameover>a.btn_next>img";
    String seleEventClear2 = "div#win>a.btn_next>img";
    String seleEventFale = "div#lost>a.btn_next>img";
    String seleCongratuRate = "div#goal>a.btn_next>img";
    String seleActionSele = "div.action_event>a.btn_text";
    String seleNext = "a.btn_next>img";
    String seleAttackSele = "div.action_event>a.btn_attack";
    String toTitleSele = "img[alt='ボタン タイトルへ戻る']";
    String lifeSele = "li>img[alt='ライフあり']";
    while (true) {
      // ライフがある限りループ
      clearFootOverlay(driver);
      clearOverlay(driver);
      if (!isExistEle(driver, lifeSele, false)) {
        logg.info("no life ...");
        break;
      }

      if (isExistEle(driver, aBtnStart)) {
        Utille.scrolledPage(driver, driver.findElement(By.cssSelector(aBtnStart)));
        clickSleepSelector(driver, aBtnStart, 5000);
        if (isExistEle(driver, aBtnText, false)) {
          Utille.scrolledPage(driver, driver.findElement(By.cssSelector(aBtnText)));
          clickSleepSelector(driver, aBtnText, 3000);
        }
        int roopCnt = 0;
        while (roopCnt++ < 2000) {
          // ここから冒険開始
          if (isExistEle(driver, seleCourse)) {
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(seleCourse));
            int choiceies = eleList2.size();
            int choiceNum = Utille.getIntRand(choiceies);
            clickSleepSelector(driver, eleList2, choiceNum, 5000);
            // event発生
            if (isExistEle(driver, seleActionSele, false)) {
              clickSleepSelector(driver, seleActionSele, 5000);
              clearFootOverlay(driver);
              clearOverlay(driver);
              // 戦う
              if (isExistEle(driver, seleAttackSele)) {
                clickSleepSelector(driver, seleAttackSele, 5000);
                clearFootOverlay(driver);
                clearOverlay(driver);
                if (isExistEle(driver, seleActionSele, false)) {
                  clickSleepSelector(driver, seleActionSele, 7000);
                  clearFootOverlay(driver);
                  clearOverlay(driver);
                  if (isExistEle(driver, seleEventClear2, false)) {
                    clickSleepSelector(driver, seleEventClear2, 6000);
                    clearFootOverlay(driver);
                    clearOverlay(driver);
                    if (isExistEle(driver, seleEventClear, false)) {
                      clearFootOverlay(driver);
                      clearOverlay(driver);
                      clickSleepSelector(driver, seleEventClear, 10000);
                      //                      break;
                    }
                  }
                  else if (isExistEle(driver, seleEventFale, false)) {
                    clickSleepSelector(driver, seleEventFale, 4000);
                    clearFootOverlay(driver);
                    clearOverlay(driver);
                    if (isExistEle(driver, seleGameOver, false)) {
                      clickSleepSelector(driver, seleGameOver, 8000);
                      break;
                    }
                  }
                }
              }
              else if (isExistEle(driver, seleActionSele)) {
                clickSleepSelector(driver, seleActionSele, 5000);
                clearFootOverlay(driver);
                clearOverlay(driver);
                if (isExistEle(driver, seleEventClear)) {
                  clickSleepSelector(driver, seleEventClear, 5000);
                }
              }
              else if (isExistEle(driver, seleEventHole)) {
                clickSleepSelector(driver, seleEventHole, 5000);
                clearFootOverlay(driver);
                clearOverlay(driver);
                if (isExistEle(driver, seleNext)) {
                  clickSleepSelector(driver, seleNext, 5000);
                  clearFootOverlay(driver);
                  clearOverlay(driver);
                  if (isExistEle(driver, seleGameOver, false)) {
                    clickSleepSelector(driver, seleGameOver, 8000);
                    break;
                  }
                }
              }
            }
            else if (isExistEle(driver, seleCongratuRate)) {
              clickSleepSelector(driver, seleCongratuRate, 5000);
              //              if (isExistEle(driver, seleToTop)) {
              //                clickSleepSelector(driver, seleToTop, 5000);
              //              }
            }
          }
        }
        clearAfterOverlay(driver);
        clearFootOverlay(driver);
        clearOverlay(driver);
        if (isExistEle(driver, toTitleSele)) {
          // スタート画面に戻る
          clickSleepSelector(driver, toTitleSele, 5000);
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
