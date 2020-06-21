package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerQuiz;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class GENQuizKentei extends GENBase {
  final String url = "http://www.gendama.jp/";
  AnswerQuiz Quiz = null;

  /**
   * @param logg
   */
  public GENQuizKentei(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "クイズ検定");
    Quiz = new AnswerQuiz(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div#dropmenu02";
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
    , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
    , preSele = "img[src*='bn_sosenkyo.png']", sele6 = "form>input.next_bt" // コラム用
    ;
    Utille.sleep(3000);

    if (isExistEle(driver, selector, false)) {
      int size0 = getSelectorSize(driver, selector);
      for (int ii = 0; ii < size0; ii++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(ii);
        String selector2 = "a[onclick*='クイズ検定Q']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String quizPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
          Utille.url(driver, quizPageUrl, logg);

          selector = "td.status>a"; // アンケート一覧の回答するボタン
          Utille.sleep(5000);
          String sele = "a>img.next_bt";
          String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
          checkOverlay(driver, overLay, false);
          int skip = 0, beforeSize = 0;

          while (true) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              int size = eleList.size(), targetIndex = skip;
              if (beforeSize == size) {
                skip++;
              }

              logg.info("size:" + size + " target:" + targetIndex);
              if (size > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
                Utille.scrolledPage(driver, eleList.get(targetIndex));
                //            clickSleepSelector(driver, eleList, targetIndex, 5000); // アンケートスタートページ
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(eleList.get(targetIndex));
                actions.perform();
                Utille.sleep(3000);

                String wid = driver.getWindowHandle();
                changeWindow(driver, wid);

                if (isExistEle(driver, sele)) {
                  Quiz.answer(driver, sele, wid);
                  driver.close();
                  driver.switchTo().window(wid);
                }
                else {
                  driver.close();
                  driver.switchTo().window(wid);
                }
              }
              else {
                break;
              }
              beforeSize = size;
              Utille.refresh(driver, logg);
            }
            else {
              break;
            }
          }
        }
      }
    }

  }
}
