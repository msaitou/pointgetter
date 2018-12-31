package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerWhichCollect;

/**
 * @author saitou
 *
 */
public class GPOWhichCollectTour extends GPOBase {
  final String url = "https://www.gpoint.co.jp/gpark/";
  /* アンケートクラス　図鑑 */
  AnswerWhichCollect WhichCollect = null;

  /**
   * @param logg
   */
  public GPOWhichCollectTour(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "どっちが正解？海外ツアー");
    WhichCollect = new AnswerWhichCollect(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    String sele1 = "img[alt*='海外ツアー編']";
    WhichCollect.answer(driver, sele1, null);
    //        selector2 = "a[onclick*='doubutsu']";
    //    if (isExistEle(driver, sele1)) {
    //      clickSleepSelector(driver, sele1, 4000);
    //      if (isExistEle(driver, selector2)) {
    //        clickSleepSelector(driver, selector2, 5000);
    //        // アラートをけして
    //        checkAndAcceptAlert(driver);
    //        Utille.sleep(2000);
    //        changeCloseWindow(driver);
    //        selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
    //        Utille.sleep(5000);
    //        String sele8 = "form>input.next_bt";
    //        while (true) {
    //          if (isExistEle(driver, selector)) {
    //            List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
    //            int size2 = eleList.size(), targetIndex = size2 - 1;
    //            logg.info("size2:" + size2 + " target:" + targetIndex);
    //            if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
    //                Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
    //                Actions actions = new Actions(driver);
    //                actions.keyDown(Keys.CONTROL);
    //                actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
    //                actions.perform();
    //                Utille.sleep(5000);
    //
    //              String wid = driver.getWindowHandle();
    //              changeWindow(driver, wid);
    //              if (isExistEle(driver, sele8)) {
    //                Zukan.answer(driver, sele8, wid);
    //                Utille.refresh(driver, logg);
    //                Utille.sleep(5000);
    //              }
    //              else {
    //                break;
    //              }
    //            }
    //          }
    //          else {
    //            break;
    //          }
    //        }
    //      }
    //    }
  }
}
