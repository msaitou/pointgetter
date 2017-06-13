package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerInfopanel extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerInfopanel(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String radioSele = "div.answer>input[type='radio']", // らじをセレクター
        checkboxSele = "div.answer>input[type='checkbox']", // チェックぽっくすセレクター
        //        noSele = "span.query-num",
        titleSele = "h2.question", // 質問NOも含む　問セレクター
        seleSub = "div.btn_next>input[type='submit']", //
        finishSele = "div.btn_2next>input[type='button']", // 終了ボタンセレクタ
        closeSele = "input.btn_close_en";

    clickSleepSelector(driver, startSele, 5000);

    driver.switchTo().frame(0);
    Utille.sleep(2000);
    if (isExistEle(driver, seleSub)) {
      clickSleepSelector(driver, seleSub, 5000);
      for (int k = 1; k <= 18; k++) {
        int choiceNum = 0;
        String qTitle = "", choiceSele = "";
        if (isExistEle(driver, titleSele)) {
          qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qTitle);
          if (isExistEle(driver, radioSele)) { // ラジオ
            choiceSele = radioSele;
          } else if (isExistEle(driver, checkboxSele)) { // チェックぼっくす
            choiceSele = checkboxSele;
          }

          // 回答選択
          if (radioSele.equals(choiceSele)
              || checkboxSele.equals(choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            if (qTitle.indexOf("あなたの性別") >= 0) {
              choiceNum = 0; // 1：男
            } else if (qTitle.indexOf("あなたの年齢を") >= 0) {
              choiceNum = 2; // 2：30代
            } else if (qTitle.indexOf("あなたのご職業") >= 0) {
              choiceNum = 2; // 2：会社員
            } else if (qTitle.indexOf("あなたのお住まい") >= 0) {
              choiceNum = 2; // 2：関東
            } else {
              if (checkboxSele.equals(choiceSele)) {
                choiceies--; // チェックボックスは最後の選択肢を除く
              }
              choiceNum = Utille.getIntRand(choiceies);
            }
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
              if (isExistEle(driver, seleSub)) {
                clickSleepSelector(driver, seleSub, 6000);
              }
            }
          }
        } else {
          break;
        }
      }
    }
    if (isExistEle(driver, finishSele)) {
      String wid2 = driver.getWindowHandle();
      clickSleepSelector(driver, finishSele, 4000);
      driver.close();
      changeWindow(driver, wid2);
//      if (isExistEle(driver, closeSele)) {
//        clickSleepSelector(driver, closeSele, 4000);
//        driver.switchTo().window(wid);
//      }
      driver.close();
      driver.switchTo().window(wid);
    }
  }
}
