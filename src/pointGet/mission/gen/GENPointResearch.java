package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerPointResearch;

public class GENPointResearch extends GENBase {
  final String url = "http://www.gendama.jp/survey/mini";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;

  /**
   * @param logg
   */
  public GENPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "アンケート");
    PointResearch = new AnswerPointResearch(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "a dd.survey_answer";
    int skip = 1;
    String sele1 = "div.ui-control.type-fixed>a.ui-button";// pointResearch用
    while (true) {
      if (!isExistEle(driver, selector)) {
        // 対象がなくなったら終了
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (size > targetIndex
          && isExistEle(eleList, targetIndex)) { // 古い順にやる
        clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
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
        // 対象がなくなったら終了
        break;
      }
    }
  }
}
