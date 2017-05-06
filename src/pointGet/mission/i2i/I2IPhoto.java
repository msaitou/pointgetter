package pointGet.mission.i2i;

import java.util.List;
import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.Utille;
import pointGet.mission.parts.AnswerPhotoEnk;

/**
 * @author saitou
 *
 */
public class I2IPhoto extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";
  /* アンケートクラス　写真 */
  AnswerPhotoEnk PhotoEnk = null;

  /**
   * @param logg
   */
  public I2IPhoto(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "写真アンケート");
    PhotoEnk = new AnswerPhotoEnk(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "li.pointfreeList_item";
    if (isExistEle(driver, selector)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "img[src='/img/special/freepoint/photo_340_120.png']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          clickSleepSelector(e, selector2, 5000);
          // アラートをけして
          val alert = driver.switchTo().alert();
          alert.accept();
          Utille.sleep(5000);
          changeCloseWindow(driver);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          String sele8 = "form>input.next_bt";
          while (true) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              int size2 = eleList.size(), targetIndex = size2 - 1;
              logg.info("size2:" + size2 + " target:" + targetIndex);
              if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
//                try {
//                  new Robot().keyPress(KeyEvent.VK_CONTROL);
//                  new Actions(driver).click(eleList.get(targetIndex)).build().perform();
//                  new Robot().keyRelease(KeyEvent.VK_CONTROL);
//              } catch (AWTException ee) {
//                  throw new IllegalStateException(ee);
//              }
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
                actions.perform();
                Utille.sleep(5000);
//                                              clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
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
          break;
        }
      }
    }
  }
}
