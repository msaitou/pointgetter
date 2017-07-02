package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerShindan;

/**
 * @author saitou
 *
 */
public class PICShindan extends PICBase {
  final String url = "http://pointi.jp/contents/research/";
  AnswerShindan Shindan = null;

  /**
   * @param logg
   */
  public PICShindan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "毎日診断");
    Shindan = new AnswerShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "img[src='./web_diagnose/img/top.png']";
    String sele0 = "div.entry", // 
    sele1 = "div[class='thumbnail'] h3.entrytitle>a", // クラスを完全一致にするのは済の場合クラスが追加されるため
    sumiSelector = "img[src='/images/icons/sumi.png']";

    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 8000); // 遷移
      changeCloseWindow(driver);
      while (isExistEle(driver, sele0)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(sele0));
        int size1 = eleList.size(), zumiCnt = 0;
        WebElement wEle = null;
        for (int i = 0; i < size1; i++) {
          if (isExistEle(eleList, i)) {
            if (isExistEle(eleList.get(i), sumiSelector)) {
              if (++zumiCnt > 3) { // 新規ミッション追加時はコメント
                break;
              }
              continue;
            }
            wEle = eleList.get(i);
            break;
          }
        }
        if (wEle == null
            || zumiCnt > 3) {// 新規ミッション追加時はコメント
          logg.warn(mName + "]all済み");
          break;
        }
        if (isExistEle(wEle, sele1)) {
          clickSleepSelector(wEle, sele1, 4000); // 遷移
          waitTilReady(driver);
          Utille.sleep(4000);
          Shindan.answer(driver, "", null);
        }
        else {
          logg.warn(mName + "]all済み");
          break;
        }
      }
    }
  }
}
