package pointGet.mission.osa;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;

/**
 * @author saitou 0時、8時、16時開催
 */
public class OSAQuiz extends OSABase {
  final String url = "http://osaifu.com/contents/coinland/";

  /**
   * @param logg
   */
  public OSAQuiz(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "daily quiz");
  }

  @Override
  public void privateMission(WebDriver driver) {
    selector = "li>a>img[alt='デイリークイズ']";
    driver.get(url);
    Utille.sleep(2000);
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 2000); // 遷移
      String overlaySelector = "div.overlay.overlay-timer>div.overlay-item[style*='display: block'] a.button-close";
      checkOverlay(driver, overlaySelector);
      String exchangeSele = "a.stamp__btn", //
          exchangeListSele = "select.exchange__selection",
          doExchangeSele = "input.exchange__btn",
          returnTopSele = "a.stamp__btn.stamp__btn-return";
      Utille.sleep(2000);
      // スタンプ変換
      if (isExistEle(driver, exchangeSele)) {
        List<WebElement> elems = driver.findElements(By.cssSelector(exchangeSele));
        for (int ii = 0; ii < elems.size(); ii++) {
          if (isExistEle(elems, ii)) {
            Utille.scrolledPage(driver, elems.get(ii));
            if ("スタンプ交換".equals(elems.get(ii).getText())) {
              clickSleepSelector(elems, ii, 3000); // 遷移

              if (isExistEle(driver, exchangeListSele)) {
                int size = getSelectorSize(driver, exchangeListSele + ">option");
                String value = driver.findElements(By.cssSelector(exchangeListSele + ">option"))
                    .get(size - 1).getAttribute("value");
                Select selectList = new Select(driver.findElement(By.cssSelector(exchangeListSele)));
                selectList.selectByValue(value); // 交換ポイントを選択
                Utille.sleep(3000);
                if (isExistEle(driver, doExchangeSele)) {
                  clickSleepSelector(driver, doExchangeSele, 4000); // i=1 交換する　i=2 本当に
                }
              }
              // Topへ戻る
              if (isExistEle(driver, returnTopSele)) {
                clickSleepSelector(driver, returnTopSele, 4000);
              }
              break;
            }
          }
        }
      }
      // finish condition
      String finishSelector = "p.ui-timer";
      if (isExistEle(driver, finishSelector)) {
        finsishFlag = true;
        return;
      }
      selector = "input[name='submit']";
      String noSele = "div.ui-item-no", titleSele = "h2.ui-item-title";
      Utille.sleep(5000);
      if (isExistEle(driver, selector)) {
        clickSelector(driver, selector);
        selector = "ul.ui-item-body";
        for (int i = 0; i < 8; i++) {
          // driver.navigate().refresh();
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
                clickSleepSelector(driver, selectId, 4000); // 選択
                //							waitTilReady(driver);
                if (isExistEle(driver, selector2)) {
                  clickSleepSelector(driver, selector2, 4000); // 遷移
                  checkOverlay(driver, overlaySelector);
                  if (isExistEle(driver, selector2)) {
                    clickSleepSelector(driver, selector2, 3000); // 遷移
                    checkOverlay(driver, overlaySelector);
                  }
                }
              }
            }
          }
        }
        logg.info(this.mName + "]kuria?");
        selector = "input[name='submit']";
        if (isExistEle(driver, selector)) {
          checkOverlay(driver, overlaySelector);
          clickSleepSelector(driver, selector, 3000); // 遷移
        }
      }
    }
    else {
      logg.warn(this.mName + "]獲得済み");
    }
  }
}
