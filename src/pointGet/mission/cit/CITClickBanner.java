package pointGet.mission.cit;

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
public class CITClickBanner extends CITBase {
  final String url = "https://www.chance.com/";

  /**
   * @param log
   */
  public CITClickBanner(Logger log, Map<String, String> cProps) {
    super(log, cProps, "クリックバナー");
  }

  @Override
  public void privateMission(WebDriver driver) {
    // TOP
    Utille.url(driver, url, logg);
    // 1つめ
    for (String selector1 : new String[] { "div.top_newest div.condition",
        "div.top_recommend li div.condition", "div.top_mypage_side div.right_box>div.condition" }) {
      if (isExistEle(driver, selector1)) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector1));
        int size1 = eleList.size();
        for (int i = 0; i < size1; i++) {
          if (isExistEle(eleList, i)) {
            List<WebElement> pList = eleList.get(i).findElements(By.cssSelector("p"));
            boolean doneFlag = false;
            for (WebElement we : pList) {
              String tex = we.getText();
              if (tex.indexOf("クリック") > -1) {
                clickSleepSelector(driver, we, 5000);
                String sele = "div.btn_point img[src='/img/10/items/btn_point.gif']";
                if (isExistEle(driver, sele)) {
                  waitTilReady(driver);
                  clickSleepSelector(driver, sele, 3000);
                }
                doneFlag = true;
                break;
              }
            }
            if (doneFlag) {
              // TOP
              Utille.url(driver, url, logg);
              Utille.sleep(3000);
              break;
            }
          }
        }
      }
    }
    // 2つめ
    Utille.url(driver, "https://www.chance.com/click.jsp", logg);
    selector = "div.ad_banner>a>img";
    if (isExistEle(driver, selector)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size1 = eleList.size();
      for (int i = 0; i < size1; i++) {
        if (isExistEle(eleList, i)) {
          clickSleepSelector(driver, eleList, i, 4000);
          //					closeOtherWindow(driver);
        }
      }
    }

    // 3つめ
    Utille.url(driver, "https://www.chance.com/present/", logg);
    closeOtherWindow(driver);
    selector = "dl.clearfix>dt>a>img";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000);
      // closeOtherWindow(driver);
    }
    selector = "div.right_list.pt_campaign li.adbox-comment";
    if (isExistEle(driver, selector)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size1 = eleList.size();
      for (int i = 0; i < size1; i++) {
        if (isExistEle(eleList, i)) {
          String addSele = "dd.point";
          if (isExistEle(eleList.get(i), addSele)) {
            String tex = eleList.get(i).findElement(By.cssSelector(addSele)).getText();
            if (tex.indexOf("クリック") > -1) {
              String clcSele = "dl>dt>a";
              if (isExistEle(eleList.get(i), clcSele)) {
                clickSleepSelector(driver, eleList.get(i), clcSele, 4000);
                break;
              }
            }
          }
        }
      }
    }

    // 4つめ
    Utille.url(driver, "https://www.chance.com/shopping/", logg);
    selector = "dl.clearfix>dt>a>img";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000);
      //			closeOtherWindow(driver);
    }
    selector = "div#ichioshi-corner dt>a>img";
    if (isExistEle(driver, selector)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      int size1 = eleList.size();
      for (int i = 0; i < size1; i++) {
        if (isExistEle(eleList, i)) {
          clickSleepSelector(driver, eleList, i, 4000);
          //					closeOtherWindow(driver);
        }
      }
    }
    // 5つめ
    Utille.url(driver, "https://www.chance.com/point/", logg);
    selector = "div#potitto-chance dt>a>img";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000);
      //			closeOtherWindow(driver);
    }
  }
}
