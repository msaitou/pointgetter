package pointGet.mission.i2i;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerNews;

/**
 * @author saitou
 *
 */
public class I2INews extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";
  AnswerNews News = null;

  /**
   * @param logg
   */
  public I2INews(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "気になるニュース");
    News = new AnswerNews(logg);
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
          clickSleepSelector(driver, e, selector2, 5000);
          // アラートをけして
          checkAndAcceptAlert(driver);
          Utille.sleep(15000);
          changeWindow(driver, wid);
          selector = "img[src='./images/top/read_button.png']"; // アンケート一覧の回答するボタン
          String seleNextb2 = "a>img[alt='次へ']";
          while (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4000); // 遷移　問開始
            if (isExistEle(driver, seleNextb2)) {
              News.answer(driver, seleNextb2, null);
            }
            driver.navigate().refresh();
            Utille.sleep(10000);
          }
          break; // 星座はこれで終了
        }
      }
    }
  }
}
