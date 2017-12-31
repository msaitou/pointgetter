package pointGet.mission.sug;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerPointResearch;

public class SUGPointResearch extends SUGBase {
  final String url = "http://www.sugutama.jp/survey";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;

  /**
   * @param logg
   */
  public SUGPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    PointResearch = new AnswerPointResearch(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "img[src='//static.sugutama.jp/ssp_site/6ca17fd4762eff9519a468ab781852d4.png']";
    int skip = 1;
    String sele1 = "div.ui-control.type-fixed>a.ui-button";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // アンケート一覧ページ
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
          driver.navigate().refresh();
          Utille.sleep(5000);
        }
        else {
          break;
        }
      }
    }
  }
}
