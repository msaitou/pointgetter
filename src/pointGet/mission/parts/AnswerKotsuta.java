package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerKotsuta extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerKotsuta(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "td.choice-table-input>input.radio", // ラジオセレクター
        seleSele = "select", // ドロップダウンセレクター
        noSele = "span.query-num", // 問番セレクター
        titleSele = "span.query-text", // 問セレクター
        closeSele = "input.btn_close_en"; // 閉じるボタンセレクター
    clickSleepSelector(driver, startSele, 4000);
    for (int k = 1; k <= 18; k++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "", qNo = "";
      if (isExistEle(driver, noSele)) {
        qNo = driver.findElement(By.cssSelector(noSele)).getText();
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qNo + " " + qTitle);
        if (isExistEle(driver, radioSele)) { // ラジオ
          choiceSele = radioSele;
        } else if (isExistEle(driver, seleSele)) { // セレクト
          choiceSele = seleSele;
        }
        // 回答選択
        if (radioSele.equals(choiceSele)
        //        || checkboxSele.equals(choiceSele)
        ) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別") >= 0) {
            choiceNum = 0; // 1：男
          } else if (qTitle.indexOf("あなたの年齢をお知らせください") >= 0) {
            choiceNum = 3; // 4：30代
          } else if (qTitle.indexOf("あなたの職業") >= 0) {
            choiceNum = 2; // 3：会社員
          } else if (qTitle.indexOf("あなたのお住まいの都道府県を教えてください") >= 0) {
            choiceNum = 2; // 3：関東
          } else {
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
        } else if (seleSele.equals(choiceSele)) {
          Utille.sleep(2000);
          int size3 = getSelectorSize(driver, choiceSele + ">option");
          String value = "";
          if (qTitle.indexOf("あなたのお住まいを") >= 0) {
            value = "13";
          } else {
            choiceNum = Utille.getIntRand(size3);
            value = driver.findElements(By.cssSelector(choiceSele + ">option"))
                .get(choiceNum).getAttribute("value");
          }
          Select selectList = new Select(driver.findElement(By.cssSelector(choiceSele)));
          selectList.selectByValue(value);
          Utille.sleep(3000);
          if (isExistEle(driver, startSele)) {
            clickSleepSelector(driver, startSele, 6000);
          }
        }
      } else {
        break;
      }
    }
    while (true) {
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 5000);
      } else {
        break;
      }
    }
    Utille.sleep(5000);
    // close
//    if (isExistEle(driver, closeSele)) {
//      clickSleepSelector(driver, closeSele, 4000);
//    } else {
      driver.close();
//    }
    driver.switchTo().window(wid);
  }
}
