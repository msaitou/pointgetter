package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerAdserver extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerAdserver(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("-[" + this.getClass().getName() + "]-");
    String radioSele = "label.radiolabel", //
        checkboxSele = "label.checkbox", //　
        overLay = "div#center-frame>img", //　広告
        noSele = "h2>span.q_number", // タイトル
        titleSele = "h2>span.q_text", // close
        closeSele = "div.question_btn>input[name='submit']";
    clickSleepSelector(driver, startSele, 4000);
    Utille.sleep(4000);
    for (int k = 1; k <= 20; k++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "", qNo = "";
      if (isExistEle(driver, noSele)) {
        qNo = driver.findElement(By.cssSelector(noSele)).getText();
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qNo + " " + qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        } else if (isExistEle(driver, checkboxSele)) { // チェックボックス
          choiceSele = checkboxSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)
            || checkboxSele.equals(choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別を教えてください") >= 0) {
            choiceNum = 0; // 1：男
          } else if (qTitle.indexOf("あなたの年齢を教えてください") >= 0) {
            choiceNum = 3; // 4：30代
          } else if (qTitle.indexOf("あなたの職業を教えてください") >= 0) {
            choiceNum = 2; // 3：会社員
          } else if (qTitle.indexOf("あなたのお住まいの都道府県を教えてください") >= 0) {
            choiceNum = 2; // 3：関東
          } else {
            if (checkboxSele.equals(choiceSele)) {
              choiceies--;  // チェックボックスは最後の選択肢を除く
            }
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3000);
            if (isExistEle(driver, startSele)) {
              clickSleepSelector(driver, startSele, 6000);
            }
          }
        }
      } else {
        break;
      }
    }
    Utille.sleep(5000);
    // [style*='visibility: visible']
    checkOverlay(driver, overLay, false);
    if (!isExistEle(driver, "div#fade-layer[style*='display: none']", false)) {
      checkOverlay(driver, overLay, false);
    }
//    if (isExistEle(driver, closeSele)) {
//      clickSleepSelector(driver, closeSele, 4000);
//    } else {
      driver.close();
//    }
    driver.switchTo().window(wid);
  }
}
