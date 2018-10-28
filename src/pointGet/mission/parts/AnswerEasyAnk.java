package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerEasyAnk extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerEasyAnk(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String questionSele = "div.panel-heading>strong",
        choiceSele = "div.radio>label", // ラジオセレクター
        seleNext = startSele,
        seleSele = "div>select", // プルダウンセレクター

        seleNextb2 = "form>input[type='image']", //
        seleNextb3 = "form>input[alt='next']", //
        overLay = "div#interstitial[style*='display: block']>div>div#inter-close", //
        seleNext2 = "div>input.enquete_nextbt", //　次へセレクター
        seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
        finishSele = "div#again_bt>a>img", // 終了ボタンセレクター
        closeSele = "input.btn_close_en"; // 閉じるボタンセレクター
    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始

    for (int g = 0; g < 10; g++) {
      String questStr = "", targetSele = "";
      int choiceNum = 0;
      if (isExistEle(driver, questionSele)) {
        questStr = driver.findElement(By.cssSelector(questionSele)).getText();
        logg.info("[" + questStr + "]");
      }

      if (isExistEle(driver, choiceSele)) { // ラジオ
        targetSele = choiceSele;
      }
      // TODO
      else if (isExistEle(driver, seleSele)) { // セレクトボックス
        targetSele = seleSele;
      }

      if (choiceSele.equals(targetSele)) {
        int choiceies = getSelectorSize(driver, choiceSele);
        if (questStr.indexOf("あなたの性別") >= 0
        //            || questStr.indexOf("あなたの職業") >= 0
        ) {
          choiceNum = 0;
        }
        else if (questStr.indexOf("あなたの年齢を") >= 0
            || questStr.indexOf("あなたのご職業を") >= 0) {
          choiceNum = 2;
        }
        else {
          choiceNum = Utille.getIntRand(choiceies);
        }
        List<WebElement> eleList = driver.findElements(By.cssSelector(choiceSele));
        if (isExistEle(eleList, choiceNum)) {
          clickSleepSelector(driver, eleList, choiceNum, 4000);
          if (isExistEle(driver, seleNext)) {
            clickSleepSelector(driver, seleNext, 4000);

          }
        }
      }
      else if (seleSele.equals(targetSele)) {
        Utille.sleep(2000);
        int size3 = getSelectorSize(driver, seleSele + ">option");
        String value = "";
        if (questStr.indexOf("あなたの居住地を") >= 0) {
          value = "13";
        }
        //        else if (qTitle.indexOf("お住まいの都道府県") >= 0) {
        //          value = "14";
        //        }
        else {
          choiceNum = Utille.getIntRand(size3);
          value = driver.findElements(By.cssSelector(seleSele + ">option"))
              .get(choiceNum).getAttribute("value");
        }
        Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
        selectList.selectByValue(value);
        Utille.sleep(3000);
        if (isExistEle(driver, seleNext)) {
          clickSleepSelector(driver, seleNext, 4000);
        }
      }
    }
    String seleEnd = "p>input.btn";
    if (isExistEle(driver, seleEnd)) {
      clickSleepSelector(driver, seleEnd, 5000);
    }
    //    String wid2 = driver.getWindowHandle();
    //    boolean winNotClosed = false;
    //    if (isExistEle(driver, finishSele)) {
    //      clickSleepSelector(driver, finishSele, 5000); // 遷移
    //      java.util.Set<String> widSet = driver.getWindowHandles();
    //      for (String id : widSet) {
    //        if (id.equals(wid2)) {
    //          winNotClosed = true;
    //        }
    //      }
    //      if (winNotClosed) {
    //        logg.info("alert消そう");
    //        // アラートをけして
    //        checkAndAcceptAlert(driver);
    //      }
    //    }

    //      // このウィンドウが新しく開かれていれば閉じるし、一覧から同じウィンドウなら閉じない
    //      if (null == wid) {
    //        logg.info("close0");
    //      }
    //      else {
    //        logg.info("close1");
    //          logg.info("close2");
    //          if (winNotClosed) {
    //            logg.info("close3");
    //            driver.close();
    //            logg.info("close4");
    //          }
    //        logg.info("close6");
    //        driver.switchTo().window(wid);
    //        logg.info("close7");
    //      }
  }
}
