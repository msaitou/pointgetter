package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameDinosaur extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameDinosaur(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
//    String aBtnStart = "a.btn_start>img";
    String aBtnStart = "a[href='/dinosaur/check']>img";
    String selectBait = "div#dinosaur_contents>ul>li>a";
    String releasePoints = "div#horihori_location_3>a>img";
    String selectTool = "div.game_content>a>img";

    String fightSele = "div#fish_under_battle>a>img";

    String toNextSele = "a.btn_next>img";
    String battleSele = "a.btn_battle>img";
    String getSele = "a.btn_get>img";
    String toTopSele = "a>img[alt='ボタン トップへ']";

    String lifeSele = "img[src*='dinosaur_life_on']";

    int loopCnt = 0;
    while (loopCnt < 10) {
      // ライフがある限りループ
      clearFootOverlay(driver);
      clearOverlay(driver);
      if (!isExistEle(driver, lifeSele, false)) {
        logg.info("no life ...");
        break;
      }

      if (isExistEle(driver, aBtnStart)) {
        for (int i = 0; i < 2; i++) {
          // スタート 2回
          clearFootOverlay(driver);
          clearOverlay(driver);
          if (isExistEle(driver, aBtnStart)) {
            clickSleepSelector(driver, aBtnStart, 4000);
          }
        }

        if (isExistEle(driver, selectBait)) {
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(selectBait));
          int choiceNum = Utille.getIntRand(eleList2.size()); // 餌の選択
          if (isExistEle(eleList2, choiceNum)) {
            clickSleepSelector(driver, eleList2, choiceNum, 5000);
            clearFootOverlay(driver);
            clearOverlay(driver);

            if (isExistEle(driver, selectTool)) {
              //              List<WebElement> eleList3 = driver.findElements(By.cssSelector(releasePoints));
              //              int choiceNum2 = Utille.getIntRand(eleList3.size());  // リリースポイントの選択
              //              if (isExistEle(eleList3, choiceNum2)) {
              //                clickSleepSelector(driver, eleList3, choiceNum2, 7000);
              //                clearFootOverlay(driver);
              //                clearOverlay(driver);
              for (;;) {
                if (isExistEle(driver, selectTool)) {
                  List<WebElement> eleList4 = driver.findElements(By.cssSelector(selectTool));
                  int choiceNum3 = Utille.getIntRand(eleList4.size());
                  if (isExistEle(eleList4, choiceNum3)) {
                    clickSleepSelector(driver, eleList4, choiceNum3, 4000);
                    clearFootOverlay(driver);
                    clearOverlay(driver);
                    if (isExistEle(driver, toNextSele, false)) {
                      clickSleepSelector(driver, toNextSele, 4000);
                      clearFootOverlay(driver);
                      clearOverlay(driver);
                    }
                    else if (isExistEle(driver, battleSele)) {
                      clickSleepSelector(driver, battleSele, 4000);
                      clearFootOverlay(driver);
                      clearOverlay(driver);
                      break;
                    }
                  }
                }
              }
              if (isExistEle(driver, selectTool)) {
                for (;;) {
                  List<WebElement> eleList4 = driver.findElements(By.cssSelector(selectTool));
                  int choiceNum3 = Utille.getIntRand(eleList4.size());
                  if (isExistEle(eleList4, choiceNum3)) {
                    clickSleepSelector(driver, eleList4, choiceNum3, 6000);
                    clearFootOverlay(driver);
                    clearOverlay(driver);
                    clearAfterOverlay(driver);
                    if (isExistEle(driver, toNextSele, false)) {
                      clickSleepSelector(driver, toNextSele, 4000);
                      clearFootOverlay(driver);
                      clearOverlay(driver);
                    }
                    else if (isExistEle(driver, getSele)) {
                      clickSleepSelector(driver, getSele, 5000);
                      clearFootOverlay(driver);
                      clearOverlay(driver);
                      clearAfterOverlay(driver);
                      if (isExistEle(driver, toNextSele, false)) {
                        clickSleepSelector(driver, toNextSele, 4000);
                        clearFootOverlay(driver);
                        clearOverlay(driver);
                        clearAfterOverlay(driver);
                        if (isExistEle(driver, toTopSele)) {
                          clickSleepSelector(driver, toTopSele, 4000);
                          clearFootOverlay(driver);
                          clearOverlay(driver);
                          clearAfterOverlay(driver);
                          break;
                        }
                      }
                    }
                    // TODO 壊れたとき
                  }
                }
              }
            }
          }
        }
      }
      //      }
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
