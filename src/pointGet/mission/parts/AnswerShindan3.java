package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerShindan3 extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerShindan3(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String beginSele = "a.submit-btn", // 次へ
    sele = "div[data-qid][class=''] label", //
    seleFinish = "div.col-xs-12 a.btn-danger", //
    seleFinish2 = "div.col-xs-12 button.btn-danger", //
    closeSele = "input.btn_close_en", // ?
    listUrlSele = "div.col-xs-12 a.btn-warning", //
    none = "[style*='display: none']", //
    nextSelector = "div.actionBar>a.next-btn", //
    endSelector = "div.actionBar>a.end-btn";//
    if (isExistEle(driver, beginSele)) {
      clickSleepSelector(driver, beginSele, 5000); // 遷移
      if (isExistEle(driver, beginSele)) {
        waitTilReady(driver);
        clickSleepSelector(driver, beginSele, 6000); // 遷移
        if (isExistEle(driver, "div[data-qid]", false)) {
          //          if (isExistEle(driver, "div[data-qid]")) {

          int qSize = getSelectorSize(driver, "div[data-qid]"); // 問題の数を数える
          for (int i = 0; i < qSize + 5; i++) {
            if (isExistEle(driver, sele)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(sele));
              int size = eleList.size(), // 選択肢の数を数える
              ran1 = Utille.getIntRand(size);
              if (isExistEle(eleList, ran1)) {
                clickSleepSelector(driver, eleList, ran1, 4000); // 選択
                // end-btn が出たら終了
                if (isExistEle(driver, endSelector + none, false)
                    && isExistEle(driver, nextSelector)) {
                  clickSleepSelector(driver, nextSelector, 2000); // 遷移
                }
                else if (isExistEle(driver, endSelector)
                    && isExistEle(driver, nextSelector + none, false)) {
                  clickSleepSelector(driver, endSelector, 4000); // 遷移
                  logg.info("neukata?");
                  Utille.clickRecaptha(driver, logg);

                  if (isExistEle(driver, seleFinish)) {
                    clickSleepSelector(driver, seleFinish, 2000);
                  }
                  else if (isExistEle(driver, seleFinish2)) {
                    clickSleepSelector(driver, seleFinish2, 2000);
                  }
                  // アラートをけして
                  checkAndAcceptAlert(driver);
                  // このウィンドウが新しく開かれていれば閉じるし、一覧から同じウィンドウなら閉じない
                  if (null == wid) {
                    String returnUrl = driver.findElement(By.cssSelector(listUrlSele))
                        .getAttribute("href");
                    logg.info("returnUrl:" + returnUrl);
                    Utille.url(driver, returnUrl, logg);
                    Utille.sleep(3000);
                  }
                  else {
                    driver.close();
                    driver.switchTo().window(wid);
                  }
                  break;
                }
              }
            }
          }
        }
      }
    }
  }
}
