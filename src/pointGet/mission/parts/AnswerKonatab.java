package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerKonatab extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerKonatab(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "input.QAbutton[type='RADIO']", //
    titleSele = "p.QAformp>strong", // 質問NOも含む
    seleNext = "button.next-btn", //
    checkSele = "input.QAbutton[type='CHECKBOX']", //
    overLay = "div#lm_overlay>a.modal_close", //
    overLayNone = "div#lm_overlay[style*='display: none']>a.modal_close", //
    finishSele = "button.next-btn[type='submit']", //
    closeSele = "input.btn_close_en", //

    a = "";//
    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    for (int g = 0; g < 12 + 2; g++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "";
      if (!isExistEle(driver, overLayNone, false)) {
        checkOverlay(driver, overLay);
      }
      if (isExistEle(driver, titleSele)) {
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        else if (isExistEle(driver, checkSele)) {
          choiceSele = checkSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)
            || checkSele.equals(choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (radioSele.equals(checkSele)) {
            choiceies--;
          }
          choiceNum = Utille.getIntRand(choiceies);
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(driver, eleList2, choiceNum, 4000);
            if (isExistEle(driver, startSele)) {
              clickSleepSelector(driver, startSele, 3000);
            }
          }
        }
      }
      else {
        break;
      }
    }
    driver.close();
    driver.switchTo().window(wid);
  }
}
