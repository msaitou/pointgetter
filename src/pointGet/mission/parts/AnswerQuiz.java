package pointGet.mission.parts;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerQuiz extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerQuiz(Logger log) {
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
    String sele1 = "section label", //
    sele2 = "input.next_bt", //
    sele3 = "img[alt='進む']", //
    sele4 = "img[alt='再挑戦']", //
    sele5 = "img[alt='終了する']",//
    a = "";
    String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
    checkOverlay(driver, overLay, false);
    if (isExistEle(driver, startSele)) {
      clickSleepSelector(driver, startSele, 4000); // 遷移　問開始
      boolean collectFlag = false;
      while (true) {
        checkOverlay(driver, overLay, false);
        if (isExistEle(driver, sele1)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(sele1));
          int choiceNum = Utille.getIntRand(eleList.size());
          if (wrongSelectedVals.size() == 0) {
            if (isExistEle(eleList, choiceNum)) {
              String selectVal = eleList.get(choiceNum).getText();
              wrongSelectedVals.add(selectVal);
              clickSleepSelector(driver, eleList.get(choiceNum), 3500);

              if (isExistEle(driver, sele2)) {
                // 次へ
                clickSleepSelector(driver, sele2, 4000);
                checkOverlay(driver, overLay, false);
                if (isExistEle(driver, sele3)) {
                  // OK
                  clickSleepSelector(driver, sele3, 4000);
                  checkOverlay(driver, overLay, false);

                  // 正解か不正解
                  if (isExistEle(driver, sele4)) {
                    // 再挑戦
                    clickSleepSelector(driver, sele4, 4000);
                  }
                  else {
                    // 正解
                    collectFlag = true;
                  }
                }
              }
            }
          }
          else {

            for (int i = 0; i < eleList.size(); i++) {
              String tmpSele = eleList.get(i).getText();
              boolean skipFlag = false;
              // 一度以上間違えてたら
              for (String wrongVal : wrongSelectedVals) {
                if (tmpSele.equals(wrongVal)) {
                  skipFlag = true;
                }
              }
              if (skipFlag) {
                continue;
              }
              else {
                choiceNum = i;
              }
            }
            if (isExistEle(eleList, choiceNum)) {
              String selectVal = eleList.get(choiceNum).getText();
              wrongSelectedVals.add(selectVal);
              clickSleepSelector(driver, eleList.get(choiceNum), 3500);

              if (isExistEle(driver, sele2)) {
                // 次へ
                clickSleepSelector(driver, sele2, 4000);
                checkOverlay(driver, overLay, false);
                if (isExistEle(driver, sele3)) {
                  // OK
                  clickSleepSelector(driver, sele3, 4000);
                  checkOverlay(driver, overLay, false);

                  // 正解か不正解
                  if (isExistEle(driver, sele4)) {
                    // 再挑戦
                    clickSleepSelector(driver, sele4, 4000);
                  }
                  else {
                    // 正解
                    collectFlag = true;
                    break;
                  }
                }
              }
            }
          }
        }
        if (collectFlag) {
          break;
        }
      }

      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, sele3)) {
        clickSleepSelector(driver, sele3, 4000);
      }
      checkOverlay(driver, overLay, false);
      if (isExistEle(driver, sele5)) {
        clickSleepSelector(driver, sele5, 4000);
        // 閉じられる
      }
    }

    //    driver.close();
    //    driver.switchTo().window(wid);
  }
}
