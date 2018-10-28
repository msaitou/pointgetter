package pointGet.mission.cms;

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
public class CMSAnk extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/enq/";

  /**
   * @param log
   */
  public CMSAnk(Logger log, Map<String, String> cProps) {
    super(log, cProps, "トリプルアンケート");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);

    String tab3 = "li.tab_03>a";
    if (isExistEle(driver, tab3)) {
      clickSleepSelector(driver, tab3, 4000); // 遷移
      changeCloseWindow(driver);
    }

    selector = "img[src='img/q_07.png']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 4000); // 遷移
      changeCloseWindow(driver);
      logg.info("1");
      // クイズ→テキスト→写真の順にやる（デフォは写真だけど）
      String seleCate = "ul.tab_index>li#";
      String goryId[] = { "tab_quiz",
                    "tab_text", "tab_photo" 
      };
      for (int p = 0; p < goryId.length; p++) {
        selector = "div.tab_cont";
        int cnt = 0;
        while (true) {
          if (isExistEle(driver, seleCate + goryId[p])) {
            logg.info("2");
            clickSleepSelector(driver, seleCate + goryId[p], 2000);
            logg.info("3");
            String dispBlo = "[style='display: block;']";
            //							String dispBlo = "[style!='display: none;']";
            if (isExistEle(driver, selector + dispBlo)) {
              logg.info("4");
              String seleQ = "td.td05>a>span";
              WebElement targetTbl = driver.findElement(By.cssSelector(selector + dispBlo));
              if (isExistEle(targetTbl, seleQ)) {
                logg.info("5");
                List<WebElement> webEle = targetTbl.findElements(By.cssSelector(seleQ));

                if (isExistEle(webEle, webEle.size() - 1)) {// 最後尾の選択しから
                  logg.info("6");
                  clickSleepSelector(driver, webEle, webEle.size() - 1, 4000);

                  String seleNext = "form[name='form1'] input.btn";
                  if (isExistEle(driver, seleNext)) {
                    clickSleepSelector(driver, seleNext, 4000);

                    // 以下より10問
                    String choiceSele = "label.adchange";
                    String nextSele2 = "p.btm>input.btn";
                    String nextSele3 = "p.top>input.btn";
                    for (int i = 0; i < 10; i++) {
                      List<WebElement> eleChoice = driver
                          .findElements(By.cssSelector(choiceSele));
                      int choiceies = eleChoice.size();
                      //											int choiceies = getSelectorSize(driver, choiceSele);
                      int choiceNum = Utille.getIntRand(choiceies);
                      if (isExistEle(driver, choiceSele) && isExistEle(eleChoice, choiceNum)) {
                        clickSleepSelector(driver, eleChoice, choiceNum, 4000); // 選択肢を選択
                        boolean a = driver.findElement(By.cssSelector(nextSele2))
                            .isDisplayed();
                        if (a) {
                          if (isExistEle(driver, nextSele2)) {
                            clickSleepSelector(driver, nextSele2, 4000);
                            //                            Utille.clickRecaptha(driver, logg);
                            a = driver.findElement(By.cssSelector(nextSele2))
                                .isDisplayed();
                            if (a) {
                              if (isExistEle(driver, nextSele2)) { // 答え合わせ
                                clickSleepSelector(driver, nextSele2, 4000); // 次の問題
                              }
                            }
                            else {
                              if (isExistEle(driver, nextSele3)) { // 答え合わせ
                                clickSleepSelector(driver, nextSele3, 4000); // 次の問題
                              }
                            }
                          }
                        }
                        else {
                          if (isExistEle(driver, nextSele3)) {
                            clickSleepSelector(driver, nextSele3, 4000);
                            //                            Utille.clickRecaptha(driver, logg);
                            a = driver.findElement(By.cssSelector(nextSele2))
                                .isDisplayed();
                            if (a) {
                              if (isExistEle(driver, nextSele2)) { // 答え合わせ
                                clickSleepSelector(driver, nextSele2, 4000); // 次の問題
                              }
                            }
                            else {
                              if (isExistEle(driver, nextSele3)) { // 答え合わせ
                                clickSleepSelector(driver, nextSele3, 4000); // 次の問題
                              }
                            }
                          }
                        }
                      }
                    }

                    String overLaySele = "div#mdl_area[style*='display: block;'] div#mdl_close";
                    // overlayを消して
                    checkOverlay(driver, overLaySele);
                    if (isExistEle(driver, seleNext)) {
                      clickSleepSelector(driver, seleNext, 4000);
                      String toList = "p.btn>a";
                      if (isExistEle(driver, toList)) {
                        clickSleepSelector(driver, toList, 4000);
                      }
                    }
                  }
                }
              }
              else {
                break;
              }
              //							// DEBUG TODO
              //							if (cnt++ == 2) {
              //								break;
              //							}
            }
          }
        }
      }
    }
  }
}
