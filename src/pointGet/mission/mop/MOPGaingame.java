package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class MOPGaingame extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";

  /**
   * @param logg
   */
  public MOPGaingame(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ゲインゲーム");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "div.game_btn>div.icon>img[alt='リーグオブジュエル']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(4000);
      String aBtnStart = "a.btn_start>img";
      String aBtnText = "p>a.btn_text";
      String seleCourse = "a.btn_select_course>img"; // 方向セレクタ
      String seleEventClear = "div#item_get>a.btn_next>img";
      String seleEventHole = "div#event_hole>a.btn_next>img";
      String seleEventClear2 = "div#win>a.btn_next>img";
      String seleCongratuRate = "div#goal>a.btn_next>img";
      String seleActionSele = "div.action_event>a.btn_text";
      String seleToTop = "a.btn_go_title>img";
      String seleAttackSele = "div.action_event>a.btn_attack";
      String finishOverlay = "div#show_adds span.win_show_adds_close";
      String footOverlay = "div.ads_overlay span.close_ads_overlay";
      String footOverlayNone = "div.ads_overlay[style*='display: none'] span.close_ads_overlay";
      if (!isExistEle(driver, footOverlayNone, false)
          && isExistEle(driver, footOverlay)) {
        clickSelector(driver, footOverlay);
      }
      if (isExistEle(driver, aBtnStart)) {
        Utille.scrolledPage(driver, driver.findElement(By.cssSelector(aBtnStart)));
        clickSleepSelector(driver, aBtnStart, 5000);
        if (isExistEle(driver, aBtnText, false)) {
          Utille.scrolledPage(driver, driver.findElement(By.cssSelector(aBtnText)));
          clickSleepSelector(driver, aBtnText, 3000);
        }
        while (true) {
          // ここから冒険開始
          if (isExistEle(driver, seleCourse)) {
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(seleCourse));
            int choiceies = eleList2.size();
            int choiceNum = Utille.getIntRand(choiceies);
            clickSleepSelector(eleList2, choiceNum, 5000);
            // event発生
            if (isExistEle(driver, seleActionSele, false)) {
              clickSleepSelector(driver, seleActionSele, 5000);
              // 戦う
              if (isExistEle(driver, seleAttackSele)) {
                clickSleepSelector(driver, seleAttackSele, 5000);
                if (isExistEle(driver, seleActionSele, false)) {
                  clickSleepSelector(driver, seleActionSele, 8000);
                  if (isExistEle(driver, seleEventClear2, false)) {
                    clickSleepSelector(driver, seleEventClear2, 4000);
                    if (isExistEle(driver, seleEventClear, false)) {
                      clickSleepSelector(driver, seleEventClear, 4000);
                    }
                  }
                }
              }
              else if (isExistEle(driver, seleActionSele)) {
                clickSleepSelector(driver, seleActionSele, 5000);
                if (isExistEle(driver, seleEventClear)) {
                  clickSleepSelector(driver, seleEventClear, 5000);
                }
              }
              else if (isExistEle(driver, seleEventHole)) {
                clickSleepSelector(driver, seleEventHole, 5000);
              }
            }
            else if (isExistEle(driver, seleCongratuRate)) {
              clickSleepSelector(driver, seleCongratuRate, 5000);
              if (isExistEle(driver, seleToTop)) {
                clickSleepSelector(driver, seleToTop, 5000);
              }
            }
          }
        }
      }
    }
  }
}
