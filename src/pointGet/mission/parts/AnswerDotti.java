package pointGet.mission.parts;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.MissCommon;

public class AnswerDotti extends MissCommon {
  /**
   * constracter
   *
   * @param log4j object
   */
  public AnswerDotti(Logger log) {
    logg = log;
  }

  /**
   *
   * @param startSele
   * @param wid
   */
  public void answer(WebDriver driver, String startSele, String wid) {
    logg.info("■□■□■□[" + this.getClass().getName() + "]■□■□■□");
    String startsele = "#Content p>a[href*='jump']", //
    closeSele = "div#gn_interstitial_area img#gn_interstitial_close_icon", //
    choiceSele = "div.listbox>dl>dt", //
    seleAaa = "div#aaa", //
    seleBbb = "div#bbb", //
    sele1 = "div>a[href*='jump']", //
    //    sele2 = "div#bbb a", //
    //    sele3 = "div#aaa a", //
    sele4 = "a[href*='top']>p", //
    choiceSele2 = "div#graph-object label", //
    septSele1 = "a[href*='p/ex']", //
    septSele2 = "p.btn_pink>a[href='/s']", //
    nextLevel = "a[href*='jump']", //
    seeYouSele = "img[src*='seeyou.png']", //

    a = "";
    boolean endFlag = false;
    if (isExistEle(driver, startsele)) {
      for (int i = 0; i < 3 || !endFlag; i++) {
        List<WebElement> eleList = driver.findElements(By.cssSelector(startsele));
        clickSleepSelector(driver, eleList.get(i), 4000);

        if (isExistEle(driver, septSele1)) {
          // インターセプト
          clickSleepSelector(driver, septSele1, 4000);
        }
        else if (isExistEle(driver, septSele2)) {
          // インターセプト
          clickSleepSelector(driver, septSele2, 4000);
          if (isExistEle(driver, sele4)) {
            // 一覧へ
            clickSleepSelector(driver, sele4, 4000);
          }
        }

        for (int j = 0; j < 20 || !endFlag; j++) {
          // 質問の選択一覧
          if (isExistEle(driver, closeSele)) {
            //       if (driver.findElement(By.cssSelector("div#popup-ad")).isDisplayed() && isExistEle(driver, closeSele)) {
            checkOverlay(driver, closeSele, false);
          }
          if (isExistEle(driver, choiceSele)) {
            // 質問の選択
            List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
            if (eleList2.size() == 0) {
              break;
            }
            clickSleepSelector(driver, eleList2.get(eleList2.size() - 1), 4000);

            String aaaSele = seleAaa + " " + sele1, //
            bbbSele = seleBbb + " " + sele1//
            , aaaSeleA = seleAaa + " a" //
            , bbbSeleA = seleBbb + " a" //
            , nextSele1 = ""//
            , nextSele2 = ""//
            , nextSele3 = ""//
            ;
            if (driver.findElement(By.cssSelector(seleAaa)).isDisplayed() && isExistEle(driver, aaaSele)) {
              nextSele1 = aaaSele;
            }
            else {
              nextSele1 = bbbSele;
            }
            if (isExistEle(driver, nextSele1)) {
              clickSleepSelector(driver, nextSele1, 4000);

              if (isExistEle(driver, choiceSele2)) {
                List<WebElement> eleList3 = driver.findElements(By.cssSelector(choiceSele2));
                int choiceNum = Utille.getIntRand(eleList3.size());
                clickSleepSelector(driver, eleList3.get(choiceNum), 3000);
                if (driver.findElement(By.cssSelector(seleAaa)).isDisplayed() && isExistEle(driver, aaaSeleA)) {
                  nextSele2 = aaaSeleA;
                }
                else {
                  nextSele2 = bbbSeleA;
                }
                if (isExistEle(driver, nextSele2)) {
                  clickSleepSelector(driver, nextSele2, 7000);

                  if (driver.findElement(By.cssSelector(seleAaa)).isDisplayed() && isExistEle(driver, aaaSeleA)) {
                    nextSele3 = aaaSeleA;
                  }
                  else {
                    nextSele3 = bbbSeleA;
                  }

                  if (isExistEle(driver, nextSele3)) {
                    clickSleepSelector(driver, nextSele3, 3000);
                    if (isExistEle(driver, sele4)) {
                      // 質問の選択一覧に行くはず
                      clickSleepSelector(driver, sele4, 3000);
                      if (isExistEle(driver, septSele1)) {
                        // インターセプト
                        clickSleepSelector(driver, septSele1, 4000);
                        if (isExistEle(driver, sele4)) {
                          // 質問の選択一覧に行くはず
                          clickSleepSelector(driver, sele4, 3000);
                        }
                      }
                    }
                  }
                }
              }
            }
          }
          else {
            i++;
            if (isExistEle(driver, seeYouSele)) {
              //              clickSleepSelector(driver, seeYouSele, 3000);
              j = 0;
              endFlag = true;
              break;
            }
            // 次のレベルに移動
            else if (isExistEle(driver, nextLevel)) {
              j = 0;
              // 質問の選択一覧に行くはず
              clickSleepSelector(driver, nextLevel, 3000);
            }
          }
        }

      }
    }

    Utille.sleep(5000);
    driver.close();
    driver.switchTo().window(wid);
  }
}
