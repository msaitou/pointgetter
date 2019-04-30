package pointGet.mission.gmy.old;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.gmy.GMYBase;
import pointGet.mission.parts.AnswerShindan;

/**
 * @author saitou
 *
 */
public class GMYShindan extends GMYBase {
  final String url = "http://dietnavi.com/pc/";
  AnswerShindan Shindan = null;

  /**
   * @param logg
   */
  public GMYShindan(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "毎日診断");
    Shindan = new AnswerShindan(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    String sele0 = "div.entry", //
    sele1 = "div[class='thumbnail'] h3.entrytitle>a", // クラスを完全一致にするのは済の場合クラスが追加されるため
    sumiSelector = "img[src='/images/icons/sumi.png']";
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    int limit = 60; 
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
    selector = "ul>li.check a[href='https://dietnavi.com/pc/game/shindan/play.php']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      changeCloseWindow(driver);
      while (isExistEle(driver, sele0)) {
      Utille.sleep(8000);
        List<WebElement> eleList = driver.findElements(By.cssSelector(sele0));
        int size1 = eleList.size(), zumiCnt = 0;
        WebElement wEle = null;
        for (int i = 0; i < size1; i++) {
          if (isExistEle(eleList, i)) {
            if (isExistEle(eleList.get(i), sumiSelector)) {
//              if (++zumiCnt > 3) { // 新規ミッション追加時はコメント
//                break;
//              }
              continue;
            }
            wEle = eleList.get(i);
            break;
          }
        }
        if (wEle == null
//            || zumiCnt > 3
            ) {// 新規ミッション追加時はコメント
          logg.warn(mName + "]all済み");
          break;
        }
        if (isExistEle(wEle, sele1)) {
          clickSleepSelector(driver, wEle, sele1, 8000); // 遷移
//          waitTilReady(driver);
//          Utille.sleep(4000);
          Shindan.answer(driver, "", null);
        }
        else {
          break;
        }
      }
    }
  }
}
