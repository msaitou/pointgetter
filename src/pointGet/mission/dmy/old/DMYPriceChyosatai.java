package pointGet.mission.dmy.old;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.dmy.DMYBase;

/**
 * @author saitou
 * 4時更新
 */
public class DMYPriceChyosatai extends DMYBase {
  final String url = "https://d-money.jp/mall";
  private String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
  private String footBnrSelector = "div.foot-bnr a.close>span";

  /**
   * @param log
   */
  public DMYPriceChyosatai(Logger log, Map<String, String> cProps) {
    super(log, cProps, "Price調査隊");
  }

  @Override
  public void privateMission(WebDriver driver) {
    String overlayNone = "div.foot-bnr[style*='display :none'] a.close>span";
    for (int j = 0; j < 6; j++) {
        Utille.url(driver, url, logg);
//      Utille.url(driver, url, logg);
      selector = "i.c-dmoney_icon_21_reward";
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 3000);
      }
      selector = "img[src*='price.png']";
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 5000);
        changeCloseWindow(driver);
      }
      checkOverlay(driver, overlaySelector);
      if (isExistEle(driver, footBnrSelector)
          && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
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
    String exchangeSele = "span.btn-exchange>a", //
    exchageListSele = "select[name='point']", //
    doExchangeSele = "div.btn-area>button[type='submit']", //
    sele = "li.js_tabPrice a.start-search";
//    Utille.url(driver, url, logg);
    Utille.url(driver, url, logg);
    selector = "i.c-dmoney_icon_21_reward";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 3000);
    }
    selector = "img[src*='price.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000);
      String wid = driver.getWindowHandle();
      changeCloseWindow(driver);
    }
    checkOverlay(driver, overlaySelector);
    if (isExistEle(driver, footBnrSelector)
        && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
      checkOverlay(driver, footBnrSelector);
    }
//    Utille.sleep(30000);
//    if (isExistEle(driver, sele)) {
//      clickSleepSelector(driver, sele, 4000); // 遷移
//      changeCloseWindow(driver);
      if (isExistEle(driver, exchangeSele)) {
          if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
                checkOverlay(driver, footBnrSelector);
              }
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
                if (isExistEle(driver, footBnrSelector)
            	        && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
            	      checkOverlay(driver, footBnrSelector);
            	    }
              clickSleepSelector(driver, doExchangeSele, 6000); // i=1 交換する　i=2 本当に
            }
          }
        }
//      }
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
        clickSleepSelector(driver, elems, ran, 2000);
        for (int g = 0; g < 10; g++) {
          if (isExistEle(driver, sele2top + sele2bot)
              && !isExistEle(driver, sele2top + sele2none + sele2bot, false)) {
            break;
          }
          Utille.sleep(1000);
          if (g == 9) {
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 2000);
          }
        }
        if (isExistEle(driver, sele2top + sele2bot)) {
          clickSleepSelector(driver, sele2top + sele2bot, 3000);
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, overlayNone, false)) {
            checkOverlay(driver, footBnrSelector);
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
