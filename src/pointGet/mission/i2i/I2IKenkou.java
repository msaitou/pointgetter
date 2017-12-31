package pointGet.mission.i2i;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerKenkou;

/**
 * @author saitou
 *
 */
public class I2IKenkou extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";
  AnswerKenkou Kenkou = null;

  /**
   * @param logg
   */
  public I2IKenkou(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "さらさら健康コラム");
    Kenkou = new AnswerKenkou(logg);
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
        String selector2 = "img[src='/img/special/freepoint/sarasara_340_120.png']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, e, selector2, 5000);
          // アラートをけして
          checkAndAcceptAlert(driver);
          Utille.sleep(10000);
          changeWindow(driver, wid);
          selector = "a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          String seleNextb2 = "form>input[alt='進む']";
          while (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4000); // 遷移　問開始
            if (isExistEle(driver, seleNextb2)) {
              Kenkou.answer(driver, seleNextb2, null);
            }
            driver.navigate().refresh();
            // アラートをけして
            checkAndAcceptAlert(driver);
            Utille.sleep(10000);
          }
          break;
        }
      }
    }
  }
}
