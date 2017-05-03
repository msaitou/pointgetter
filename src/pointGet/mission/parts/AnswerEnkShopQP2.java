package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerEnkShopQP2 extends MissCommon {
  final String url = "http://www.gendama.jp/survey/mini";

  //  WebDriver driver = null;

  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerEnkShopQP2(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("-[" + this.getClass().getName() + "]-");
    // 回答開始
    clickSleepSelector(driver, startSele, 3000);
    String qTitleSele = "div.question-label", // タイトルセレクター
        radioSele = "label.item-radio", // ラジオセレクター
        checkboxSele = "label.item-checkbox", // チェックボックスセレクター
        seleSele = "select.mdl-textfield__input"; // ドロップダウンセレクター
    for (int k = 1; k <= 18; k++) {
      int choiceNum = 0;
      String qTitle = "", choiceSele = "";
      if (isExistEle(driver, qTitleSele)) {
        qTitle = driver.findElement(By.cssSelector(qTitleSele)).getText();
        logg.info("[" + qTitle + "]");
      }
      else {
        break;
      }
      if (isExistEle(driver, radioSele)) { // ラジオ
        choiceSele = radioSele;
      }
      else if (isExistEle(driver, checkboxSele)) { // チェックボックス
        choiceSele = checkboxSele;
      }
      else if (isExistEle(driver, seleSele)) {// セレクトボックス
        choiceSele = seleSele;
      }
      if (radioSele.equals(choiceSele)
          || checkboxSele.equals(choiceSele)) {
        int choiceies = getSelectorSize(driver, choiceSele);
        if (qTitle.indexOf("あなたの性別を") >= 0) {
          // 1：男
        }
        else if (qTitle.indexOf("あなたの年齢を") >= 0) {
          if (choiceies > 3) {// 一応選択可能な範囲かをチェック
            choiceNum = 3;
          }
        }
        else {
          choiceNum = Utille.getIntRand(choiceies);
        }
        List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
        if (isExistEle(eleList2, choiceNum)) {
          // 選択
          clickSleepSelector(eleList2.get(choiceNum), 2500);
        }
      }
      else if (seleSele.equals(choiceSele)) {
        Utille.sleep(2000);
        int size3 = getSelectorSize(driver, seleSele + ">option");
        String value = "";
        if (qTitle.indexOf("住まい") >= 0) {
          value = "14";
        }
        else {
          choiceNum = Utille.getIntRand(size3);
          value = driver.findElements(By.cssSelector(seleSele + ">option"))
              .get(choiceNum).getAttribute("value");
        }
        Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
        selectList.selectByValue(value);
        Utille.sleep(3000);
      }
      if (isExistEle(driver, startSele)) {
        // 次へ
        clickSleepSelector(driver, startSele, 4000);
      }
    }
    Utille.sleep(3000);
    if (isExistEle(driver, startSele)) {
      // 次へ
      clickSleepSelector(driver, startSele, 4000);
    }
    // close
    String closeSele = "input.btn_close_en";
    if (isExistEle(driver, closeSele)) {
      clickSleepSelector(driver, closeSele, 4000);
      driver.switchTo().window(wid);
      driver.navigate().refresh();
    }
  }
}
