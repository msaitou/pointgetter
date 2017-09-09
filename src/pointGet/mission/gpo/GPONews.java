package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerNews;

/**
 * @author saitou
 *
 */
public class GPONews extends GPOBase {
  final String url = "http://www.gpoint.co.jp/gpark/";
  AnswerNews News = null;

  /**
   * @param logg
   */
  public GPONews(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "気になるニュース");
    News = new AnswerNews(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(3000);
    String selector2 = "div.bnr>[alt='今日の気になるニュース']";
    if (isExistEle(driver, selector2)) {
      String wid = driver.getWindowHandle();
      clickSleepSelector(driver, selector2, 5000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(10000);
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
    }
  }
}
