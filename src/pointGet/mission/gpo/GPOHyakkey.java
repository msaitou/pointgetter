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
import pointGet.mission.parts.AnswerHyakkey;

/**
 * @author saitou
 *
 */
public class GPOHyakkey extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  /* アンケートクラス　日本百景 */
  AnswerHyakkey Hyakkey = null;

  /**
   * @param logg
   */
  public GPOHyakkey(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "日本百景アンケート");
    Hyakkey = new AnswerHyakkey(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "li.menu07>a";
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
              Utille.scrolledPage(driver, eleList.get(targetIndex));
              Utille.sleep(3000);
              Actions actions = new Actions(driver);
              actions.keyDown(Keys.CONTROL);
              actions.click(eleList.get(targetIndex));
              actions.perform();

              String wid = driver.getWindowHandle();
              Utille.sleep(7000);
              changeWindow(driver, wid);
              if (isExistEle(driver, sele8)) {
                Hyakkey.answer(driver, sele8, wid);
              }
              else {
                break;
              }
              Utille.refresh(driver, logg);
              Utille.sleep(5000);
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
