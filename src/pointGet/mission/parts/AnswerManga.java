package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerManga extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerManga(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String overLay = "div#interstitial[style*='display: block']>div>div#inter-close", //
    //    seleNextb2 = "form>input[type='image']", //
    //        seleNextb3 = "form>input[alt='next']", //
    choiceSele = "input[type='radio']", // ラジオセレクター
    seleNext2 = "div>input.enquete_nextbt", //　次へセレクター
    seleNext3 = "div>input.enquete_nextbt_2", //　次へセレクター2
    seleSele = "form.menu>select", // プルダウンセレクター
    finishSele = "div#again_bt>a>img", // 終了ボタンセレクター
    //        closeSele = "input.btn_close_en", // 閉じるボタンセレクター
    a = "";

    //    for (int g = 0; g < 9; g++) {
    //      if (isExistEle(driver, startSele)) {
    //        checkOverlay(driver, overLay);
    //        clickSleepSelector(driver, startSele, 4000); // 遷移
    //      }
    //    }
    String seleNext = "form>input[type='image']";
    String seleNexttugi = seleNext + "[src='common/image/9999/manga_next_bt.png']";
    String seleMouseOver = "div#ad1";
    for (int g = 0, h = 0; g < 8;) {
      checkOverlay(driver, overLay, false);
      //      if (isExistEle(driver, seleMouseOver)) {
      //        // マウスオーバー
      //        WebElement mouseTarget = driver.findElement(By.cssSelector(seleMouseOver));
      //        Actions builder = new Actions(driver);
      // チェインするとTypeError: action.inputStateMap is undefined　って怒られる
      //        builder.moveToElement(mouseTarget).build().perform();
      //        builder.moveToElement(mouseTarget);
      //        builder.build();
      //        builder.perform();

      //        Utille.sleep(5000);
      //      }
      if (isExistEle(driver, seleNexttugi)) {
        clickSleepSelector(driver, seleNexttugi, 5000); // 遷移
        g++;
      }
      else if (isExistEle(driver, seleNext)) {
        //      WebElement mouseTarget = driver.findElement(By.cssSelector(seleNext));
        //        actions.moveToElement(mouseTarget)
        //        .build();
        //        .perform();
        Utille.mouseOverByScript(driver, seleMouseOver, logg);
        //        Actions actions = new Actions(driver);
        //        actions.keyDown(Keys.CONTROL);
        //        actions.click(driver.findElement(By.cssSelector(seleNext)));
        //        actions.perform();
        Utille.sleep(2000); // 

        clickSleepSelector(driver, seleNext, 4000); // 遷移
        g++;
      }
      else {
        Utille.refresh(driver, logg);
        Utille.sleep(5000);
        if (h++%2 == 1) {
          g++;// 2回に1階加算
        }
      }
    }
    Utille.sleep(4000);
    // 12問
    for (int k = 1; k <= 12; k++) {
      checkOverlay(driver, overLay);
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
          clickSleepSelector(driver, eleList2.get(choiceNum), 2500);
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
    checkOverlay(driver, overLay);
    Utille.clickRecaptha(driver, logg);
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 3000); // 遷移
    }
    checkOverlay(driver, overLay);
    Utille.clickRecaptha(driver, logg);
    //    if (isExistEle(driver, finishSele)) {
    //      clickSleepSelector(driver, finishSele, 3000); // 遷移
    //      // タブが閉じる?
    //    }
    //    else {
    driver.close();
    //    }
    driver.switchTo().window(wid);
  }
}
