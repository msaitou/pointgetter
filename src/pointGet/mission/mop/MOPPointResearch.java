package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerAdserver;
import pointGet.mission.parts.AnswerPointResearch;

public class MOPPointResearch extends MOPBase {
  final String url = "http://pc.moppy.jp/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerPointResearch PointResearch = null;
  AnswerAdserver Adserver = null;

  /**
   * @param logg
   */
  public MOPPointResearch(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    PointResearch = new AnswerPointResearch(logg);
    Adserver = new AnswerAdserver(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    String researchSele = "h3.game__title";
    if (isExistEle(driver, researchSele)) {
      List<WebElement> eles = driver.findElements(By.cssSelector(researchSele));
      int eleSize = eles.size();
      String targetText = "ポイントリサーチ";
      for (int i = 0;i<eleSize;i++ ) {
        if (targetText.equals(eles.get(i).getText())) {
          clickSleepSelector(driver, eles, i , 5000); // 遷移
          break;
        }
      }

      selector = "table a.ui-button";
      int skip = 1;
      String sele1 = "div.ui-control.type-fixed>a.ui-button", //
      sele2 = "div.question_btn>input[type='submit']"//
      ;// pointResearch用
      while (true) {
        if (!isExistEle(driver, selector)) {
          break;
        }
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size(), targetIndex = size - skip;
        if (targetIndex > -1 && targetIndex > -1
            && isExistEle(eleList, targetIndex)) {
          String wid = driver.getWindowHandle();
          clickSleepSelector(driver, eleList, targetIndex, 4000); // アンケートスタートページ
          changeWindow(driver, wid);
          String cUrl = driver.getCurrentUrl();
          logg.info("url[" + cUrl + "]");
          if (isExistEle(driver, sele1)) {
            PointResearch.answer(driver, sele1, wid);
          }
          else if (isExistEle(driver, sele2)) {
            Adserver.answer(driver, sele2, wid);
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
