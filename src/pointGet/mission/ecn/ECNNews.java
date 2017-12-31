package pointGet.mission.ecn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class ECNNews extends ECNBase {
  final String url = "http://ecnavi.jp/mainichi_news/";

  /**
   * @param log
   */
  public ECNNews(Logger log, Map<String, String> cProps) {
    super(log, cProps, "毎日ニュース");
  }

  @Override
  public void privateMission(WebDriver driver) {
    int ecnNewsClickNorma = 5;
    int ecnNewsClick = 0;
    for (int i = 0; ecnNewsClick < ecnNewsClickNorma
        && i < 20; i++) {
      driver.get(this.url);
      selector = "div.article_list li>a";
      if (!isExistEle(driver, selector)) {
        break;
      }
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      if (isExistEle(eleList, i)) {
        clickSleepSelector(driver, eleList, i, 3000);

        String selector1 = "div.feeling_buttons button";
        int ran = Utille.getIntRand(4);
        if (ran == 0) {
          selector1 += ".btn_feeling_good";
        }
        else if (ran == 1) {
          selector1 += ".btn_feeling_bad";
        }
        else if (ran == 2) {
          selector1 += ".btn_feeling_sad";
        }
        else if (ran == 3) {
          selector1 += ".btn_feeling_glad";
        }
        if (isExistEle(driver, selector1)) {
          clickSleepSelector(driver, selector1, 3000);
          ecnNewsClick++;
        }
        else if (isExistEle(driver, "p.got_maxpoints")) {
          logg.warn(mName + "]獲得済み");
          break;
        }
      }
    }
  }
}
