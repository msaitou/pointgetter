package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerAdEnq extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerAdEnq(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public boolean answer(WebDriver driver, String startSele, String wid) {
    logg.info("####[" + this.getClass().getName() + "]####");
    Utille.sleep(3000);
    driver.switchTo().frame(0);
    if (isExistEle(driver, "iframe[title='reCAPTCHA ウィジェット']")) {
      return false;
    }
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 3000);
      String
      //    radioSele = "div.answer>input[type='radio']", //
      //    checkboxSele = "div.answer>input[type='checkbox']", //
      radioSele = "div.answer>label", //
      checkboxSele = radioSele, //
      //    noSele = "span.query-num",
      titleSele = "h2.question", // 質問NOも含む
      seleSub = "div.btn_next>input[type='submit']", //
      finishSele = "div.btn_next>form>input[type='submit']", //
      finishSele2 = "div.btn_getpoint>a", // 6問バージョン用
      seleSele = "select[name*='question_']"; // ドロップダウンセレクター

      Utille.sleep(2000);
      for (int k = 1; k <= 12 + 3; k++) {
        int choiceNum = 0;
        String qTitle = "", choiceSele = "";
        if (isExistEle(driver, titleSele)) {
          qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qTitle);
          if (isExistEle(driver, radioSele)) { // ラジオ
            choiceSele = radioSele;
          }
          else if (isExistEle(driver, checkboxSele)) { // チェックぼっくす
            choiceSele = checkboxSele;
          }
          else if (isExistEle(driver, seleSele)) { // ドロップダウン
            choiceSele = seleSele;
          }

          // 回答選択
          if (radioSele.equals(choiceSele)
              || checkboxSele.equals(choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            if (qTitle.indexOf("あなたの性別") >= 0) {
              choiceNum = 0; // 1：男
            }
            else if (qTitle.indexOf("あなたの年齢を") >= 0) {
              choiceNum = 2; // 2：30代
            }
            else if (qTitle.indexOf("あなたのご職業") >= 0) {
              choiceNum = 2; // 2：会社員
            }
            else if (qTitle.indexOf("あなたの国籍") >= 0) {
              choiceNum = 0; // 0:日本
            }
            else if (qTitle.indexOf("あなたのお住まいを") >= 0) {
              choiceNum = 2; // 2：関東
            }
            else {
              if (checkboxSele.equals(choiceSele)) {
                choiceies--; // チェックボックスは最後の選択肢を除く
              }
              choiceNum = Utille.getIntRand(choiceies);
            }
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              Utille.sleep(500);
              // 選択
              clickSleepSelector(eleList2, choiceNum, 3000);
              if (isExistEle(driver, "div.ad_footer>div>div>div.layered", false)) {
                driver.navigate().refresh();
                k--;
                Utille.sleep(5000);
                driver.switchTo().frame(0);
                if (isExistEle(driver, startSele)) {
                  clickSleepSelector(driver, startSele, 3000);
                }
                continue;
              }
              if (isExistEle(driver, seleSub)) {
                clickSleepSelector(driver, seleSub, 4000);
              }
            }
          }
          else if (seleSele.equals(choiceSele)) {
            Utille.sleep(2000);
            int size3 = getSelectorSize(driver, seleSele + ">option");
            String value = "";
            if (qTitle.indexOf("性別・年代") >= 0) {
              value = "3";
            }
            else if (qTitle.indexOf("のお住まい") >= 0) {
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
            if (isExistEle(driver, "div.ad_footer>div>div>div.layered", false)) {
              driver.navigate().refresh();
              k--;
              Utille.sleep(5000);
              driver.switchTo().frame(0);
              if (isExistEle(driver, startSele)) {
                clickSleepSelector(driver, startSele, 3000);
              }
              continue;
            }
            if (isExistEle(driver, seleSub)) {
              clickSleepSelector(driver, seleSub, 4000);
            }
          }
        }
        else {
          break;
        }
      }
      if (isExistEle(driver, finishSele)) {
        String wid2 = driver.getWindowHandle();
        clickSleepSelector(driver, finishSele, 4000);
        String wid3 = driver.getWindowHandle();
        if (wid3 != wid2) {
          driver.switchTo().window(wid3);
          driver.close();
          changeWindow(driver, wid2);
        }
      }
    }
    driver.close();
    driver.switchTo().window(wid);
    return true;
  }
}
