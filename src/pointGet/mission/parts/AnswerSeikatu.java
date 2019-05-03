package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerSeikatu extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerSeikatu(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    String sele0 = "img[alt='ダイジェストをチェック']";
    String sele1 = "img[alt='進む']";
    String sele2 = "p.weather__region>a";
    String sele3 = "p.weather__prefecture>a";
    String sele4 = "p.weather__area>a";
    String sele5 = "label.action__radio__label";
    String sele6 = "img[alt='報酬を獲得する']";
    String sele7 = "img[alt='終了する']";
    Utille.sleep(5000);
//    if (isExistEle(driver, sele0, false)) {
//      return ;
//    }

    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000); // 遷移
    }

    if (isExistEle(driver, sele2)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(sele2));
      int selectSize = eleList.size();
      int ran = Utille.getIntRand(selectSize); // 9択のエリアを選択
      if (isExistEle(eleList, ran)) {
        clickSleepSelector(driver, eleList.get(ran), 4000);
        if (isExistEle(driver, sele3)) {
          List<WebElement> eleList2 = driver.findElements(By.cssSelector(sele3));
          int ran2 = Utille.getIntRand(eleList2.size()); // 県を選択
          if (isExistEle(eleList2, ran2)) {
            clickSleepSelector(driver, eleList2.get(ran2), 4000);

            if (isExistEle(driver, sele4)) {
              List<WebElement> eleList3 = driver.findElements(By.cssSelector(sele4));
              int ran3 = Utille.getIntRand(eleList3.size()); // 県を選択
              if (isExistEle(eleList3, ran3)) {
                clickSleepSelector(driver, eleList3.get(ran3), 6000);

                for (int i = 0; i < 10; i++) {
                  if (isExistEle(driver, sele5)) {
                    clickSleepSelector(driver, sele5, 4000); // 遷移
                    if (isExistEle(driver, sele1)) {
                      clickSleepSelector(driver, sele1, 6000); // 遷移
                    }
                  }
                }
                if (isExistEle(driver, sele6)) {
                  clickSleepSelector(driver, sele6, 6000); // 遷移
                }
                if (isExistEle(driver, sele7)) {
                  clickSleepSelector(driver, sele7, 6000); // 遷移
                }
              }
            }
          }

          clickSleepSelector(driver, sele3, 4000); // 遷移
          if (isExistEle(driver, sele4)) {
            clickSleepSelector(driver, sele4, 4000); // 遷移
            //              if (i < 2) {
            //                if (isExistEle(driver, sele5)) {
            //                  clickSleepSelector(driver, sele5, 4000); // 遷移
            //                }
            //              }
            //              else {
            for (int j = 0; j < 5; j++) {
              if (isExistEle(driver, sele1, false)) {
                clickSleepSelector(driver, sele1, 6000); // 遷移
              }
            }
            Utille.sleep(4000);
            if (isExistEle(driver, sele6)) {
              clickSleepSelector(driver, sele6, 4000); // 遷移
            }
            //              }
          }
        }
      }
    }
    driver.close();
    driver.switchTo().window(wid);
  }
}
