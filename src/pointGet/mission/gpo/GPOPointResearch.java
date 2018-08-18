package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerPointResearch;

public class GPOPointResearch extends GPOBase {
  final String url = "http://www.gpoint.co.jp/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;

  /**
   * @param logg
   */
  public GPOPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    PointResearch = new AnswerPointResearch(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String sele = "a[href='https://kotaete.gpoint.co.jp/']>span.navi-icon",
        selector2 = "li.menu05>a";
    String sele1 = "div.ui-control.type-fixed>a.ui-button";
    int skip = 1;
    if (isExistEle(driver, sele)) {
      clickSleepSelector(driver, sele, 4000);
      if (isExistEle(driver, selector2)) {
        clickSleepSelector(driver, selector2, 4000);
        changeCloseWindow(driver);
        while (true) {
          selector = "td>a[href*='ad-contents.jp']";
          if (!isExistEle(driver, selector)) {
            // 対象がなくなったら終了
            break;
          }
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size = eleList.size(), targetIndex = skip - 1; // 順番はサイト毎に変更可能だが、変数を使う
          if (targetIndex > -1 && size > targetIndex
              && isExistEle(eleList, targetIndex)) {
            clickSleepSelector(driver, eleList, targetIndex, 6000); // アンケートスタートページ
            String wid = driver.getWindowHandle();
            changeWindow(driver, wid);
            String cUrl = driver.getCurrentUrl();
            logg.info("url[" + cUrl + "]");
            if (isExistEle(driver, sele1)) {
              PointResearch.answer(driver, sele1, wid);
            }
            else {
              skip++;
              driver.close();
              driver.switchTo().window(wid);
            }
            Utille.refresh(driver, logg);
            Utille.sleep(5000);
          }
          else {
            break;
          }
        }
      }
    }
  }
}
