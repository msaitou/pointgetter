package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerAdShindan extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerAdShindan(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String choiceSele = "div.answer__form>label.radio", // ラジオセレクター
    seleNext = "div.button__box>input#adtech-button", //
    titleSele = "div.question", // close
    alreadyGetSele = "p.bonus__progress.settled", a = "";
    if (!isExistEle(driver, alreadyGetSele, false)) {// 既に獲得済みがなければ
      clickSleepSelector(driver, startSele, 4000);
      
      String agreeSele = "label[for=agree_checkbox]";
      if (isExistEle(driver, agreeSele)) {
        clickSleepSelector(driver, agreeSele, 4000);
        String seleSub = "input.button--start";
        if (isExistEle(driver, seleSub)) {
          clickSleepSelector(driver, seleSub, 3000);
        }
      }

      // 12問?
      for (int k = 1; k <= 20; k++) {
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
              clickSleepSelector(eleList2.get(choiceNum), 3000);
            }
          }
          if (isExistEle(driver, seleNext)) {
            // 次へ
            clickSleepSelector(driver, seleNext, 4500);
          }
        }
      }
    }
    driver.close();
    // 最後に格納したウインドウIDにスイッチ
    driver.switchTo().window(wid);
  }
}
