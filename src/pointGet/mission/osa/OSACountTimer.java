package pointGet.mission.osa;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

public class OSACountTimer extends OSABase {
  final String url = "http://osaifu.com/coinland/";

  /**
   * @author saitou 0時、12時開催
   */
  public OSACountTimer(Logger log, Map<String, String> cProps) {
    super(log, cProps, "CountTimer");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "li>a>img[alt='Count Timer']";
    String overlaySele = "div.overlay-popup a.button-close", // 
        overlayNoneSele = "div.overlay-popup[style*='display: none;'] a.button-close";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      String overlaySelector = "div.overlay.overlay-timer>div.overlay-item[style*='display: block'] a.button-close";
      checkOverlay(driver, overlaySelector);
      selector = "form.fx-control>input[name='submit']";
      Utille.sleep(4000);
      if (isExistEle(driver, selector)) {
        clickSelector(driver, selector);
        for (int i = 0; i < 5; i++) {
          Utille.sleep(6000);
          if (!isExistEle(driver, overlayNoneSele, false)) {
            checkOverlay(driver, overlaySele);
          }
          String selectorExpression = "span.try-subject-time";
          int waitTime = 0;
          if (isExistEle(driver, selectorExpression)) {
            String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
            logg.info("お題　" + text);
            waitTime = Utille.getWaitTimeRan(text);
          }
          else {
            logg.info("not get odai");
            break;
          }
          // label.ui-label-radio.ui-circle-button[for='radio-1']
          String selectId = "div.fx-control>input#js-timer_btn_start";
          String selectStop = "div.fx-control>input#js-timer_btn_stop";
          String selectRes = "input#timer_btn_stop";
          if (isExistEle(driver, selectId)) {
            clickSleepSelector(driver, selectId, 0); // START!!
            long s = System.currentTimeMillis();
            Utille.sleep(waitTime);
            long e = System.currentTimeMillis();
            System.out.println("sleep " + (s - e));
            if (isExistEle(driver, selectStop)) {
              clickSleepSelector(driver, selectStop, 1000); // END
              if (isExistEle(driver, selectRes)) {
                clickSleepSelector(driver, selectRes, 3000); // 結果
                // クリアなら次のボタンが表示
                String next = "input.ui-button.ui-button-b.ui-button-result.quake";
                String reset = "input.ui-button.ui-button-a.ui-button-reset";
                if (isExistEle(driver, next)) {
                  clickSleepSelector(driver, next, 3000); // 次
                }
                else {
                  clickSleepSelector(driver, reset, 3000); // 最初から
                  i = -1;
                }
              }
            }
          }
        }

        if (!isExistEle(driver, overlayNoneSele, false)) {
          checkOverlay(driver, overlaySele);
        }
        String selectorEnd = "input.ui-button.ui-button-b.ui-button-end.quake";
        if (isExistEle(driver, selectorEnd)) {
          clickSleepSelector(driver, selectorEnd, 3000); // 次
        }
        if (!isExistEle(driver, overlayNoneSele, false)) {
          checkOverlay(driver, overlaySele);
        }
        // ボーナスチャレンジ
        String bounusGo = "input.ui-button.ui-button-b.ui-button-start";
        selector = "div.fx-control>a.ui-button.ui-button-a.ui-button-close.quake";
        if (isExistEle(driver, bounusGo)) {
          clickSleepSelector(driver, bounusGo, 5000); // 次
          if (!isExistEle(driver, overlayNoneSele, false)) {
            checkOverlay(driver, overlaySele);
          }
          String selectorExpression = "span.try-subject-time";
          int waitTime = 0;
          if (isExistEle(driver, selectorExpression)) {
            String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
            logg.info("お題　" + text);
            waitTime = Utille.getWaitTime(text);
            waitTime -= 299;
          }
          else {
            logg.info("not get odai");
            return;
          }
          // label.ui-label-radio.ui-circle-button[for='radio-1']
          String selectId = "div.fx-control>input#js-timer_btn_start";
          String selectStop = "div.fx-control>input#js-timer_btn_stop";
          String selectRes = "input#timer_btn_stop";
          if (isExistEle(driver, selectId)) {
            clickSleepSelector(driver, selectId, 0); // START!!
            long s = System.currentTimeMillis();
            Utille.sleep(waitTime);
            long e = System.currentTimeMillis();
            System.out.println("sleep " + (s - e));
            if (isExistEle(driver, selectStop)) {
              clickSleepSelector(driver, selectStop, 1000); // END
              if (isExistEle(driver, selectRes)) {
                clickSleepSelector(driver, selectRes, 3000); // 結果
                // クリアなら次のボタンが表示
                String next = "input.ui-button.ui-button-b.ui-button-result.quake";
                if (isExistEle(driver, next)) {
                  clickSleepSelector(driver, next, 3000); // 次
                }
                else {
                  clickSleepSelector(driver, selector, 3000); // 終了
                }
              }
            }
          }
        }
        if (!isExistEle(driver, overlayNoneSele, false)) {
          checkOverlay(driver, overlaySele);
        }
        if (isExistEle(driver, selectorEnd)) {
          clickSleepSelector(driver, selectorEnd, 3000); // 終了
        }
        logg.info(this.mName + "]kuria?");
        finsishFlag = true;
      }
      else {
        String endSelector = "input[name='submit']";
        if (isExistEle(driver, endSelector)) {
          clickSleepSelector(driver, endSelector, 3000);
          waitTilReady(driver);
        }
        logg.warn(this.mName + "]獲得済み");
        finsishFlag = true;
      }
    }
  }
}
