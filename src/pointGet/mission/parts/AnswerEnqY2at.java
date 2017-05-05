package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.Utille;
import pointGet.mission.MissCommon;

public class AnswerEnqY2at extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerEnqY2at(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("-[" + this.getClass().getName() + "]-");
    String enkTitle = "", // アンケートタイトル
    enkTitleSele = "div#eqttitle", // アンケートタイトルセレクター
    qTitleSele = "div.content_note.note", // タイトルセレクター
    qNoSele = "div.qno", // 問番セレクター
    radioSele = "label.rdck_label_sp", // ラジオセレクター
    overlay = "div.bnrFrame>div.bnrclose>img", // 広告セレクター
    noneOverlay = "div.bnrFrame[style*='display: none;']>div.bnrclose>img", // 非表示広告セレクター 
    seleSele = "div.note>select"; // ドロップダウンセレクター
    boolean maruFlg = false;
    if (isExistEle(driver, enkTitleSele)) {
      enkTitle = driver.findElement(By.cssSelector(enkTitleSele)).getText();
      logg.info("アンケートTITLE[" + enkTitle + "]");
      if (enkTitle.indexOf("クイズ") >= 0) {
        maruFlg = true;
      }
    }

    // 回答開始
    clickSleepSelector(driver, startSele, 7000);
    waitTilReady(driver);
    for (int k = 1; k <= 25; k++) {
      logg.info("[" + k + "]");
      int choiceNum = 0;
      String qTitle = "", qNo = "", choiceSele = "";
      if (isExistEle(driver, qTitleSele)
          && isExistEle(driver, qNoSele)) {
        qNo = driver.findElement(By.cssSelector(qNoSele)).getText();
        qTitle = driver.findElement(By.cssSelector(qTitleSele)).getText();
        logg.info("[" + qNo + " " + qTitle + "]");
      }
      if (isExistEle(driver, radioSele)) { // ラジオ
        choiceSele = radioSele;
      }
      // TODO
      else if (isExistEle(driver, seleSele)) { // セレクトボックス
        choiceSele = seleSele;
      }
      if (radioSele.equals(choiceSele)) {
        int choiceies = getSelectorSize(driver, choiceSele);
        if (qTitle.indexOf("あなたの性別を") >= 0
            || qTitle.indexOf("あなたの未既婚") >= 0
            || qTitle.indexOf("あなたの職業") >= 0) {
          choiceNum = 0;
        }
        else if (qTitle.indexOf("あなたの年齢を") >= 0) {
          choiceNum = 2;
        }
        else if (qTitle.indexOf("お住まいの都道府県") >= 0) {
          choiceNum = 14;
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
        if (qTitle.indexOf("性別・年代") >= 0) {
          value = "3";
        }
        else if (qTitle.indexOf("お住まいの都道府県") >= 0) {
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
        // 次へ(回答へ)
        waitTilReady(driver);
        clickSleepSelector(driver, startSele, 8000);
        // 広告
        if (!isExistEle(driver, noneOverlay)
            && isExistEle(driver, overlay)) {
          clickSleepSelector(driver, overlay, 3000);
        }
        if (isExistEle(driver, "div.ansview_attention", false)) {
//        if (maruFlg
//            || (!maruFlg && k % 10 == 0)) {
          if (isExistEle(driver, startSele)) {
            waitTilReady(driver);
            // 次へ(回答へ)
            clickSleepSelector(driver, startSele, 5000);
          }
        }
      }
    }
    Utille.sleep(3000);
    driver.close();
    // 最後に格納したウインドウIDにスイッチ
    driver.switchTo().window(wid);
  }
}
