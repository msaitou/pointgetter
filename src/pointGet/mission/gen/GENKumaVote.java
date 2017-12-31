package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerSouSenkyo;

public class GENKumaVote extends GENBase {
  final String url = "http://www.gendama.jp/";
  AnswerSouSenkyo SouSenkyo = null;

  /**
   * @param logg
   */
  public GENKumaVote(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "くま投票");
    SouSenkyo = new AnswerSouSenkyo(logg);
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    // div#dropmenu01
    driver.get(url);
    selector = "div#dropmenu01";
    if (isExistEle(driver, selector, false)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "a[onclick*='CMくじ']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
          driver.get(cmPageUrl); // CMpage
          Utille.sleep(3000);
          String sele0 = "a.start__button" //
          , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
          , preSele = "dl>dt>img[src*='kumakumasenkyo']";
          if (isExistEle(driver, preSele)) {
            clickSleepSelector(driver, preSele, 5000); // 遷移 全体へ
            changeCloseWindow(driver);
            Utille.sleep(5000);
            if (isExistEle(driver, sele0)) {
              clickSleepSelector(driver, sele0, 5000); // 遷移 全体へ
              while (isExistEle(driver, sele1)) {
                List<WebElement> eleList = driver.findElements(By.cssSelector(sele1));
                int size1 = eleList.size(), zumiCnt = 0, targetIndex = 0;
                if (isExistEle(eleList, targetIndex)) {
                  Utille.scrolledPage(driver, eleList.get(targetIndex));
                  clickSleepSelector(driver, eleList, targetIndex, 4000); // 遷移
                  SouSenkyo.answer(driver, "", null);
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
  }
}
