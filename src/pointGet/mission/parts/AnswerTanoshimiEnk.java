package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerTanoshimiEnk extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerTanoshimiEnk(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public boolean answer(WebDriver driver, String startSele, String wid) {
    logg.info("####[" + this.getClass().getName() + "]####");

    String seleNext = "form[name='form1']>input.btn";
    if (isExistEle(driver, seleNext)) {
      clickSleepSelector(driver, seleNext, 4000);
      // 以下より10問
      String choiceSele = "label.adchange";
      String nextSele2 = "p.btm>input.btn";
      for (int i = 0; i < 10; i++) {
        List<WebElement> eleChoice = driver
            .findElements(By.cssSelector(choiceSele));
        int choiceies = eleChoice.size();
        int choiceNum = Utille.getIntRand(choiceies);
        if (isExistEle(eleChoice, choiceNum)) {
          clickSleepSelector(eleChoice, choiceNum, 3000); // 選択肢を選択
          if (isExistEle(driver, nextSele2)) {
            clickSleepSelector(driver, nextSele2, 3000);
            if (isExistEle(driver, nextSele2)) { // 答え合わせ
              clickSleepSelector(driver, nextSele2, 4000); // 次の問題
            }
          }
        }
      }

      String overLaySele = "div#mdl_area[style='display: block;'] div#mdl_close";
      // overlayを消して
      checkOverlay(driver, overLaySele);
      if (isExistEle(driver, seleNext)) {
        clickSleepSelector(driver, seleNext, 4000);
      }
    }
    driver.close();
    driver.switchTo().window(wid);
    return true;
  }
}
