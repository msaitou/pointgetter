package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerPittango extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerPittango(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String choiceSele = "div.answer__form>label", // ラジオセレクター
        seleNext = "div.button__box>input#adtech-button", //
        titleSele = "h2.question", // close
        alreadyGetSele = "p.bonus__progress.settled", a = "";

    clickSleepSelector(driver, startSele, 4000);
//    String agreeSele = "label[for=agree_checkbox]";
//    if (isExistEle(driver, agreeSele)) {
//      clickSleepSelector(driver, agreeSele, 4000);
//      String seleSub = "input.button--start";
//      if (isExistEle(driver, seleSub)) {
//        clickSleepSelector(driver, seleSub, 3000);
//      }
//    }

    // 10問?
    for (int k = 1; k <= 15; k++) {
      if (isExistEle(driver, titleSele)) {
        String qTitle = driver.findElement(By.cssSelector(titleSele)).getText();
        logg.info(qTitle);
        int choiceNum = 0;
        if (isExistEle(driver, choiceSele)) {
          int choiceies = getSelectorSize(driver, choiceSele);
          choiceNum = Utille.getIntRand(choiceies);
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
          if (isExistEle(eleList2, choiceNum)) {
            // 選択
            clickSleepSelector(driver, eleList2.get(choiceNum), 3000);
          }
        }
        if (isExistEle(driver, startSele)) {
          // 次へ
          clickSleepSelector(driver, startSele, 4500);
          if (isExistEle(driver, startSele)) {
            // 次へ
            clickSleepSelector(driver, startSele, 4500);
          }
        }
      }
    }
    driver.close();
    // 最後に格納したウインドウIDにスイッチ
    driver.switchTo().window(wid);
  }
}
