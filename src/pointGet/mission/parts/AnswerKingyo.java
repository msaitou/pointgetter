package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerKingyo extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerKingyo(Logger log) {
    logg = log;
  }

  /**
  *
  * @param startSele a>img.next_bt
  * @param wid
  */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");

    String sele1 = "img[alt='進む']";
    String sele2 = "img[alt='金魚']";
    String sele3 = "img[alt='カード']";
    String sele4 = "img[alt='カードをめくる']";
    String sele5 = "img[alt='次のカードをチェックする']";
    String sele6 = "img[alt='終了する']";
    Utille.sleep(9000);
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 3000); // 遷移
    }
    if (isExistEle(driver, sele2)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(sele2));
      int selectSize = eleList.size();
      int ran = Utille.getIntRand(selectSize); // 4択の金魚を選択
      if (isExistEle(eleList, ran)) {
        clickSleepSelector(driver, eleList.get(ran), 3000);
        for (int i = 0; i < 3; i++) {
          if (isExistEle(driver, sele3)) {
            clickSleepSelector(driver, sele3, 4000); // 遷移
            if (isExistEle(driver, sele4)) {
              clickSleepSelector(driver, sele4, 4000); // 遷移
              if (i < 2) {
                if (isExistEle(driver, sele5)) {
                  clickSleepSelector(driver, sele5, 4000); // 遷移
                }
              }
              else {
                for (int j = 0; j < 5; j++) {
                  if (isExistEle(driver, sele1,false)) {
                    clickSleepSelector(driver, sele1, 6000); // 遷移
                  }
                }
                Utille.sleep(4000);
                if (isExistEle(driver, sele6)) {
                  clickSleepSelector(driver, sele6, 4000); // 遷移
                }
              }
            }
          }
        }
      }
    }
    driver.close();
    driver.switchTo().window(wid);
  }
}
