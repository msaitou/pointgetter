package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerSouSenkyo extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerSouSenkyo(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "label.radio", //
        voteSele = "input.button--answer", //
        nextSele = "a.button--next", //
        titleSele = "div.question>p", // close
        a = "";
    for (int k = 1; k <= 40; k++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "";
      if (isExistEle(driver, titleSele)) {
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info("[" + qTitle + "]");

        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          choiceNum = Utille.getIntRand(choiceies);
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(driver, eleList2.get(choiceNum), 3000);
            if (isExistEle(driver, voteSele)) {
              clickSleepSelector(driver, voteSele, 7000);
              if (isExistEle(driver, voteSele)) {
                waitTilReady(driver);
                clickSleepSelector(driver, voteSele, 6000);
              }
              else if (isExistEle(driver, nextSele)) {
                waitTilReady(driver);
                clickSleepSelector(driver, nextSele, 6000);
              }
            }
          }
        }
      }
      else {
        break;
      }
    }
//    Utille.sleep(5000);
//    driver.close();
//    driver.switchTo().window(wid);
  }
}
