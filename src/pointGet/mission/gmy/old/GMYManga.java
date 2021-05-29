package pointGet.mission.gmy.old;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.gmy.GMYBase;
import pointGet.mission.parts.AnswerManga;

/**
 * @author saitou
 *
 */
public class GMYManga extends GMYBase {
  final String url = "https://dietnavi.com/pc/game/";
  AnswerManga Manga = null;

  /**
   * @param logg
   */
  public GMYManga(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "mannga");
    Manga = new AnswerManga(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.sleep(3000);
    String sele1 = "img[alt='漫画でアンケート']", selector2 = "li.menu03>a";
    if (isExistEle(driver, sele1)) {
      clickSleepSelector(driver, sele1, 4000);
      // アラートをけして
      checkAndAcceptAlert(driver);
      Utille.sleep(5000);
      changeCloseWindow(driver);
      selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
      String seleNextb2 = "form>input[type='image']";
      while (isExistEle(driver, selector)) {
        if (isExistEle(driver, selector)) {
          List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
          int size2 = eleList.size(), targetIndex = size2 - 1;
          logg.info("size2:" + size2 + " target:" + targetIndex);
          if (size2 > targetIndex && isExistEle(eleList, targetIndex)) { // 古い順にやる
            Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
            Actions actions = new Actions(driver);
            actions.keyDown(Keys.CONTROL);
            actions.click(driver.findElements(By.cssSelector(selector)).get(targetIndex));
            actions.perform();
            Utille.sleep(5000);

            String wid = driver.getWindowHandle();
            changeWindow(driver, wid);
            if (isExistEle(driver, seleNextb2)) {
              Manga.answer(driver, seleNextb2, wid);
              Utille.refresh(driver, logg);
              Utille.sleep(5000);
            }
            else {
              break;
            }
          }
        }
        else {
          break;
        }
      }
    }
  }
}
