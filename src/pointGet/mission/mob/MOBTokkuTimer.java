package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

public class MOBTokkuTimer extends MOBBase {
  final String url = "http://pc.mtoku.jp/contents/";

  /**
   * @author saitou
   */
  public MOBTokkuTimer(Logger log, Map<String, String> cProps) {
    super(log, cProps, "CountTimer");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "img[src='https://pc-assets.mtoku.jp/common/img/contents/item_tokku_timer.png']";
    String overlaySele = "div#colorbox button#cboxClose", //
    overlayNoneSele = "div#colorbox[style*='display: none;'] button#cboxClose";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      checkOverlay(driver, overlaySele);
      Utille.sleep(4000);
        String selectorExpression = "span.tokku_timer__subject_time";
        String selectId = "input#timer_btn_start";
        String selectStop = "input#timer_btn_stop";
        String selectRes = "div#ok_btn>input.tokku_timer__comfirm__btn__ok";
        String next = "input",
            retryBtn = "a.tokku_timer__btn__return";
        
        int limitRoop = 0;
        while (limitRoop++ < 5) {
          int waitTime = 0;
          if (isExistEle(driver, selectorExpression)) {
            String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
            logg.info("お題　" + text);
            waitTime = Utille.getWaitTime(text);
            waitTime -= 110;
          }
          else {
            logg.info("not get odai");
            return;
          }
          if (isExistEle(driver, selectId)) {
            clickSleepSelector(driver, selectId, 0); // START!!
            long s = System.currentTimeMillis();
            Utille.sleep(waitTime);
            long e = System.currentTimeMillis();
            if (isExistEle(driver, selectStop)) {
              clickSleepSelector(driver, selectStop, 5000); // END
              if (isExistEle(driver, selectRes)) {
                clickSleepSelector(driver, selectRes, 4000); // 結果
                
                checkOverlay(driver, overlaySele);
                
                // クリアなら次のボタンが表示
                if (isExistEle(driver, next)) {
                  clickSleepSelector(driver, next, 3000); // 次
                }
                else if (isExistEle(driver, retryBtn)) {
                  clickSleepSelector(driver, retryBtn, 3000); // 終了
                }
              }
            }
            System.out.println("sleep " + (s - e));
          }
        }
      }
    //      if (!isExistEle(driver, overlayNoneSele, false)) {
    //        checkOverlay(driver, overlaySele);
    //      }
    //      if (isExistEle(driver, selectorEnd)) {
    //        clickSleepSelector(driver, selectorEnd, 3000); // 終了
    //      }
      logg.info(this.mName + "]kuria?");
      finsishFlag = true;
    //    }
    //    else {
    //      String endSelector = "input[name='submit']";
    //      if (isExistEle(driver, endSelector)) {
    //        clickSleepSelector(driver, endSelector, 3000);
    //        waitTilReady(driver);
    //      }
    //      logg.warn(this.mName + "]獲得済み");
    //      finsishFlag = true;
    //    }
  }
}
