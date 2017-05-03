package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerShindan extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerShindan(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("####[" + this.getClass().getName() + "]####");
    String beginSele = "a.submit-btn", // 次へ
        sele = "div[data-qid][class=''] label", //
        seleFinish = "div.col-xs-12>a.btn-danger", //
        closeSele = "input.btn_close_en", //
        none = "[style*='display: none']", //
        nextSelector = "div.actionBar>a.next-btn", //
        endSelector = "div.actionBar>a.end-btn";//
    if (isExistEle(driver, beginSele)) {
      clickSleepSelector(driver, beginSele, 2000); // 遷移
      if (isExistEle(driver, beginSele)) {
        clickSleepSelector(driver, beginSele, 2000); // 遷移
        if (isExistEle(driver, "div[data-qid]")) {
          int qSize = getSelectorSize(driver, "div[data-qid]"); // 問題の数を数える
          for (int i = 0; i < qSize + 5; i++) {
            if (isExistEle(driver, sele)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(sele));
              int size = eleList.size(),// 選択肢の数を数える
              ran1 = Utille.getIntRand(size);
              if (isExistEle(eleList, ran1)) {
                clickSleepSelector(eleList, ran1, 4000); // 選択
                // end-btn が出たら終了
                if (isExistEle(driver, nextSelector)
                    && isExistEle(driver, endSelector + none, false)) {
                  clickSleepSelector(driver, nextSelector, 2000); // 遷移
                } else if (isExistEle(driver, endSelector)
                    && isExistEle(driver, nextSelector + none, false)) {
                  clickSleepSelector(driver, endSelector, 4000); // 遷移
                  logg.info("neukata?");
                  if (isExistEle(driver, seleFinish)) {
                    clickSleepSelector(driver, seleFinish, 2000); // 遷移
                  }
//                  if (isExistEle(driver, closeSele)) {
//                    clickSleepSelector(driver, closeSele, 2000);
//                  }
                  driver.close();
                  driver.switchTo().window(wid);
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
