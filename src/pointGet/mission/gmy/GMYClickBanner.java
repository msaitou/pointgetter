package pointGet.mission.gmy;

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
public class GMYClickBanner extends GMYBase {
  final String url = "";

  /**
   * @param log
   */
  public GMYClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "clipoバナー");
  }

  @Override
  public void privateMission(WebDriver driver) {
    selector = "a span.clickpt";
    String[] urls = {
        // ■clipoバナーtop
        "http://dietnavi.com/pc/",
        // clipoバナーページ
        "http://dietnavi.com/pc/daily_click.php" };

    for (int j = 0; j < urls.length; j++) {
      driver.get(urls[j]);
      String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
      recoNoneSele = "div#cxOverlayParent>a.recommend_close" // disabled recomend
      ;
      if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
        clickSleepSelector(driver, recoSele, 2000); // 遷移
      }
      if (isExistEle(driver, selector)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        // clipoバナー
        int size = eleList.size();
        for (int i = 0; i < size; i++) {
          if (isExistEle(eleList, i)) {
            clickSleepSelector(eleList, i, 2000);
            closeOtherWindow(driver);
          }
        }
      }
    }
  }
}
