package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class GameBears extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public GameBears(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String aBtnStart = "div.bearsBtnArea>p>a>img";
    String goToPlay = "div.bearsBtnArea>p>a>img[alt='あそびにいく']";
    String toTopSele = "div.bearsBtnArea>p>a>img[alt='TOPへ']";
    String toNextSele = "div.bearsBtnArea>p>a>img[alt='つぎへ']";
    String toNextSele2 = "div.bearsBtnArea>p>img[alt='つぎへ']";

    String nextPage = "p.bearsNext";
    String selePlayPlace = "div#bearsMap>div>dl>a>dt";
    String lifeSele = "img[src*='life_on']";

    String existGetPointSele = "a#trigger_reward>img[alt*='報酬を受け取る']";
    String getPointSele = "a#btn_recieve>img[alt*='報酬を受け取る']";
    String pointConfirmClose = "div#cboxClose";
    // ライフがある限りループ
    clearOverlay(driver);
    clearFootOverlay(driver);
    if (isExistEle(driver, existGetPointSele, false)) {
        clickSleepSelector(driver, existGetPointSele, 2000);
        if (isExistEle(driver, getPointSele)) {
          clickSleepSelector(driver, getPointSele, 2000);
        }
        if (isExistEle(driver, pointConfirmClose)) {
          clickSleepSelector(driver, pointConfirmClose, 2000);
        }
    }

    int loopCnt = 0;
    boolean opendParty = false;
    while (loopCnt < 10) {
      // ライフがある限りループ
      clearOverlay(driver);
      clearFootOverlay(driver);
      if (!isExistEle(driver, lifeSele, false)) {
        logg.info("no life ...");
        break;
      }

      if (isExistEle(driver, aBtnStart)) {
        for (int i = 0; i < 2; i++) {
          // スタート 2回
          if (isExistEle(driver, aBtnStart)) {
            clickSleepSelector(driver, aBtnStart, 4000);
            clearOverlay(driver);
            clearFootOverlay(driver);
            clearAfterOverlay(driver);
          }
        }

        if (isExistEle(driver, nextPage)) {
          clickSleepSelector(driver, nextPage, 4000);
          clearOverlay(driver);
          clearFootOverlay(driver);
          clearAfterOverlay(driver);
          if (isExistEle(driver, selePlayPlace)) {
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(selePlayPlace));
            int choiceies = eleList2.size();
            int choiceNum = Utille.getIntRand(choiceies);
            if (choiceNum == 3) {
              if (!opendParty) {
                opendParty = true;
              }
              else {
                choiceNum = 0;
              }
            }

            if (isExistEle(eleList2, choiceNum)) {
              clickSleepSelector(driver, eleList2, choiceNum, 5000);
              clearOverlay(driver);
              clearFootOverlay(driver);
              if (isExistEle(driver, goToPlay)) {
                clickSleepSelector(driver, goToPlay, 4000);
                clearOverlay(driver);
                clearFootOverlay(driver);
                clearAfterOverlay(driver);
                if (isExistEle(driver, nextPage)) {
                  clickSleepSelector(driver, nextPage, 4000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                  clearAfterOverlay(driver);

                  if (isExistEle(driver, nextPage, false)) {
                    for (int i = 0; i < 2;) {
                      // 多分パーティ
                      if (isExistEle(driver, nextPage, false)) {
                        clickSleepSelector(driver, nextPage, 4000);
                        clearOverlay(driver);
                        clearFootOverlay(driver);
                      }
                      if (isExistEle(driver, toNextSele, false)) {
                        clickSleepSelector(driver, toNextSele, 4000);
                        clearOverlay(driver);
                        clearFootOverlay(driver);
                        i++; // 2回で多分終わりなはず
                        logg.info("パーティ" + i);
                      }
                    }
                  }
                  else if (choiceNum == 3 && isExistEle(driver, aBtnStart)) {
                    logg.info("パーティやりすぎ");
                    logg.info("TOPに戻らされてました！！");
                    continue;
                  }
                  else {
                    if (isExistEle(driver, toNextSele, false)) {
                      logg.info("友達発見？！！");
                      // 友達発見
                      // choiceNum  1の時亀でその時に対応できていないポイ
                      for (int i = 0; i < 10;i++) {
                        if (isExistEle(driver, toNextSele, false)) {
                          clickSleepSelector(driver, toNextSele, 4000);
                          clearOverlay(driver);
                          clearFootOverlay(driver);
                          clearAfterOverlay(driver);
//                          i++;
                        }
                        else if (isExistEle(driver, toNextSele2, false)) {
                          clickSleepSelector(driver, toNextSele2, 4000);
                          clearOverlay(driver);
                          clearFootOverlay(driver);
                          clearAfterOverlay(driver);
                        }

                        if (isExistEle(driver, nextPage, false)) {
                          clickSleepSelector(driver, nextPage, 4000);
                          clearOverlay(driver);
                          clearFootOverlay(driver);
                          clearAfterOverlay(driver);
                        }
                      }
                    }
                    else if (isExistEle(driver, aBtnStart)) {
                      clickSleepSelector(driver, aBtnStart, 4000);
                      clearOverlay(driver);
                      clearFootOverlay(driver);
                      clearAfterOverlay(driver);
                      if (isExistEle(driver, nextPage, false)) {
                        clickSleepSelector(driver, nextPage, 4000);
                        clearOverlay(driver);
                        clearFootOverlay(driver);
                        clearAfterOverlay(driver);
                      }
                    }
                  }
                }
                else if (choiceNum != 3 && isExistEle(driver, aBtnStart)) {
                  clickSleepSelector(driver, aBtnStart, 4000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                  clearAfterOverlay(driver);
                  if (isExistEle(driver, nextPage, false)) {
                    clickSleepSelector(driver, nextPage, 4000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                    clearAfterOverlay(driver);
                  }
                }
              }
              if (isExistEle(driver, toTopSele)) {
                logg.info("TOPへ！！");
                // TOPへ戻る
                clickSleepSelector(driver, toTopSele, 4000);
                clearOverlay(driver);
                clearFootOverlay(driver);
              }
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
