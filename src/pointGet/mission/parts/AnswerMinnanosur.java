package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerMinnanosur extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerMinnanosur(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "label.radio_label", //
        titleSele = "p.question-count", // 質問NO
        seleNext = "button.next-btn", //
		seleNext2 = "a.next-button",
        finishSele = "button.next-btn[type='submit']", //
        closeSele = "input.btn_close_en";//

    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 2000); // 遷移　問開始
      for (int g = 0; g < 13 + 2; g++) {
        int choiceNum = 0;
        String qTitle = "", choiceSele = "";
        if (isExistEle(driver, titleSele)) {
          qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qTitle);
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
              clickSleepSelector(driver, eleList2, choiceNum, 3000);
              if (isExistEle(driver, seleNext)) {
                clickSleepSelector(driver, seleNext, 3000);
                if (isExistEle(driver, seleNext)) {
                  clickSleepSelector(driver, seleNext, 3000);
                }
                else if (isExistEle(driver, seleNext2)) {
                    clickSleepSelector(driver, seleNext2, 3000);
                    if (isExistEle(driver, seleNext)) {
                      clickSleepSelector(driver, seleNext, 3000);
                    }
                }
              }
            }
          }
        } else {
          break;
        }
      }
      if (isExistEle(driver, seleNext)) {
        clickSleepSelector(driver, seleNext, 3000);
        if (isExistEle(driver, seleNext)) {
          clickSleepSelector(driver, seleNext, 3000);
        }
      }
      driver.close();
      driver.switchTo().window(wid);
    }
  }
}
