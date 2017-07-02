package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerResearcgEcEnq extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerResearcgEcEnq(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "table.answer_table_block label", //
        matrixTableSele = "table.matrix tr",
        matrixRadioSele = "input[type='radio']",
        nextSele = "div.button_block>input",
        checkboxSele = "label.checkbox", //　
        noSele = "div.question_name", // タイトル
        titleSele = "div.question"; // close
    clickSleepSelector(driver, startSele, 4000);
    Utille.sleep(4000);
    for (int k = 1; k <= 10; k++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "", qNo = "";
      if (isExistEle(driver, noSele)) {
        qNo = driver.findElement(By.cssSelector(noSele)).getText();
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qNo + " " + qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        }
        else if (isExistEle(driver, checkboxSele)) { // チェックボックス
          choiceSele = checkboxSele;
        }
        else if (isExistEle(driver, matrixTableSele)) { // チェックボックス
          choiceSele = matrixTableSele;
        }

        // 回答選択
        if (radioSele.equals(choiceSele)
            || checkboxSele.equals(choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("参加したい") >= 0) {
            choiceNum = 1; // 3：関東
          }
          else {
            if (checkboxSele.equals(choiceSele)) {
              choiceies--; // チェックボックスは最後の選択肢を除く
            }
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(eleList2.get(choiceNum), 3000);
            if (isExistEle(driver, nextSele)) {
              clickSleepSelector(driver, nextSele, 6000);
              String t = driver.findElement(By.cssSelector(nextSele)).getAttribute("value");
              if (t.indexOf("完了") >= 0) {
                // アラートをけして
                checkAndAcceptAlert(driver);
              }
            }
          }
        }
        else if (matrixTableSele.equals(choiceSele)) {
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(matrixTableSele));
          for (WebElement wE : eleList2) {
            if (isExistEle(wE, matrixRadioSele, false)) {
              List<WebElement> eleList3 = wE.findElements(By.cssSelector(matrixRadioSele));
              int choiceies = getSelectorSize(wE, matrixRadioSele);
              choiceNum = Utille.getIntRand(choiceies);
              // 選択
              clickSleepSelector(eleList3, choiceNum, 500);
            }
          }
          if (isExistEle(driver, nextSele)) {
            clickSleepSelector(driver, nextSele, 3000);
            String t = driver.findElement(By.cssSelector(nextSele)).getAttribute("value");
            if (t.indexOf("完了") >= 0) {
              // アラートをけして
              checkAndAcceptAlert(driver);
            }
          }
        }
      }
      else {
        break;
      }
    }
    Utille.sleep(5000);
    driver.close();
    driver.switchTo().window(wid);
  }
}
