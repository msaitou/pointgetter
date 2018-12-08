package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerPochi extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerPochi(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele div.btn_send>a>p
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String seleNextb2 = "input.enquete_nextbt.enqpage", //
    // [style*='display: block']
    overLay = "div#gn_interstitial_area[style*='z-index:1000000000000'] img#gn_interstitial_close_icon", //
    choiceSele = "input[type='radio']", // ラジオセレクター
    seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
    seleSele = "form.menu>select", // プルダウンセレクター
    finishSele = "span.enquete_nextbt.next_bt", // 終了ボタンセレクター
    closeSele = "input.btn_close_en"; // 閉じるボタンセレクター

    checkOverlay(driver, overLay, false);
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    }

    //    for (int g = 0; g < 2; g++) {
    //      checkOverlay(driver, overLay, false);
    //      if (isExistEle(driver, startSele)) {
    //        clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
    //      }
    //    }

    // 12問
    for (int k = 1; k <= 7; k++) {
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
            //          case 3:
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
          if (isExistEle(driver, startSele)) {
            //            div.btn_send>a>p
            String aaa1 = "div.btn_send#aaa1";
            String aaa2 = "div.btn_send#aaa2";
            String addSele = ">a>p";
            if (isExistEle(driver, aaa1) && !isExistEle(driver, aaa1 + "[style*='display:none;']", false)) {
              if (isExistEle(driver, aaa1 + addSele)) {
                clickSleepSelector(driver, aaa1 + addSele, 3500);
              }
            }
            else {
              if (isExistEle(driver, aaa2 + addSele)) {
                clickSleepSelector(driver, aaa2 + addSele, 3500);
              }
            }
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
        if (isExistEle(driver, startSele)) {
          // 次へ
          clickSleepSelector(driver, startSele, 4000);
        }
        break;
      }
    }

    for (int g = 0; g < 2; g++) {
      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, startSele)) {
        // 次へ
        clickSleepSelector(driver, startSele, 4000);
      }
    }

    if (isExistEle(driver, finishSele)) {
      // 終了する
      clickSleepSelector(driver, finishSele, 4000);
    }
    //    driver.close();
    //    driver.switchTo().window(wid);
  }
}
