package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerPointResearch extends MissCommon {
  final String url = "http://www.gendama.jp/survey/mini";

  //  WebDriver driver = null;

  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerPointResearch(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    clickSleepSelector(driver, startSele, 4000);
    // 回答開始
    String beginSele = "form>input.ui-button";
    if (isExistEle(driver, beginSele)) {
      clickSleepSelector(driver, beginSele, 4000);
      String choiceSele = "label.ui-label-radio", // ラジオセレクター
      seleNext2 = "div.fx-control>input.ui-button", // 次へボタンセレクター
      seleSele = "select.ui-select", // ドロップダウンセレクター
      overLay = "div.overlay-popup a.button-close", // タイトルセレクター 
      noSele = "div.ui-item-no", // 問番セレクター
      titleSele = "h2.ui-item-title", // タイトルセレクター
      checkSele = "label.ui-label-checkbox"; // チェックボックスセレクター
      // 12問
      for (int k = 1; k <= 15; k++) {
        if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
            && isExistEle(driver, overLay)) {
          checkOverlay(driver, overLay, false);
        }
        if (isExistEle(driver, noSele)) {
          String qNo = driver.findElement(By.cssSelector(noSele)).getText();
          String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
          logg.info(qNo + " " + qTitle);
          int choiceNum = 0;
          if (isExistEle(driver, choiceSele)) {
            int choiceies = getSelectorSize(driver, choiceSele);
            if (qTitle.indexOf("性別をお知らせ") >= 0
                || qTitle.indexOf("未既婚をお知") >= 0) {
              choiceNum = 0; // 1：男 // 1: 未婚
            }
            else if (qTitle.indexOf("年齢をお知") >= 0
                || qTitle.indexOf("職業をお知") >= 0) {
              choiceNum = 2; // 2：30代 // 2：会社員
            }
            else {
              choiceNum = Utille.getIntRand(choiceies);
            }
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
            }
          }
          else if (isExistEle(driver, checkSele)) {
            int size4 = getSelectorSize(driver, checkSele);
            choiceNum = Utille.getIntRand(size4);
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(checkSele));
            if (isExistEle(eleList2, choiceNum)) {
              // 選択
              clickSleepSelector(eleList2.get(choiceNum), 3000);
            }
          }
          else if (isExistEle(driver, seleSele)) {
            Utille.sleep(4000);
            int size3 = getSelectorSize(driver, seleSele + ">option");
            String value = "";
            if (qTitle.indexOf("居住区") >= 0) {
              value = "4";
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
        if (isExistEle(driver, seleNext2)) {
          // 次へ
          clickSleepSelector(driver, seleNext2, 4500);
        }
      }
      Utille.sleep(2000);
      if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close", false)
          && isExistEle(driver, overLay)) {
        checkOverlay(driver, overLay, false);
      }
      if (isExistEle(driver, beginSele)) {
        // ポイント獲得
        clickSleepSelector(driver, beginSele, 6000);
      }
      checkOverlay(driver, overLay, false);
      String closeSele = "div.ui-control>a";
      if (isExistEle(driver, closeSele)) {
        // 閉じる
        clickSleepSelector(driver, closeSele, 5000);
      }
      driver.close();
      // 最後に格納したウインドウIDにスイッチ
      driver.switchTo().window(wid);
    }
  }
}
