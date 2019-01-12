package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerNaruhodo extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerNaruhodo(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    String closeSele = "div#popup-ad button.popup-close", //
    closeSeleNone = "div#popup-ad[style*='display: none;'] button.popup-close", //
    titleSele = "p.question", //
    noSele = "div#title span.number", //

    radioSele = "div#answer-select img", //
    resultSele = "div#result",//

    voteSele = "input.button--answer", //
    nextSele = "a.button--next", //
    getSele = "a.button--get", //
    a = "";
    // 最初
    for (int i = 0; i < 2; i++) {
      if (driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed() && isExistEle(driver, closeSele)) {
        logg.info("div#popup-ad :"+driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed());
        checkOverlay(driver, closeSele, false);
      }
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 4000);
      }
    }

    // 問題
    for (int i = 0; i < 20; i++) {
      if (driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed() && isExistEle(driver, closeSele)) {
        logg.info("div#popup-ad :"+driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed());
        checkOverlay(driver, closeSele, false);
      }
      if (isExistEle(driver, resultSele)) {
        if (isExistEle(driver, startSele)) {
          clickSleepSelector(driver, startSele, 4000);
        }
        break;
      }
      if (isExistEle(driver, titleSele) && isExistEle(driver, noSele)) {
        logg.info(noSele + " : " + titleSele);
        if (isExistEle(driver, radioSele)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(radioSele));
          int choiceNum = Utille.getIntRand(eleList.size());
          if (isExistEle(eleList, choiceNum)) { // 選択
            clickSleepSelector(driver, eleList.get(choiceNum), 3000);
            if (isExistEle(driver, startSele)) {  // 応える
              clickSleepSelector(driver, startSele, 5000);
              if (driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed() && isExistEle(driver, closeSele)) {
                logg.info("div#popup-ad :"+driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed());
                checkOverlay(driver, closeSele, false);
              }
              if (isExistEle(driver, startSele)) {  // 結果
                clickSleepSelector(driver, startSele, 6000);
              }
            }
          }
        }
      }
    }

    for (int i = 0; i < 3; i++) {
      if (driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed() && isExistEle(driver, closeSele)) {
        logg.info("div#popup-ad :"+driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed());
        checkOverlay(driver, closeSele, false);
      }
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 4000);
      }
    }
  }
}
