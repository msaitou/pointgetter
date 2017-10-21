package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerColum extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerColum(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String seleNextb2 = "form>input[type='image']", //
        seleNextb3 = "form>input[alt='next']", //
        overLay = "div#interstitial[style*='display: block']>div>div#inter-close", //
        choiceSele = "input[type='radio']", // ラジオセレクター
        seleNext2 = "div>input.enquete_nextbt", //　次へセレクター
        seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
        seleSele = "form.menu>select", // プルダウンセレクター
        finishSele = "div#again_bt>a>img", // 終了ボタンセレクター
        closeSele = "input.btn_close_en"; // 閉じるボタンセレクター
    clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    for (int g = 0; g < 2; g++) {
      if (isExistEle(driver, seleNextb2)) {
        clickSleepSelector(driver, seleNextb2, 4000); // 遷移　問開始するよ
      }
    }
    Utille.sleep(7000);
    // 6page
    for (int g = 0; g < 6; g++) {
      if (isExistEle(driver, seleNextb3)) {
        clickSleepSelector(driver, seleNextb3, 13000); // 遷移　問開始するよ
      }
    }
    checkOverlay(driver, overLay, false);
    if (isExistEle(driver, seleNextb2)) {
      clickSleepSelector(driver, seleNextb2, 5000); // 遷移　問開始するよ
    }
    // 6問
    for (int k = 1; k <= 6; k++) {
      checkOverlay(driver, overLay, false);
      Utille.sleep(2000);
      int choiceNum = 0;
      if (isExistEle(driver, choiceSele)) {
        int choiceies = getSelectorSize(driver, choiceSele);
        switch (k) {
          case 1:
            // 1問目は1：男
            break;
          case 2:
          case 3:
            // 2問目は3：30代
            // 3問目は3：会社員
            if (choiceies > 2) {// 一応選択可能な範囲かをチェック
              choiceNum = 2;
            }
            break;
          default:
            choiceNum = Utille.getIntRand(choiceies);
        }
        List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
        if (isExistEle(eleList2, choiceNum)) {
          // 選択
          clickSleepSelector(eleList2.get(choiceNum), 3500);
          if (isExistEle(driver, seleNext2)) {
            // 次へ
            clickSleepSelector(driver, seleNext2, 4000);
          }
        }
      } else if (isExistEle(driver, seleSele)) {
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
      } else {
        break;
      }
    }
    for (int ii = 0; ii < 2; ii++) {
      checkOverlay(driver, overLay, false);
      Utille.clickRecaptha(driver, logg);

      if (isExistEle(driver, seleNextb2)) {
        clickSleepSelector(driver, seleNextb2, 4000); // 遷移　
      }
    }
    checkOverlay(driver, overLay, false);
    String wid2 = driver.getWindowHandle();
    boolean winNotClosed = false;
    if (isExistEle(driver, finishSele)) {
      clickSleepSelector(driver, finishSele, 5000); // 遷移
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
    }
    
//    if (isExistEle(driver, closeSele)) {
//      clickSleepSelector(driver, closeSele, 4000);
//    }
//    else {
//    }
      // このウィンドウが新しく開かれていれば閉じるし、一覧から同じウィンドウなら閉じない
      if (null == wid) {
        logg.info("close0");
      }
      else {
        logg.info("close1");
//        String wid2 = driver.getWindowHandle();
          logg.info("close2");
          if (winNotClosed) {
            logg.info("close3");
            driver.close();
            logg.info("close4");
          }
        logg.info("close6");
        driver.switchTo().window(wid);
        logg.info("close7");
      }
  }
}
