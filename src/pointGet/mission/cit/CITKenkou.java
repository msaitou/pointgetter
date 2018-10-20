package pointGet.mission.cit;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.gmy.GMYBase;
import pointGet.mission.parts.AnswerKenkou;

/**
 * @author saitou
 *
 */
public class CITKenkou extends GMYBase {
  final String url = "https://www.chance.com/game/";
  private String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
  private String footBnrSelector = "div.foot-bnr a.close>span";
  AnswerKenkou Kenkou = null;

  /**
   * @param logg
   */
  public CITKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "けんこう");
    Kenkou = new AnswerKenkou(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String sele1 = "img[alt='さらさら健康コラム']", selector2 = "li.menu03>a";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(5000);
      changeCloseWindow(driver);
      String wid0 = driver.getWindowHandle();
      selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[type='image']";
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
              Kenkou.answer(driver, seleNextb2, wid);
              Utille.refresh(driver, logg);
              Utille.sleep(5000);
            }
            else {
              break;
            }
            //            driver.switchTo().window(wid0);
            //            driver.close();
            //            driver.switchTo().window(wid);
            //            Utille.refresh(driver, logg);
            //            Utille.sleep(5000);
          }
        }
        else {
          break;
        }
      }
    }
  }
}
