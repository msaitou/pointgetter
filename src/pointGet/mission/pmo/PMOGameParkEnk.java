package pointGet.mission.pmo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.parts.AnswerGameParkEnk;

public class PMOGameParkEnk extends PMOBase {
  final String url = "http://poimon-plus.gamepark.net/survey/summary/";
  WebDriver driver = null;
  /* アンケートクラス　ポイントサーチ */
  AnswerGameParkEnk GameParkEnk = null;

  /**
   * @param logg
   */
  public PMOGameParkEnk(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "ポイントリサーチ");
    GameParkEnk = new AnswerGameParkEnk(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    driver.get(url);
    selector = "div.qBox>button";
    int skip = 1;
    String sele1 = "form>button#nextBtn";// pointResearch用
    while (true) {
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size = eleList.size(), targetIndex = 0;
      if (size > targetIndex && isExistEle(eleList, targetIndex)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(eleList, targetIndex, 3000); // アンケートスタートページ
        changeWindow(driver, wid);
        //        if (isExistEle(driver, sele1)) {
        Utille.sleep(4000);
        GameParkEnk.answer(driver, sele1, wid);
        //        }
        //        else {
        //          skip++;
        //          driver.close();
        //          driver.switchTo().window(wid);
        //        }
        driver.navigate().refresh();
        Utille.sleep(5000);
      }
      else {
        break;
      }
    }
  }
}
