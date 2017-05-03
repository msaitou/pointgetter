package pointGet.mission.i2i;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.val;
import pointGet.Utille;
import pointGet.mission.parts.AnswerColum;

/**
 * @author saitou
 *
 */
public class I2IColum extends I2IBase {
  final String url = "https://point.i2i.jp/special/freepoint/";
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public I2IColum(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "コラム");
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    driver.get(url);
    selector = "li.pointfreeList_item";
    if (isExistEle(driver, selector)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "img[src='/img/special/freepoint/column_340_120.png']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String wid = driver.getWindowHandle();
          clickSleepSelector(e, selector2, 5000);
          // アラートをけして
          val alert = driver.switchTo().alert();
          alert.accept();
          Utille.sleep(5000);
//          changeCloseWindow(driver);

          changeWindow(driver, wid);

          selector = "td.status>a.ui-btn.ui-btn-a"; // アンケート一覧の回答するボタン
          String seleNext = "form>input.next_bt";
          String seleNextb2 = "form>input[type='image']";
          //					String seleNextb2 = "form>input[alt='次へ進む']";
          String seleNextb3 = "form>input[alt='next']";
          String overLay = "div#interstitial[style*='display: block']>div>div#inter-close";
          while (true) {
            if (isExistEle(driver, selector)) {
              Colum.answer(driver, selector, wid);
              driver.navigate().refresh();
              Utille.sleep(5000);
//              clickSleepSelector(driver, selector, 3000); // 遷移　問開始
//              if (isExistEle(driver, seleNext)) {
//                clickSleepSelector(driver, seleNext, 5000); // 遷移　問開始
//                for (int g = 0; g < 2; g++) {
//                  if (isExistEle(driver, seleNextb2)) {
//                    clickSleepSelector(driver, seleNextb2, 3000); // 遷移　問開始するよ
//                  }
//                }
//                Utille.sleep(7000);
//                // 6page
//                for (int g = 0; g < 6; g++) {
//                  if (isExistEle(driver, seleNextb3)) {
//                    clickSleepSelector(driver, seleNextb3, 13000); // 遷移　問開始するよ
//                  }
//                }
//                checkOverlay(driver, overLay);
//                if (isExistEle(driver, seleNextb2)) {
//                  clickSleepSelector(driver, seleNextb2, 5000); // 遷移　問開始するよ
//                }
//                String choiceSele = "input[type='radio']";
//                String seleNext2 = "div>input.enquete_nextbt";
//                String seleNext3 = "div>input.enquete_nextbt_2";
//                String seleSele = "form.menu>select";
//                // 6問
//                for (int k = 1; k <= 6; k++) {
//                  checkOverlay(driver, overLay);
//                  int choiceNum = 0;
//                  if (isExistEle(driver, choiceSele)) {
//                    int choiceies = getSelectorSize(driver, choiceSele);
//                    switch (k) {
//                      case 1:
//                        // 1問目は1：男
//                        break;
//                      case 2:
//                      case 3:
//                        // 2問目は3：30代
//                        // 3問目は3：会社員
//                        if (choiceies > 2) {// 一応選択可能な範囲かをチェック
//                          choiceNum = 2;
//                        }
//                        break;
//                      default:
//                        choiceNum = Utille.getIntRand(choiceies);
//                    }
//                    List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
//                    if (isExistEle(eleList2, choiceNum)) {
//                      // 選択
//                      clickSleepSelector(eleList2.get(choiceNum), 4000);
//                      if (isExistEle(driver, seleNext2)) {
//                        // 次へ
//                        clickSleepSelector(driver, seleNext2, 4000);
//                      }
//                    }
//                  } else if (isExistEle(driver, seleSele)) {
//                    int size3 = getSelectorSize(driver, seleSele + ">option");
//                    choiceNum = Utille.getIntRand(size3);
//                    String value = driver.findElements(By.cssSelector(seleSele + ">option"))
//                        .get(choiceNum).getAttribute("value");
//                    Select selectList = new Select(driver.findElement(By.cssSelector(seleSele)));
//                    //										selectList.deselectByIndex(choiceNum);
//                    selectList.selectByValue(value);
//                    if (isExistEle(driver, seleNext3)) {
//                      // 次へ
//                      clickSleepSelector(driver, seleNext3, 4000);
//                    }
//                  } else {
//                    break;
//                  }
//                }
//                if (isExistEle(driver, overLay)) {
//                  checkOverlay(driver, overLay);
//                }
//                if (isExistEle(driver, seleNextb2)) {
//                  clickSleepSelector(driver, seleNextb2, 3000); // 遷移　問開始するよ
//                }
//                String finishSele = "div#again_bt>a>img";
//                if (isExistEle(driver, overLay)) {
//                  checkOverlay(driver, overLay);
//                }
//                if (isExistEle(driver, finishSele)) {
//                  clickSleepSelector(driver, finishSele, 3000); // 遷移
//                  // 一覧に戻るはず
//                }
//              }
            }
            else {
              break;
            }
          }
          break; // 星座はこれで終了
        }
      }
    }
  }
}
