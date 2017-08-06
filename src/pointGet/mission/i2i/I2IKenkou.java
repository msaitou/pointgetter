package pointGet.mission.i2i;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;

/**
 * @author saitou
 *
 */
public class I2IKenkou extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public I2IKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "気になるニュース");
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(3000);
    selector = "li.pointfreeList_item";
    if (isExistEle(driver, selector)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "img[src='/img/special/freepoint/news_340_120.png']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String wid = driver.getWindowHandle();
          clickSleepSelector(e, selector2, 5000);
          // アラートをけして
          checkAndAcceptAlert(driver);
          Utille.sleep(5000);
          changeWindow(driver, wid);
          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          String seleNextb2 = "form>input[type='image']";
          while (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4000); // 遷移　問開始
            if (isExistEle(driver, seleNextb2)) {
              Colum.answer(driver, seleNextb2, null);
            }
            driver.navigate().refresh();
            Utille.sleep(5000);
          }
          break; // 星座はこれで終了
        }
      }
    }
  }
}
