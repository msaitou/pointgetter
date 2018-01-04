package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 */
public class GENClickBanner extends Mission {
  final String url = "http://www.gendama.jp/forest/";

  /**
   * @param log
   */
  public GENClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps);
    this.mName = "■ポイントの森(クリック)";
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    Utille.sleep(2000);
    String selecter[] = { "a.itx-listitem-link div", "img[src='http://img.gendama.jp/img/forest/bt_day1.gif']",
        "img[src='//img.gendama.jp/img/neo/index/click_pt.png']",
        "img[src='http://img.gendama.jp/img/forest/forest_bt1.gif']",
        "img[src='//img.gendama.jp/img/renew/common/btn_detail.png']",
        "img[src*='//img.gendama.jp/img/renew/common/btn_detail_daily.gif']" };
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
          logg.error("##GENException##################");
          logg.error(Utille.truncateBytes(e.getLocalizedMessage(), 500));
          logg.error("####################");
          logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 500));
          logg.error("##GENException##################");
          driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
        }
      }
      logg.info("selector: end");
    }

    driver.get("http://www.gendama.jp/mypage/");
    selector = "img[src='http://img.gendama.jp/img/forest/forest_bt1.gif']";
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
//      driver.close();
      driver.quit();
      logg.error("##GEN2Exception##################");
      logg.error(Utille.truncateBytes(e.getLocalizedMessage(), 500));
      logg.error("####################");
      logg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 500));
      logg.error("##GEN2Exception##################");
      driver = Utille.getWebDriver(commonProps.get("geckopath"), commonProps.get("ffprofile"));
    }
  }
}
