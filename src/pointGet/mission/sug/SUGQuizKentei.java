package pointGet.mission.sug;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class SUGQuizKentei extends SUGBase {
  final String url = "http://www.sugutama.jp/game";

  /**
   * @param logg
   */
  public SUGQuizKentei(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "クイズ");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "dl.game-area>dt>a[href='/ssp/20']>img";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(9000);
      String uranaiSelector = "a>img[alt='ceres']";
      if (isExistEle(driver, uranaiSelector)) {
        clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
        changeCloseWindow(driver);
        checkOverlay(driver, "div.overlay-popup a.button-close");
        // finish condition
        String finishSelector = "p.ui-timer";
        if (isExistEle(driver, finishSelector)) {
          finsishFlag = true;
          return;
        }
        String noSele = "div.ui-item-no", titleSele = "h2.ui-item-title";
        selector = "form>input[name='submit']";
        Utille.sleep(4000);
        if (isExistEle(driver, selector)) {
          clickSelector(driver, selector);
          selector = "ul.ui-item-body";
          for (int i = 0; i < 8; i++) {
            Utille.sleep(4000);
            if (isExistEle(driver, noSele)) {
              String qNo = driver.findElement(By.cssSelector(noSele)).getText();
              String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
              logg.info(qNo + " " + qTitle);
              if (isExistEle(driver, selector)) {
                int ran = Utille.getIntRand(4);
                String selectId = "label[for='radio-";
                if (ran == 0) {
                  selectId += "1']";
                }
                else if (ran == 1) {
                  selectId += "2']";
                }
                else if (ran == 2) {
                  selectId += "3']";
                }
                else {
                  selectId += "4']";
                }
                // 8kai roop
                String selector2 = "input[name='submit']";
                if (isExistEle(driver, selectId)) {
                  clickSleepSelector(driver, selectId, 4000); // 遷移
                  int ranSleep = Utille.getIntRand(9);
                  Utille.sleep(ranSleep * 1000);
                  //								waitTilReady(driver);
                  if (isExistEle(driver, selector2)) {
                    //									waitTilReady(driver);
                    clickSleepSelector(driver, selector2, 4000); // 遷移
                    checkOverlay(driver, "div.overlay-popup a.button-close");
                    if (isExistEle(driver, selector2)) {
                      clickSleepSelector(driver, selector2, 3000); // 遷移
                      checkOverlay(driver, "div.overlay-popup a.button-close");
                    }
                  }
                }
              }
            }
          }
          logg.info(this.mName + "]kuria?");
          selector = "input[name='submit']";
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 3000);
            //						waitTilReady(driver);
          }
        }
        else {
          logg.warn(this.mName + "]獲得済み");
        }
      }
    }
  }
}
