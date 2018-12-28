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
import pointGet.mission.parts.AnswerZukan;

/**
 * @author saitou
 *
 */
public class GPOAnimal extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  /* アンケートクラス　図鑑 */
  AnswerZukan Zukan = null;

  /**
   * @param logg
   */
  public GPOAnimal(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "動物アンケート");
    Zukan = new AnswerZukan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "a[onclick*='doubutsu']";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      if (isExistEle(driver, selector2)) {
        clickSleepSelector(driver, selector2, 5000);
        // アラートをけして
        checkAndAcceptAlert(driver);
        Utille.sleep(2000);
        changeCloseWindow(driver);
        selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
        Utille.sleep(5000);
        String sele8 = "form>input.next_bt";
        while (true) {
          if (isExistEle(driver, selector)) {
            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
            int size2 = eleList.size(), targetIndex = size2 - 1;
            logg.info("size2:" + size2 + " target:" + targetIndex);
            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
                Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
                actions.perform();
                Utille.sleep(5000);

              String wid = driver.getWindowHandle();
              changeWindow(driver, wid);
              if (isExistEle(driver, sele8)) {
                Zukan.answer(driver, sele8, wid);
                Utille.refresh(driver, logg);
                Utille.sleep(5000);
              }
              else {
                break;
              }
            }
          }
          else {
            break;
          }
        }
      }
    }
  }
}
