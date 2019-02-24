package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerQuiz;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class GPOQuizKentei extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";
  AnswerQuiz Quiz = null;

  /**
   * @param logg
   */
  public GPOQuizKentei(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "クイズ検定");
    Quiz = new AnswerQuiz(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "img[alt='クイズ検定Q']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(5000);
      //    String wid = wid0;

      selector = "td.status>a"; // アンケート一覧の回答するボタン
      Utille.sleep(5000);
      String sele = "a>img.next_bt";
      String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
      checkOverlay(driver, overLay, false);
      int skip = 0, beforeSize = 0;

      while (true) {
        if (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip;
          if (beforeSize == size) {
            skip++;
          }

          logg.info("size:" + size + " target:" + targetIndex);
          if (size > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
            Utille.scrolledPage(driver, eleList.get(targetIndex));
            //            clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL);
            actions.click(eleList.get(targetIndex));
            actions.perform();
            Utille.sleep(3000);

            String wid = driver.getWindowHandle();
            changeWindow(driver, wid);

            if (isExistEle(driver, sele)) {
              Quiz.answer(driver, sele, wid);
              driver.close();
              driver.switchTo().window(wid);
            }
            else {
              driver.close();
              driver.switchTo().window(wid);
            }
          }
          else {
            break;
          }
          beforeSize = size;
          Utille.refresh(driver, logg);
        }
        else {
          break;
        }
      }
    }
  }
}
