package pointGet.mission.pst;

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
public class PSTClickBanner extends PSTBase {
  final String url = "http://www.point-stadium.com/mincp.asp";

  /**
   * @param log
   */
  public PSTClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "クリックポイント");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(2000);
    String selecter[] = { "td>a>img" };
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
          driver.quit();
          logg.error("##PILException##################");
          logg.error(Utille.truncateBytes(e.getLocalizedMessage(), 500));
          logg.error("####################");
          logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 500));
          logg.error("##PILException##################");
          driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
        }
      }
      logg.info("selector: end");
    }
  }
}
