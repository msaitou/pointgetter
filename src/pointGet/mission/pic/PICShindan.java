package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.parts.AnswerShindan;

/**
 * @author saitou
 *
 */
public class PICShindan extends PICBase {
  final String url = "http://pointi.jp/contents/research/";
  AnswerShindan Shindan = null;

  /**
   * @param logg
   */
  public PICShindan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "毎日診断");
    Shindan = new AnswerShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "img[src='./web_diagnose/img/top.png']";
    String sele0 = "div.entry", // 
    sele1 = "div[class='thumbnail'] h3.entrytitle>a", // クラスを完全一致にするのは済の場合クラスが追加されるため
    sumiSelector = "img[src='/images/icons/sumi.png']";

    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 8000); // 遷移
      changeCloseWindow(driver);
      while (isExistEle(driver, sele0)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(sele0));
        int size1 = eleList.size(), zumiCnt = 0;
        WebElement wEle = null;
        for (int i = 0; i < size1; i++) {
          if (isExistEle(eleList, i)) {
            if (isExistEle(eleList.get(i), sumiSelector)) {
              if (++zumiCnt > 3) { // 新規ミッション追加時はコメント
                break;
              }
              continue;
            }
            wEle = eleList.get(i);
            break;
          }
        }
        if (wEle == null
            || zumiCnt > 3) {// 新規ミッション追加時はコメント
          logg.warn(mName + "]all済み");
          break;
        }
        if (isExistEle(wEle, sele1)) {
          clickSleepSelector(wEle, sele1, 4000); // 遷移
          waitTilReady(driver);
          Shindan.answer(driver, "", null);
          //          selector = "a.submit-btn";// 次へ
          //          if (isExistEle(driver, selector)) {
          //            clickSleepSelector(driver, selector, 5500); // 遷移
          //            if (isExistEle(driver, selector)) {
          //              clickSleepSelector(driver, selector, 5000); // 遷移
          //              this.waitTilReady(driver);
          //
          //              if (isExistEle(driver, "div[data-qid]")) {
          //                int qSize = getSelectorSize(driver, "div[data-qid]"); // 選択肢の数を数える
          //                for (int i = 0; i < qSize; i++) {
          //                  selector = "div[data-qid][class=''] label";
          //                  if (isExistEle(driver, selector)) {
          //                    int size = getSelectorSize(driver, selector); // 選択肢の数を数える
          //                    int ran1 = Utille.getIntRand(size);
          //                    if (isExistEle(driver.findElements(By.cssSelector(selector)).get(ran1))) {
          //                      driver.findElements(By.cssSelector(selector)).get(ran1).click(); // 選択
          //                      Utille.sleep(2000);
          //
          //                      // end-btn が出たら終了
          //                      String none = "[style*='display: none']";
          //                      String nextSelector = "div.actionBar>a.next-btn";
          //                      String endSelector = "div.actionBar>a.end-btn";
          //                      if (isExistEle(driver, nextSelector)
          //                          && isExistEle(driver, endSelector + none, false)) {
          //                        clickSleepSelector(driver, nextSelector, 2000); // 遷移
          //                      }
          //                      else if (isExistEle(driver, endSelector)
          //                          && isExistEle(driver, nextSelector + none, false)) {
          //                        this.waitTilReady(driver);
          //                        clickSleepSelector(driver, endSelector, 4000); // 遷移
          //                        // 抜けたら
          //                        // span#end-btn-area>a.end-btn
          //                        // をクリック
          //                        selector = "span#end-btn-area>a.end-btn";
          //                        if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
          //                          clickSleepSelector(driver, selector, 2000); // 遷移
          //                          // アラートをけして
          //                          checkAndAcceptAlert(driver);
          //                          // overlayを消して
          //                          checkOverlay(driver, "div#overlay p#close>i");
          //
          //                          selector = "div.col-xs-12 a.btn-warning";
          //                          if (!isExistEle(driver, selector)) {
          //                            break;
          //                          }
          //                          String returnUrl = driver.findElement(By.cssSelector(selector))
          //                              .getAttribute("href");
          //                          logg.info("returnUrl:" + returnUrl);
          //                          driver.get(returnUrl);
          //                          Utille.sleep(3000);
          //                        }
          //                      }
          //                    }
          //                  }
          //                }
          //              }
          //            }
          //          }
        }
        else {
          logg.warn(mName + "]all済み");
          break;
        }
      }
    }
  }
}
