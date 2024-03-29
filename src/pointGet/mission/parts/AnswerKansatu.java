package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerKansatu extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerKansatu(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    Utille.sleep(4000);
    for (int g = 0; g < 3; g++) {
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 4000);
      }
    }
    String
        seleNextb2 = "form>input[alt='進む']", //
        seleChageStart = "div#start", //
    seleEnd = "form>input[type='image']", //
    overLay = "div#interstitial[style*='display: block']>div>div#inter-close", //
    choiceSele = "ul#enqueteUl>li>label", // ラジオセレクター
    seleNext2 = "div>input.enquete_nextbt", //　次へセレクター
    seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
    seleSele = "form.menu>select", // プルダウンセレクター
    titleSele = "p#enqueteTitle";
    for (int g = 0; g < 4; g++) {
      if (isExistEle(driver, seleNextb2)) {
        waitTilReady(driver);
        clickSleepSelector(driver, seleNextb2, 6000); // 遷移　問開始するよ
        if (g == 0) {
          if (isExistEle(driver, seleChageStart)) {
            clickSleepSelector(driver, seleChageStart, 10000); // 画面が変化してる
          }
        }
      }
    }
    Utille.sleep(5000);
    checkOverlay(driver, overLay, false);
    if (isExistEle(driver, seleNextb2)) {
      clickSleepSelector(driver, seleNextb2, 5000); // 遷移　問開始するよ
    }
    // 8問
    for (int k = 1; k <= 8; k++) {
      checkOverlay(driver, overLay, false);
      int choiceNum = 0;
      String qTitle = "";
      if (isExistEle(driver, titleSele)) {
        qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qTitle);
        if (isExistEle(driver, choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          if (qTitle.indexOf("あなたの性別") >= 0) {
            choiceNum = 0; // 1：男
          }
          else if (qTitle.indexOf("あなたの年齢を") >= 0) {
            choiceNum = 2; // 3：30代
          }
          else if (qTitle.indexOf("あなたの職業") >= 0) {
            choiceNum = 2; // 3：会社員
          }
          else if (qTitle.indexOf("あなたの居住地を") >= 0) {
            choiceNum = 2; // 3：関東
          }
          else {
            choiceNum = Utille.getIntRand(choiceies);
          }
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(driver, eleList2.get(choiceNum), 3500);
            if (isExistEle(driver, seleNext2)) {
              // 次へ
              clickSleepSelector(driver, seleNext2, 4000);
            }
          }
        }
        else if (isExistEle(driver, seleSele)) {
          int size3 = getSelectorSize(driver, seleSele + ">option");
          choiceNum = Utille.getIntRand(size3);
          String value = driver.findElements(By.cssSelector(seleSele + ">option"))
              .get(choiceNum).getAttribute("value");
          Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
          //                    selectList.deselectByIndex(choiceNum);
          selectList.selectByValue(value);
          if (isExistEle(driver, seleNext3)) {
            // 次へ
            clickSleepSelector(driver, seleNext3, 4000);
          }
        }
        else {
          break;
        }
      }
    }
    checkOverlay(driver, overLay, false);
    Utille.clickRecaptha(driver, logg);
    if (isExistEle(driver, seleNextb2)) {
      clickSleepSelector(driver, seleNextb2, 4000); // 遷移　
    }
    checkOverlay(driver, overLay, false);
    Utille.clickRecaptha(driver, logg);
    String wid2 = driver.getWindowHandle();
    boolean winNotClosed = false;
    if (isExistEle(driver, seleEnd)) {
      clickSleepSelector(driver, seleEnd, 4000); // 遷移　
      java.util.Set<String> widSet = driver.getWindowHandles();
      for (String id : widSet) {
        if (id.equals(wid2)) {
          winNotClosed = true;
        }
      }
      if (winNotClosed) {
        logg.info("alert消そう");
        // アラートをけして
        checkAndAcceptAlert(driver);
      }
      // このウィンドウが新しく開かれていれば閉じるし、一覧から同じウィンドウなら閉じない
      if (null == wid) {
        logg.info("close0");
      }
      else {
        logg.info("close1");
//        String wid2 = driver.getWindowHandle();
          logg.info("close2");
          if (winNotClosed && !wid2.equals(wid)) {
            logg.info("close3");
            driver.close();
            logg.info("close4");
          }
        logg.info("close6");
        driver.switchTo().window(wid);
        logg.info("close7");
      }
    }
    else {
      logg.info("エラーケース");
      driver.close();
      driver.switchTo().window(wid);
    }
//    driver.close();
//    driver.switchTo().window(wid);
  }
}
