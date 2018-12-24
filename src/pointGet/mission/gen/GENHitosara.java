package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 */
public class GENHitosara extends Mission {
  final String url = "http://www.gendama.jp/forest/";

  /**
   * @param log
   */
  public GENHitosara(Logger log, Map<String, String> cProps) {
    super(log, cProps);
    this.mName = "■一皿";
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(2000);
    selector ="img[src='http://img.gendama.jp/service/campaign/20181212_111142.gif']";
    String wid = driver.getWindowHandle();
    for (int j = 0; j < 100; j++) {
      logg.info("selector: start");
      if (isExistEle(driver, selector)) {
        clickSleepSelector(driver, selector, 2500);
        changeWindow(driver, wid);

        String shopSele = "div#reserve div.shop";
        int choiceies = getSelectorSize(driver, shopSele);
        int choiceNum = Utille.getIntRand(choiceies);

        Utille.scrolledPage(driver, driver.findElements(By.cssSelector(shopSele)).get(choiceNum));
        clickSleepSelector(driver, driver.findElements(By.cssSelector(shopSele)), choiceNum, 3000); // アンケートスタートページ
        driver.close();
        driver.switchTo().window(wid);
      }
    }

  }
}
