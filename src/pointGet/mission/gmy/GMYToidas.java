package pointGet.mission.gmy;

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
public class GMYToidas extends GMYBase {
  final String url = "http://dietnavi.com/pc/";

  /**
   * @param logg
   */
  public GMYToidas(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "トイダス");
  }

  @Override
  public void privateMission(WebDriver driver) {
    selector = "div.everyday_list1 ul>li>a[href='http://dietnavi.com/pc/ad_jump.php?id=42431']";
    driver.get(url);
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "div#cxOverlayParent>a.recommend_close" // disabled recomend
    ;
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }

    int cntt = 0;
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 7500); // 遷移
      changeCloseWindow(driver);
      while (true) {
        driver.switchTo().defaultContent();
        Utille.sleep(5000);
        //				selector = "li.col-md-4.col-sm-6.entry-item.category-trivia.checked";	// が取得済み
        selector = "li[class='col-md-4 col-sm-6 entry-item category-trivia ']";
        if (!isExistEle(driver, selector)) {
          break;
        }
        List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
        int size1 = eleList.size();
        WebElement wEle = null;
        for (int i = 0; i < size1; i++) {
          if (isExistEle(eleList, i)) {
            if (isExistEle(eleList.get(i), "div.entry-date")) {
              String entryDate = eleList.get(i).findElement(By.cssSelector("div.entry-date")).getText();
              String nowDate = Utille.getNowTimeStr("yyyy/MM/dd");
              logg.warn("entryDate:" + entryDate + " nowDate:" + nowDate);
              if (cntt++ < 2) {
                //							if (entryDate.equals(nowDate)) {
                //							if (true) {
                wEle = eleList.get(i);
                break;
              }
              break;
            }
          }
        }
        if (wEle == null) {
          break;
        }
        selector = "div.entry-text-wrap a";
        if (isExistEle(wEle, selector)) {
          clickSleepSelector(driver, wEle, selector, 8000); // 遷移
          //					this.waitTilReady(driver);
          //	$('div#pager', top.frames[1].document.body)
          driver.switchTo().frame(0);
          selector = "div#pager";// 始める
          Utille.sleep(15000);
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 10000); // 遷移
            //						this.waitTilReady(driver);
            String[] sele2s = {
                "div.toidas-question-answer[transition='fade'] div.toidas-question-answer-checkbox-img",
                "div.toidas-question-answer.fade-transition[style='opacity: 1;'] div.toidas-question-answer-checkbox-img"
            };
            for (String sele2 : sele2s) {
              if (isExistEle(driver, sele2)) {
                for (int i = 0; i < 10; i++) {
                  if (isExistEle(driver, sele2)) {
                    List<WebElement> eleList2 = driver.findElements(By.cssSelector(sele2));
                    int size = eleList2.size();
                    int ran1 = Utille.getIntRand(size);
                    if (isExistEle(eleList2, ran1)) {
                      clickSleepSelector(driver, eleList2, ran1, 2000);// 選択
                      String nextSelector = selector;
                      if (isExistEle(driver, nextSelector)) {
                        clickSleepSelector(driver, nextSelector, 2000); // 遷移
                        if (isExistEle(driver, nextSelector)) {
                          clickSleepSelector(driver, nextSelector, 6000); // 遷移
                        }
                      }
                    }
                  }
                }
                Utille.sleep(3000);
                clickSleepSelector(driver, selector, 5000); // pointgett
                logg.warn(mName + "]1つクリア！！");
                clickSleepSelector(driver, selector, 8000); // 一覧に戻る
                break;
              }
              else {
                logg.warn(mName + "]sele2なし");
              }
            }
          }
          else {
            logg.warn(mName + "]始められない？");
          }
        }
        else {
          logg.warn(mName + "]all済み");
          break;
        }
        logg.warn(mName + "]roopします");
      }
    }
  }
}
