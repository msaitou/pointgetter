package pointGet.mission.mop;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerEnkShopQP;
import pointGet.mission.parts.AnswerEnqY2at;
import pointGet.mission.parts.AnswerSurveyEnk;

public class MOPPointResearch2 extends MOPBase {
  final String url = "http://pc.moppy.jp/research/";
  WebDriver driver = null;
  AnswerSurveyEnk SurveyEnk = null;
  AnswerEnqY2at EnqY2at = null;
  AnswerEnkShopQP EnkShopQP = null;

  /**
   * @param logg
   */
  public MOPPointResearch2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ2");
    SurveyEnk = new AnswerSurveyEnk(logg);
    EnqY2at = new AnswerEnqY2at(logg);
    EnkShopQP = new AnswerEnkShopQP(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "div.research td>a>span";
    int skip = 1;
    String
    //    sele1 = "div.ui-control.type-fixed>a.ui-button",// pointResearch用
    sele3 = "div.enq-submit>button[type='submit']", // 回答する surveyenk用
        a = "";
    String sele4 = "div#buttonArea>input[name='next']"; // shop-qp用(4択) // 回答する y2at用(〇×)// rsch用
    while (true) {
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = size - skip;
      if (targetIndex > -1 && isExistEle(eleList, targetIndex)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
        changeWindow(driver, wid);
        String cUrl = driver.getCurrentUrl();
        if (isExistEle(driver, sele3)) {
          SurveyEnk.answer(driver, sele3, wid);
//          skip++;
        }
        else if (cUrl.indexOf("enq.shop-qp.com") >= 0
            && isExistEle(driver, sele4)) {
          EnkShopQP.answer(driver, sele4, wid);
        }
        else if (cUrl.indexOf("enq.y2at.com") >= 0
            && isExistEle(driver, sele4)) {
          EnqY2at.answer(driver, sele4, wid);
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
