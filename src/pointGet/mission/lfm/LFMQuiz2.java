package pointGet.mission.lfm;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;

/**
 *
 * @author saitou
 */
public class LFMQuiz2 extends LFMBase {
  final String url = "http://lifemedia.jp/game/";

  /**
   * @param logg
   */
  public LFMQuiz2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "クイズ2");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div>img[alt='デイリークイズ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelectorNoRefre(driver, selector, 3000); // 遷移 全体へ
      changeCloseWindow(driver);
      checkOverlay(driver, "div.overlay-popup a.button-close");
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
              clickSleepSelectorNoRefre(driver, elems, ii, 3000); // 遷移

              if (isExistEle(driver, exchangeListSele)) {
                int size = getSelectorSize(driver, exchangeListSele + ">option");
                String value = driver.findElements(By.cssSelector(exchangeListSele + ">option"))
                    .get(size - 1).getAttribute("value");
                Select selectList = new Select(driver.findElement(By.cssSelector(exchangeListSele)));
                selectList.selectByValue(value); // 交換ポイントを選択
                Utille.sleep(3000);
                if (isExistEle(driver, doExchangeSele)) {
                  clickSleepSelectorNoRefre(driver, doExchangeSele, 4000); // i=1 交換する　i=2 本当に
                }
              }
              // Topへ戻る
              if (isExistEle(driver, returnTopSele)) {
                clickSleepSelectorNoRefre(driver, returnTopSele, 4000);
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
      String noSele = "div.ui-item-no", titleSele = "h2.ui-item-title";
      selector = "form>input[name='submit']";
      Utille.sleep(4000);
      if (isExistEle(driver, selector)) {
        clickSelectorNoRefre(driver, selector);
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
              } else if (ran == 1) {
                selectId += "2']";
              } else if (ran == 2) {
                selectId += "3']";
              } else {
                selectId += "4']";
              }
              // 8kai roop
              String selector2 = "input[name='submit']";
              if (isExistEle(driver, selectId)) {
                clickSleepSelectorNoRefre(driver, selectId, 4000); // 遷移
                int ranSleep = Utille.getIntRand(9);
                Utille.sleep(ranSleep * 1000);
                //							waitTilReady(driver);
                if (isExistEle(driver, selector2)) {
                  //								waitTilReady(driver);
                  clickSleepSelectorNoRefre(driver, selector2, 4000); // 遷移
                  checkOverlay(driver, "div.overlay-popup a.button-close");
                  if (isExistEle(driver, selector2)) {
                    clickSleepSelectorNoRefre(driver, selector2, 3000); // 遷移
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
          clickSleepSelectorNoRefre(driver, selector, 3000);
          //					waitTilReady(driver);
        }
      } else {
        logg.warn(this.mName + "]獲得済み");
      }
    }
  }
}
