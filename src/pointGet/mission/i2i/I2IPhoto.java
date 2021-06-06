package pointGet.mission.i2i;

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
    Utille.url(driver, url, logg);
    selector = "div.tileItem a div.bnr";
    if (isExistEle(driver, selector)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "img[alt='写真とアンケート']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          clickSleepSelector(driver, e, selector2, 7000);
          // アラートをけして
          checkAndAcceptAlert(driver);
          Utille.sleep(2000);
          changeCloseWindow(driver);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele8 = "form>input.next_bt";
          int skipIndex = 0;
          while (true) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              int size2 = eleList.size(), targetIndex = size2 - 1 - skipIndex;
              logg.info("size2:" + size2 + " target:" + targetIndex);
              if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
                //                try {
                //                  new Robot().keyPress(KeyEvent.VK_CONTROL);
                //                  new Actions(driver).click(eleList.get(targetIndex)).build().perform();
                //                  new Robot().keyRelease(KeyEvent.VK_CONTROL);
                //              } catch (AWTException ee) {
                //                  throw new IllegalStateException(ee);
                //              }
                Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
                //                clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), targetIndex, 7000);
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
                actions.perform();
                Utille.sleep(5000);
                //                                              clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
                String wid = driver.getWindowHandle();
                changeWindow(driver, wid);
                String errSele = "img[alt='エラー発生']";
                if (isExistEle(driver, errSele, true)) {
                  skipIndex++;
                  driver.close();
                  driver.switchTo().window(wid);
                }
                else if (isExistEle(driver, sele8)) {
                  PhotoEnk.answer(driver, sele8, wid);
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
          break;
        }
      }
    }
  }
}
