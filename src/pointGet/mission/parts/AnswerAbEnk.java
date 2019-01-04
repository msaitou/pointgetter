package pointGet.mission.parts;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerAbEnk extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerAbEnk(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    ArrayList<String> wrongSelectedVals = new ArrayList<String>();
    String sele1 = "article#answer img[src*='answer']", //
//    sele3 = "input.next_bt", //
    sele2 = "input[alt='進む']", //
//    sele4 = "img[alt='再挑戦']", //
//    sele5 = "img[alt='終了する']", //
    a = "";
    String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
        checkOverlay(driver, overLay, false);

    boolean questionFlag = false;
    for (int i = 0; i < 2; i++) {
      if (isExistEle(driver, startSele)) {
        clickSleepSelector(driver, startSele, 3000); // 遷移　問開始
        questionFlag = true;
        checkOverlay(driver, overLay, false);
      }
    }
    if (questionFlag) {
      for (int i = 0; i < 2; i++) {
        checkOverlay(driver, overLay, false);
        if (isExistEle(driver, sele1)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(sele1));
          int choiceNum = Utille.getIntRand(eleList.size());
          if (isExistEle(eleList, choiceNum)) {
            clickSleepSelector(driver, eleList.get(choiceNum), 3500);
            checkOverlay(driver, overLay, false);
            if (isExistEle(driver, sele2)) {
              clickSleepSelector(driver, sele2, 3000); // 遷移　問開始
              checkOverlay(driver, overLay, false);
            }
          }
        }
      }
      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, sele2)) {
        clickSleepSelector(driver, sele2, 3000); // 遷移　問開始
      }
    }

    driver.close();
    driver.switchTo().window(wid);
  }
}
