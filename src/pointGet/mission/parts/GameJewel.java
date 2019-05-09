package pointGet.mission.parts;

import java.util.ArrayList;
import java.util.HashMap;
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
//    String seleCourse = "a.btn_select_course>img"; // 方向セレクタ
    String seleCourse = "a.btn_select_course"; // 方向セレクタ
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

    String existGetPointSele = "div.notify_yesterday_reward";
    String confirmPointSele = "img[alt*='報酬を確認']";
    String getPointSele = "img[alt*='報酬を受け取る']";
    String pointConfirmClose = "button#cboxClose";
    // ライフがある限りループ
    clearOverlay(driver);
    clearFootOverlay(driver);
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

    while (true) {
      // ライフがある限りループ
      clearOverlay(driver);
      clearFootOverlay(driver);
      if (!isExistEle(driver, lifeSele, false)) {
        logg.info("no life ...");
        break;
      }
      initCrossingStatus();
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
//            int choiceies = eleList2.size();
            //            int choiceNum = Utille.getIntRand(choiceies);

            int choiceNum = judgementWay(eleList2);
            clickSleepSelector(driver, eleList2, choiceNum, 5000);
            clearOverlay(driver);
            clearFootOverlay(driver);

            // event発生
            if (isExistEle(driver, seleActionSele, false)) {
              clickSleepSelector(driver, seleActionSele, 5000);
              clearOverlay(driver);
              clearFootOverlay(driver);
              // 戦う
              if (isExistEle(driver, seleAttackSele)) {
                clickSleepSelector(driver, seleAttackSele, 5000);
                clearOverlay(driver);
                clearFootOverlay(driver);
                if (isExistEle(driver, seleActionSele, false)) {
                  clickSleepSelector(driver, seleActionSele, 7000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                  if (isExistEle(driver, seleEventClear2, false)) {
                    clickSleepSelector(driver, seleEventClear2, 6000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                    if (isExistEle(driver, seleEventClear, false)) {
                      clickSleepSelector(driver, seleEventClear, 10000);
                      clearOverlay(driver);
                      clearFootOverlay(driver);
                      //                      break;
                    }
                  }
                  else if (isExistEle(driver, seleEventFale, false)) {
                    clickSleepSelector(driver, seleEventFale, 4000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                    if (isExistEle(driver, seleGameOver, false)) {
                      clickSleepSelector(driver, seleGameOver, 8000);
                      clearOverlay(driver);
                      clearFootOverlay(driver);
                      break;
                    }
                  }
                }
              }
              else if (isExistEle(driver, seleActionSele)) {
                clickSleepSelector(driver, seleActionSele, 5000);
                clearOverlay(driver);
                clearFootOverlay(driver);
                if (isExistEle(driver, seleEventClear)) {
                  clickSleepSelector(driver, seleEventClear, 5000);
                }
              }
              else if (isExistEle(driver, seleEventHole)) {
                clickSleepSelector(driver, seleEventHole, 5000);
                clearOverlay(driver);
                clearFootOverlay(driver);
                if (isExistEle(driver, seleNext)) {
                  clickSleepSelector(driver, seleNext, 5000);
                  clearOverlay(driver);
                  clearFootOverlay(driver);
                  if (isExistEle(driver, seleGameOver, false)) {
                    clickSleepSelector(driver, seleGameOver, 8000);
                    clearOverlay(driver);
                    clearFootOverlay(driver);
                    break;
                  }
                }
              }
            }
            else if (isExistEle(driver, seleCongratuRate)) {
              clickSleepSelector(driver, seleCongratuRate, 5000);
              clearOverlay(driver);
              clearFootOverlay(driver);
              break;
              //              if (isExistEle(driver, seleToTop)) {
              //                clickSleepSelector(driver, seleToTop, 5000);
              //              }
            }
          }
        }
        clearFootOverlay(driver);
        clearOverlay(driver);
        clearAfterOverlay(driver);
        if (isExistEle(driver, toTitleSele)) {
          // スタート画面に戻る
          clickSleepSelector(driver, toTitleSele, 5000);
          clearOverlay(driver);
          clearFootOverlay(driver);
        }
      }
    }
    // この画面にほかのゲームのリンクがあるのでそっから遷移するためにウィンドウを消さない
    //    driver.close();
    //    driver.switchTo().window(wid);
  }

  private int currentX = 0;
  private int currentY = 0;
  private HashMap<String, Integer> crossingstatus = new HashMap<String, Integer>();

  private void initCrossingStatus() {
    currentX = 0;
    currentY = 0;
    crossingstatus = new HashMap<String, Integer>();
  }
  private int judgementWay(List<WebElement> eleList) {
    logg.info("currentX:" + currentX + " currentY:" + currentY);
    int selectWay = 0;
    // key x+currentX+y+currentY
    String currentKey = getKey(currentX, currentY);
    if (!crossingstatus.containsKey(currentKey)) {
      crossingstatus.put(currentKey, 1); // 踏破しています
    }
    // 選択可能な方向の情報を整理
    ArrayList<HashMap<String, String>> availCourses = new ArrayList<HashMap<String, String>>();
    for (int i = 0; i < eleList.size(); i++) {
      // TODO data-course　1 上　3 左　2　下　4 右
      //  driver.findElement(By.id("sample").getAttribute(attr1)
      HashMap<String, String> availInfo = new HashMap<String, String>();
      availInfo.put("data-cource", eleList.get(i).getAttribute("data-course"));
      availCourses.add(availInfo);
    }

    // 選択可能な方向の踏破情報を作成
    for (int i = 0; i < availCourses.size(); i++) {
      HashMap<String, String> availCourse = availCourses.get(i);
      int availX = currentX;
      int availY = currentY;
      switch (availCourse.get("data-cource")) {
        case "1":
          availY++; // 上
          break;
        case "2":
          availY--; // 下
          break;
        case "3":
          availX++; // 左
          break;
        case "4":
          availX--; // 右
          break;
      }
      String availKey = getKey(availX, availY);
      availCourse.put("availKey", availKey);
      if (!crossingstatus.containsKey(availKey)) {
        crossingstatus.put(availKey, 0); // 未踏破
      }
    }
    HashMap<String, ArrayList<Integer>> kouho = new HashMap<String, ArrayList<Integer>>();
    // 進む方向を決定する準備
    for (int i = 0; i < availCourses.size(); i++) {
      String availKey = availCourses.get(i).get("availKey");
      String primaryKey = String.valueOf(crossingstatus.get(availKey));
      if (!kouho.containsKey(primaryKey)) {
        ArrayList<Integer> iList = new ArrayList<Integer>();
        iList.add(i);
        kouho.put(primaryKey, iList);
      }
      else {
        ArrayList<Integer> iList = kouho.get(primaryKey);
        iList.add(i);
        kouho.put(primaryKey, iList);
      }
    }

    if (availCourses.size() > 1 && kouho.containsKey("2")) {
      if (availCourses.size() - 1 == kouho.get("2").size()) {
        // 今いる場所を袋小路として2を設定
        crossingstatus.put(currentKey, 2);
      }
    }
    else if (availCourses.size() == 1) {
      // 今いる場所を袋小路として2を設定
      crossingstatus.put(currentKey, 2);
    }

    String primaryList[] = new String[] { "0", "1" };
    for (int i = 0; i < primaryList.length; i++) {
      if (kouho.containsKey(primaryList[i])) {
        selectWay = kouho.get(primaryList[i]).get(0); // 最初の添え字を優先
        String availKey = availCourses.get(selectWay).get("availKey");
        crossingstatus.put(availKey, 1);
        break;
      }
    }
    switch (availCourses.get(selectWay).get("data-cource")) {
      case "1":
        currentY++; // 上
        break;
      case "2":
        currentY--; // 下
        break;
      case "3":
        currentX++; // 左
        break;
      case "4":
        currentX--; // 右
        break;
    }

    logg.info("進む方向:" + availCourses.get(selectWay).get("data-cource") + " 選択肢:" + selectWay);
    return selectWay;
  }

  private String getKey(int x, int y) {
    return "x" + String.valueOf(x) + "y" + String.valueOf(y);
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
