package pointGet.mission.i2i;

import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
          String wid = driver.getWindowHandle();
          clickSleepSelector(e, selector2, 5000);
          // アラートをけして
          val alert = driver.switchTo().alert();
          alert.accept();
          Utille.sleep(5000);
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          while (true) {
            if (isExistEle(driver, selector)) {
              PhotoEnk.answer(driver, selector, wid);
              driver.navigate().refresh();
              Utille.sleep(5000);
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
