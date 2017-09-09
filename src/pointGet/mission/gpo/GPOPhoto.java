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
import pointGet.mission.parts.AnswerPhotoEnk;

/**
 * @author saitou
 *
 */
public class GPOPhoto extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  /* アンケートクラス　写真 */
  AnswerPhotoEnk PhotoEnk = null;

  /**
   * @param logg
   */
  public GPOPhoto(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "写真アンケート");
    PhotoEnk = new AnswerPhotoEnk(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "li.menu05>a";
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
              Actions actions = new Actions(driver);
              actions.keyDown(Keys.CONTROL);
              actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
              actions.perform();
              Utille.sleep(5000);
              //                                            clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
              String wid = driver.getWindowHandle();
              changeWindow(driver, wid);
              if (isExistEle(driver, sele8)) {
                PhotoEnk.answer(driver, sele8, wid);
                driver.navigate().refresh();
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
