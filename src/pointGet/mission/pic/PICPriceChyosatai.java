package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 * @author saitou
 * 4時更新
 */
public class PICPriceChyosatai extends PICBase {
  final String url = "http://pointi.jp/game/index.php";
  private String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
  private String footBnrSelector = "div.foot-bnr a.close>span";

  /**
   * @param log
   */
  public PICPriceChyosatai(Logger log, Map<String, String> cProps) {
    super(log, cProps, "Price調査隊");
  }

  @Override
  public void privateMission(WebDriver driver) {
    for (int j = 0; j < 6; j++) {
      driver.get(url);
      String overlayNone = "div.foot-bnr[style*='display :none'] a.close>span";
      selector = "a[href='http://pointi.jp/api/prices.php']>img[alt='プライス調査隊']";
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 4000); // 遷移
        changeCloseWindow(driver);
        Utille.sleep(4000);
        checkOverlay(driver, overlaySelector);
        if (isExistEle(driver, footBnrSelector)
            && !isExistEle(driver, overlayNone, false)) {
          checkOverlay(driver, footBnrSelector);
        }

        String noEntrySele = "div.thumbnail span.icon-noentry";
        String entrySele = "div.thumbnail span.icon-entry";
        if (isExistEle(driver, noEntrySele)) {
          clickSleepSelector(driver, noEntrySele, 3000); // 遷移
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, overlayNone, false)) {
            checkOverlay(driver, footBnrSelector);
          }

          selector = "div.thumb-start div.btn>a";
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4000); // 遷移
            checkOverlay(driver, overlaySelector);
            checkOverlay(driver, footBnrSelector);
            String finshSele = "div.finish-area";
            // otukare!
            if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
              break;
            }
            localRoop1(driver);
          }
        }
        else if (isExistEle(driver, entrySele)) {
          clickSleepSelector(driver, entrySele, 3000); // 遷移
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, overlayNone, false)) {
            checkOverlay(driver, footBnrSelector);
          }
          //					selector = "div.btn>a";
          localRoop1(driver);
        }
        else {
          j = 6;
        }
      }
    }
  }

  private void localRoop1(WebDriver driver) {
    selector = "span.icon-arrow";
    String sele2top = "div#popup";
    String sele2none = "[style*='display:none;']";
    String sele2bot = " div.btn.mrg-t5.mrg-b5>a";
    String overlayNone = "div.foot-bnr[style*='display :none'] a.close>span";
    for (int i = 0; i < 5; i++) {
      String finshSele = "div.finish-area";
      // otukare!
      if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
        break;
      }
      int ran = Utille.getIntRand(2);
      List<WebElement> elems = driver.findElements(By.cssSelector(selector));
      if (isExistEle(elems, ran)) {
        Utille.scrolledPage(driver, elems.get(ran));
        clickSleepSelector(elems, ran, 2000);
        for (int g = 0; g < 10; g++) {
          if (isExistEle(driver, sele2top + sele2bot)
              && !isExistEle(driver, sele2top + sele2none + sele2bot, false)) {
            break;
          }
          Utille.sleep(1000);
          if (g == 9) {
            clickSleepSelector(driver.findElements(By.cssSelector(selector)), ran, 2000);
          }
        }
        if (isExistEle(driver, sele2top + sele2bot)) {
          clickSleepSelector(driver, sele2top + sele2bot, 3000);
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, overlayNone, false)) {
            checkOverlay(driver, footBnrSelector);
            Utille.sleep(3000);
          }
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4500);
            waitTilReady(driver);
            if (isExistEle(driver, footBnrSelector)
                && !isExistEle(driver, overlayNone, false)) {
              checkOverlay(driver, footBnrSelector);
            }
          }
        }
      }
    }
  }
}
