package pointGet.mission.mop;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;

public class MOPAnzan extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";

  /**
   * @author saitou 0時、12時開催
   */
  public MOPAnzan(Logger log, Map<String, String> cProps) {
    super(log, cProps, "暗算");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.game_btn>div.icon>img[alt='ANZANmental arithmetic']";
    if (isExistEle(driver, selector)) {
      clickSleepSelectorNoRefre(driver, selector, 2000); // 遷移
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
      selector = "form.fx-control>input[name='submit']";
      while (true) { // 最大２回
        Utille.sleep(4000);
        if (isExistEle(driver, selector)) {
          clickSelectorNoRefre(driver, selector);
          for (int i = 0; i < 10; i++) {
            Utille.sleep(4000);
            if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)) {
              checkOverlay(driver, "div.overlay-popup a.button-close");
            }
            String selectorExpression = "div.ui-item-header>h2.ui-item-title";
            String selectAns = "";
            if (isExistEle(driver, selectorExpression)
                && isExistEle(driver, "div.ui-item-no")) {
              String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
              String quest = driver.findElement(By.cssSelector("div.ui-item-no")).getText();
              logg.info(quest + " :" + text);
              String regex = "(\\d) ([-×+÷]) (\\d) ([-×+÷]) (\\d)";
              Pattern p = Pattern.compile(regex);
              Matcher m = p.matcher(text);
              if (m.find()) {
                String ans = Utille.calcAnzan(m);
                logg.info("答え [" + ans + "]");
                selectAns = ans;
              }
            }
//            else {
//              logg.info("not get after days");
//              return;
//            }
            if (isExistEle(driver, "ul.ui-item-body")) { // 答え要素全体
              // label.ui-label-radio.ui-circle-button[for='radio-1']
              String selectId = "label.ui-label-radio";
              String selector2 = "input[name='submit']";

              List<WebElement> eleList = driver.findElements(By.cssSelector(selectId));
              for (int j = 0; j < 4; j++) { // 4択から正解を探す
                if (isExistEle(eleList, j)) {
                  String choice = eleList.get(j).getText();
                  if (Utille.numEqual(selectAns, choice)) {
                    // if (selectAns.equals(choice)) {
                    clickSleepSelectorNoRefre(driver, eleList, j, 3000);// 選択
                    int ranSleep = Utille.getIntRand(9);
                    Utille.sleep(ranSleep * 1000);
                    waitTilReady(driver);
                    if (isExistEle(driver, selector2)) {
                      waitTilReady(driver);
                      clickSleepSelectorNoRefre(driver, selector2, 5500); // 遷移
                      String selector3 = "div.fx-control>input[name='submit']";
                      String selector4 = "form.fx-control>input[name='submit']";
                      checkOverlay(driver, "div.overlay-popup a.button-close");
                      if (isExistEle(driver, selector3)) {
                        clickSleepSelectorNoRefre(driver, selector3, 3000); // 遷移
                        checkOverlay(driver, "div.overlay-popup a.button-close");
                      }
                      else if (isExistEle(driver, selector4)) {
                        clickSleepSelectorNoRefre(driver, selector4, 5000); // 遷移
                        checkOverlay(driver, "div.overlay-popup a.button-close");
                      }
                    }
                    break;
                  }
                }
              }
            }
          }
          logg.info(this.mName + "]kuria?");
          if (isExistEle(driver, selector)) {
            clickSelectorNoRefre(driver, selector);
            if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)) {
              checkOverlay(driver, "div.overlay-popup a.button-close");
            }
          }
          //	checkOverlay(driver, "div.overlay-popup a.button-close");
        }
        else {
          String endSelector = "input[name='submit']";
          if (isExistEle(driver, endSelector)) {
            clickSleepSelectorNoRefre(driver, endSelector, 3000);
            waitTilReady(driver);
          }
          logg.warn(this.mName + "]獲得済み");
          break;
        }
      }
    }
  }
}
