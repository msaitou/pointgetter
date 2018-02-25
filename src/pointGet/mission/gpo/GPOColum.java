package pointGet.mission.gpo;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;

/**
 * @author saitou
 *
 */
public class GPOColum extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public GPOColum(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "コラム");
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(3000);
    String sele1 = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "li.menu04>a";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      if (isExistEle(driver, selector2)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(driver, selector2, 5000);
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
          Utille.refresh(driver, logg);
          Utille.sleep(5000);
        }
      }
    }
  }
}
