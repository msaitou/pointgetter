package pointGet.mission.pic;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 */
public class PICClickBanner extends PICBase {
  final String url = "http://pointi.jp/daily.php";

  /**
   * @param log
   */
  public PICClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "クリックバナー");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(2000);
    String selecter[] = { "li.goto_service>a>img[src='../img_daily/y_btn.png']" };
    for (int j = 0; j < selecter.length; j++) {
      logg.info("selector: start");
      String selector = selecter[j];
      if (isExistEle(driver, selector)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size = eleList.size();
        try {
          for (int i = 0; i < size; i++) {
            if (isExistEle(eleList, i)) {
              clickSleepSelector(driver, eleList, i, 2500);
              closeOtherWindow(driver);
            }
          }
        } catch (Throwable e) {
//          driver.close();
          driver.quit();
          logg.error("##PICException##################");
          logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
          logg.error("##PICException##################");
          driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
        }
      }
      logg.info("selector: end");
    }
  }
}
