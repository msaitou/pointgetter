package pointGet.mission.gmy;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerEasyAnk;

/**
 * @author saitou
 *
 */
public class GMYEasyAnk extends GMYBase {
  final String url = "https://dietnavi.com/pc/survey/";
  AnswerEasyAnk EasyAnk = null;

  /**
   * @param logg
   */
  public GMYEasyAnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "簡単アンケート");
    EasyAnk = new AnswerEasyAnk(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String sele1 = "img[alt='簡単お手軽アンケート']", selector2 = "li.menu03>a";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(5000);
      changeCloseWindow(driver);
      String wid0 = driver.getWindowHandle();
      selector = "td>button"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>p>input.btn";
      while (isExistEle(driver, selector)) {
        if (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size2 = eleList.size(), targetIndex = size2 - 1;
          logg.info("size2:" + size2 + " target:" + targetIndex);
          if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
//            Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
//            Actions actions = new Actions(driver);
//            actions.keyDown(Keys.CONTROL);
//            actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
//            actions.perform();
            Utille.sleep(5000);

            String wid = driver.getWindowHandle();
//            changeCloseWindow(driver);
            clickSleepSelector(driver, eleList, targetIndex, 3000);

            if (isExistEle(driver, seleNextb2)) {
              EasyAnk.answer(driver, seleNextb2, wid);
              Utille.refresh(driver, logg);
              Utille.sleep(5000);
            }
            else {
              break;
            }
//            driver.close();
//            driver.switchTo().window(wid0);
          }
        }
        else {
          break;
        }
      }
    }
  }
}
