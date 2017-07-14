package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerAdsurvey extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerAdsurvey(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String choiceSele = "div.answer label", // ラジオセレクター
        seleSele = "select[name='question_4']", // ドロップダウンセレクター
        seleNext = "div.btn_next>input.btn", //
        //    radioSele = "label.radiolabel", //
        //    checkboxSele = "label.checkbox", //　
        //    overLay = "div#center-frame>img", //　広告
        //    noSele = "h2>span.q_number", // タイトル
        titleSele = "h2.question", // close
        //    closeSele = "div.question_btn>input[name='submit']", //
        a = "";
    driver.switchTo().frame(0);
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 4000);
    }
    //    Utille.sleep(4000);
    // 12問?
    for (int k = 1; k <= 15; k++) {
      if (isExistEle(driver, titleSele)) {
        String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qTitle);
        int choiceNum = 0;
        if (isExistEle(driver, choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別を") >= 0
              || qTitle.indexOf("未既婚を") >= 0) {
            choiceNum = 0; // 1：男 // 1: 未婚
          }
          else if (qTitle.indexOf("あなたの年齢を") >= 0
              || qTitle.indexOf("職業を") >= 0) {
            choiceNum = 2; // 2：30代 // 2：会社員
          }
          else {
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            Utille.scrolledPage(driver, eleList2.get(choiceNum));
            clickSleepSelector(eleList2.get(choiceNum), 3000);
          }
        }
        else if (isExistEle(driver, seleSele)) {
          Utille.sleep(4000);
          int size3 = getSelectorSize(driver, seleSele + ">option");
          String value = "";
          if (qTitle.indexOf("お住まいを") >= 0) {
            value = "34";
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
      }
      else {
        // タイトルが取得できなかったので、少し待ってみよう
        Utille.sleep(1000);
      }
      if (isExistEle(driver, seleNext)) {
        // 次へ
        clickSleepSelector(driver, seleNext, 4500);
      }
    }
    Utille.sleep(2000);
    if (isExistEle(driver, startSele)) {
      // ポイント獲得
      clickSleepSelector(driver, startSele, 5000);
    }
    driver.close();
    // 最後に格納したウインドウIDにスイッチ
    driver.switchTo().window(wid);
  }
}
