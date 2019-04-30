package pointGet.mission.lfm.old;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.lfm.LFMBase;

/**
 * @author saitou
 * 4時更新
 */
public class LFMChyosatai extends LFMBase {
  final String url = "http://lifemedia.jp/game/";
  WebDriver driver = null;

  /**
   * @param log
   */
  public LFMChyosatai(Logger log, Map<String, String> cProps) {
    super(log, cProps, "トキメキ調査隊");
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
    String footBnrSelector = "div.foot-bnr a.close>span", //
        footBnrNoneSele = "div.foot-bnr[style*='display :none'] a.close>span",
        sele = "div>a>img[alt='トキメキ調査隊']";
    //    if (isExistEle(driver, selector)) {
    //      clickSleepSelector(driver, selector, 4000); // 遷移
    //      changeCloseWindow(driver);
    for (int j = 0; j < 6; j++) {
      Utille.url(driver, url, logg);
      //        selector = "p.tokimeki";
      String selector = sele;
      if (isExistEle(driver, selector, false)) {
        clickSleepSelector(driver, selector, 4000); // 遷移
        changeCloseWindow(driver);
        checkOverlay(driver, overlaySelector);
        if (isExistEle(driver, footBnrSelector)
            && !isExistEle(driver, footBnrNoneSele, false)) {
          checkOverlay(driver, footBnrSelector);
        }

        String noEntrySele = "div.thumbnail span.icon-noentry";
        String entrySele = "div.thumbnail span.icon-entry";
        if (isExistEle(driver, noEntrySele)) {
          clickSleepSelector(driver, noEntrySele, 3000); // 遷移
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, footBnrNoneSele, false)) {
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
            selector = "span.icon-arrow";
            for (int i = 0; i < 10; i++) {
              int ran = Utille.getIntRand(2);
              if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
                checkOverlay(driver, overlaySelector);
                if (isExistEle(driver, footBnrSelector)
                    && !isExistEle(driver, footBnrNoneSele, false)) {
                  checkOverlay(driver, footBnrSelector);
                }
              }
            }
          }
        }
        else if (isExistEle(driver, entrySele)) {
          clickSleepSelector(driver, entrySele, 3000); // 遷移
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, footBnrNoneSele, false)) {
            checkOverlay(driver, footBnrSelector);
          }
          //          selector = "div.btn>a";
          selector = "span.icon-arrow";
          for (int i = 0; i < 10; i++) {
            String finshSele = "div.finish-area";
            // otukare!
            if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
              break;
            }
            int ran = Utille.getIntRand(2);
            Utille.sleep(2000);
            if (isExistEle(driver.findElements(By.cssSelector(selector)))
                && isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
              clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
            }
          }
        }
        else {
          j = 6;
        }
      }
    }
    String exchangeSele = "span.btn-exchange>a", //
        exchageListSele = "select[name='point']",
        doExchangeSele = "div.btn-area>button[type='submit']";
    ;
    Utille.url(driver, url, logg);
    if (isExistEle(driver, sele)) {
      clickSleepSelector(driver, sele, 4000); // 遷移
      changeCloseWindow(driver);
      checkOverlay(driver, overlaySelector);
      if (isExistEle(driver, footBnrSelector)
          && !isExistEle(driver, footBnrNoneSele, false)) {
        checkOverlay(driver, footBnrSelector);
      }
      if (isExistEle(driver, exchangeSele)) {
        clickSleepSelector(driver, exchangeSele, 6000);
        if (isExistEle(driver, exchageListSele)) {
          int size = getSelectorSize(driver, exchageListSele + ">option");
          String value = driver.findElements(By.cssSelector(exchageListSele + ">option"))
              .get(size - 1).getAttribute("value");
          Select selectList = new Select(driver.findElement(By.cssSelector(exchageListSele)));
          selectList.selectByValue(value); // 交換ポイントを選択
          Utille.sleep(3000);
          for (int i = 0; i < 2; i++) {
            if (isExistEle(driver, doExchangeSele)) {
              clickSleepSelector(driver, doExchangeSele, 6000); // i=1 交換する　i=2 本当に
            }
          }
        }
      }
    }
  }
}
