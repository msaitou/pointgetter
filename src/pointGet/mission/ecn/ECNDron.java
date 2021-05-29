package pointGet.mission.ecn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author saitou
 *
 */
public class ECNDron extends ECNBase {
  final String url = "";

  /**
   * @param log
   */
  public ECNDron(Logger log, Map<String, String> cProps) {
    super(log, cProps, "ドロンバナークリック2種");
  }

  @Override
  public void privateMission(WebDriver driver) {
    String[] dronUrlList = { "https://ecnavi.jp/contents/doron/" };
    selector = "div[class*='character-'][class*='doron-'] ul[class*='character-']";
//    for (int i = 0; i < dronUrlList.length; i++) {
//      Utille.url(driver, dronUrlList[i], logg);
//      Utille.sleep(2000);
//      if (isExistEle(driver, "div.js_anime.got")) {
//        logg.warn(mName + i + "]獲得済み");
//      }
//      else if (isExistEle(driver, selector)) {
//        clickSleepSelector(driver, selector, 2000);
//      }
//    }
    if (isExistEle(driver, selector)) {
      List<WebElement> eleList1 = driver.findElements(By.cssSelector(selector));
      String cliSele = "img";
      for (WebElement ele : eleList1) {
        if (isExistEle(ele, cliSele)) {
          clickSleepSelector(driver, ele, cliSele, 3000);
          closeOtherWindow(driver);
        }
      }
    }

  }
}
