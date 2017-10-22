package pointGet.mission.i2i;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class I2IMangaVer2 extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";

  /**
   * @param logg
   */
  public I2IMangaVer2(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "漫画Ver2");
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "li.pointfreeList_item";
    if (isExistEle(driver, selector)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "img[src='/img/special/freepoint/manga_340_120.png']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          clickSleepSelector(e, selector2, 5000);
          // アラートをけして
          checkAndAcceptAlert(driver);
          Utille.sleep(2000);
          changeCloseWindow(driver);

          selector = "a.ui-btn.ui-btn-a"; // 回答する
          while (true) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              if (isExistEle(eleList, 0)) {
                clickSleepSelector(eleList, 0, 5000); // 遷移
                String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
                String seleNext = "form>input[type='image']";
                String seleNexttugi = seleNext + "[src='common/image/9999/manga_next_bt.png']";
                for (int g = 0; g < 8;) {
                  checkOverlay(driver, overLay, false);
                  if (isExistEle(driver, seleNexttugi)) {
                    clickSleepSelector(driver, seleNexttugi, 5000); // 遷移
                    g++;
                  }
                  else if (isExistEle(driver, seleNext)) {
                    clickSleepSelector(driver, seleNext, 4000); // 遷移
                    g++;
                  }
                  else {
                    driver.navigate().refresh();
                    Utille.sleep(5000);
                  }
                }
                Utille.sleep(6000);
                String choiceSele = "div.enquete>p>label";
                //								String choiceSele = "input[type='radio']";
                String seleNext2 = "div>input.enquete_nextbt";
                String seleNext3 = "div>input.enquete_nextbt_2";
                String seleSele = "form.menu>select";
                // 8問
                for (int k = 1; k <= 8; k++) {
                  checkOverlay(driver, overLay);
                  int choiceNum = 0;
                  if (isExistEle(driver, choiceSele)) {
                    int choiceies = getSelectorSize(driver, choiceSele);
                    switch (k) {
                      case 1:
                        // 1問目は1：男
                        break;
                      case 2:
                      case 3:
                        // 2問目は3：30代
                        // 3問目は3：会社員
                        if (choiceies > 2) {// 一応選択可能な範囲かをチェック
                          choiceNum = 2;
                        }
                        break;
                      default:
                        choiceNum = Utille.getIntRand(choiceies);
                    }
                    List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
                    if (isExistEle(eleList2, choiceNum)) {
                      // 選択
                      clickSleepSelector(eleList2, choiceNum, 3500);
                      if (isExistEle(driver, seleNext2)) {
                        // 次へ
                        clickSleepSelector(driver, seleNext2, 6000);
                      }
                    }
                  }
                  else if (isExistEle(driver, seleSele)) {
                    int size3 = getSelectorSize(driver, seleSele + ">option");
                    choiceNum = Utille.getIntRand(size3);
                    String value = driver.findElements(By.cssSelector(seleSele + ">option"))
                        .get(choiceNum).getAttribute("value");
                    Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
                    //										selectList.deselectByIndex(choiceNum);
                    selectList.selectByValue(value);
                    if (isExistEle(driver, seleNext3)) {
                      // 次へ
                      clickSleepSelector(driver, seleNext3, 4000);
                    }
                  }
                  else {
                    break;
                  }
                }
                checkOverlay(driver, overLay, false);
                Utille.clickRecaptha(driver, logg);
                if (isExistEle(driver, seleNext)) {
                  clickSleepSelector(driver, seleNext, 3000); // 遷移
                }
                String finishSele = "div#again_bt>a>img";
                if (isExistEle(driver, overLay)) {
                  checkOverlay(driver, overLay, false);
                }
                if (isExistEle(driver, finishSele)) {
                  clickSleepSelector(driver, finishSele, 3000); // 遷移
                  // 一覧に戻るはず
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
  }
}
