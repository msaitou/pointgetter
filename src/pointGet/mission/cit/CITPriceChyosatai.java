package pointGet.mission.cit;

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
 * 4時更新
 */
public class CITPriceChyosatai extends CITBase {
  final String url = "https://www.chance.com/game/";
  private String overlaySelector = "div#popup[style*='display: block'] a.modal_close";
  private String footBnrSelector = "div.foot-bnr a.close>span";
  private String footBnrNoneSele = "div.foot-bnr[style*='display :none'] a.close>span";
  private String overlayNone = "div.foot-bnr[style*='display :none'] a.close>span";
  private String recoSele = "div#cxOverlayParent>a.recommend_close"; // recomend
  private String recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close"; // disabled recomend

  /**
   * @param log
   */
  public CITPriceChyosatai(Logger log, Map<String, String> cProps) {
    super(log, cProps, "調査隊パーク");
  }

  @Override
  public void privateMission(WebDriver driver) {
    String firstEntrySele = "img[alt='調査隊パーク']";
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
    Utille.url(driver, url, logg);

    if (isExistEle(driver, firstEntrySele)) {
      clickSleepSelector(driver, firstEntrySele, 4000); // 遷移
      changeCloseWindow(driver);
      String[] chousaSeles = {
          "a[href='/go/tokimeki'] strong", // ときめき
          "a[href='/go/recipe'] strong", // レシピ
          "a[href='/go/price'] strong", // プライス
      };
      for (int i = 0; i < chousaSeles.length; i++) {
        clickSleepSelector(driver, chousaSeles[i], 4000); // 遷移
        if (chousaSeles[i].equals("a[href='/go/price'] strong")) {
          logg.info("■□■□■□[price]■□■□■□");
          String toTop = "div.back-top>a[href='/']";

          for (int j = 0; j < 6; j++) {
            Utille.sleep(2000);
            checkOverlay(driver, overlaySelector);
            if (isExistEle(driver, footBnrSelector)
                && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
              checkOverlay(driver, footBnrSelector);
            }

            String noEntrySele = "div.thumbnail span.icon-noentry";
            String entrySele = "div.thumbnail span.icon-entry";
            if (isExistEle(driver, noEntrySele)) {
              clickSleepSelector(driver, noEntrySele, 4000); // 遷移
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, overlayNone, false)) {
                checkOverlay(driver, footBnrSelector);
              }

              selector = "div.thumb-start div.btn>a";
              if (isExistEle(driver, selector)) {
                clickSleepSelector(driver, selector, 4000); // 遷移
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                String finshSele = "div.result-area";
                // otukare!
                if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
                  break;
                }
                localRoop1(driver);
                // 調査隊のトップへ戻る
                if (isExistEle(driver, toTop)) {
                  clickSleepSelector(driver, toTop, 4500);
                }

              }
            }
            else if (isExistEle(driver, entrySele)) {
              clickSleepSelector(driver, entrySele, 4000); // 遷移
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, overlayNone, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              //          selector = "div.btn>a";
              localRoop1(driver);
              // 調査隊のトップへ戻る
              if (isExistEle(driver, toTop)) {
                clickSleepSelector(driver, toTop, 4500);
              }
            }
            else {
              j = 6;
            }
          }
        }
        else if (chousaSeles[i].equals("a[href='/go/tokimeki'] strong")) {
          logg.info("■□■□■□[tokimeki]■□■□■□");
          String noEntrySele = "div.thumbnail span.icon-noentry";
          String entrySele = "div.thumbnail span.icon-entry";
          String finshSele = "div.finish-area";
          String toTop = "span>a[href='/']";
          for (int j = 0; j < 6; j++) {

            if (isExistEle(driver, noEntrySele)) {
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              clickSleepSelector(driver, noEntrySele, 3000); // 遷移

              selector = "div.thumb-start div.btn>a";
              if (isExistEle(driver, selector)) {
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                clickSleepSelector(driver, selector, 4000); // 遷移
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                selector = "span.icon-arrow";
                for (int ii = 0; ii < 10; ii++) {
                  int ran = Utille.getIntRand(2);
                  if (isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                    // otukare!
                    if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
                      if (isExistEle(driver, selector)) {
                        clickSleepSelector(driver, selector, 4500);
                      }
                      break;
                    }
                    clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
                    checkOverlay(driver, overlaySelector);
                    if (isExistEle(driver, footBnrSelector)
                        && !isExistEle(driver, footBnrNoneSele, false)) {
                      checkOverlay(driver, footBnrSelector);
                    }
                  }
                }
              }
              // 調査隊のトップへ戻る
              if (isExistEle(driver, toTop)) {
                clickSleepSelector(driver, toTop, 4500);
              }
            }
            else if (isExistEle(driver, entrySele)) {
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              clickSleepSelector(driver, entrySele, 3000); // 遷移
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              //          selector = "div.btn>a";
              selector = "span.icon-arrow";
              for (int ii = 0; ii < 10; ii++) {
                // otukare!
                if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
                  if (isExistEle(driver, selector)) {
                    clickSleepSelector(driver, selector, 4500);
                  }
                  break;
                }
                int ran = Utille.getIntRand(2);
                Utille.sleep(2000);
                if (isExistEle(driver.findElements(By.cssSelector(selector)))
                    && isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                  clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
                  checkOverlay(driver, overlaySelector);
                  if (isExistEle(driver, footBnrSelector)
                      && !isExistEle(driver, footBnrNoneSele, false)) {
                    checkOverlay(driver, footBnrSelector);
                  }
                }
              }
              // 調査隊のトップへ戻る
              if (isExistEle(driver, toTop)) {
                clickSleepSelector(driver, toTop, 4500);
              }
            }
            else {
              j = 6;
            }
          }
        }
        else if (chousaSeles[i].equals("a[href='/go/recipe'] strong")) {
          logg.info("■□■□■□[recipe]■□■□■□");
          String noEntrySele = "div.thumbnail span.icon-noentry";
          String entrySele = "div.thumbnail span.icon-entry";
          String finshSele = "div.result-area";
          String toTop = "div.back-top>a[href='/']";
          for (int j = 0; j < 6; j++) {
            if (isExistEle(driver, noEntrySele)) {
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              clickSleepSelector(driver, noEntrySele, 3000); // 遷移
              selector = "div.thumb-start div.btn>a";
              if (isExistEle(driver, selector)) {
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                clickSleepSelector(driver, selector, 4000); // 遷移
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                selector = "span.icon-arrow";
                for (int ii = 0; ii < 7; ii++) {
                  // otukare!
                  if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
                    if (isExistEle(driver, selector)) {
                      clickSleepSelector(driver, selector, 4500);
                    }
                    break;
                  }
                  int ran = Utille.getIntRand(2);
                  Utille.sleep(2000);
                  if (isExistEle(driver.findElements(By.cssSelector(selector)))
                      && isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                    clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
                    String fixSele = "div.btn.mrg-t5.mrg-b5 a";
                    if (isExistEle(driver.findElements(By.cssSelector(fixSele)))) {
                      clickSleepSelector(driver, fixSele, 3000);
                      if (isExistEle(driver, footBnrSelector)
                          && !isExistEle(driver, footBnrNoneSele, false)) {
                        checkOverlay(driver, footBnrSelector);
                        if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
                          clickSleepSelector(driver, selector, 3000);
                        }
                      }
                    }
                  }
                }
                // 調査隊のトップへ戻る
                if (isExistEle(driver, toTop)) {
                  clickSleepSelector(driver, toTop, 4500);
                }
              }
            }
            else if (isExistEle(driver, entrySele)) {
              checkOverlay(driver, overlaySelector);
              if (isExistEle(driver, footBnrSelector)
                  && !isExistEle(driver, footBnrNoneSele, false)) {
                checkOverlay(driver, footBnrSelector);
              }
              clickSleepSelector(driver, entrySele, 3000); // 遷移
              selector = "div.thumb-start div.btn>a";
              if (isExistEle(driver, selector)) {
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                clickSleepSelector(driver, selector, 4000); // 遷移
                checkOverlay(driver, overlaySelector);
                checkOverlay(driver, footBnrSelector);
                selector = "span.icon-arrow";
                for (int ii = 0; ii < 6; ii++) {
                  // otukare!
                  if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
                    if (isExistEle(driver, selector)) {
                      clickSleepSelector(driver, selector, 4500);
                    }
                    break;
                  }
                  int ran = Utille.getIntRand(2);
                  Utille.sleep(2000);
                  if (isExistEle(driver.findElements(By.cssSelector(selector)))
                      && isExistEle(driver.findElements(By.cssSelector(selector)), ran)) {
                    clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 3000);
                    String fixSele = "div.btn.mrg-t5.mrg-b5 a";
                    if (isExistEle(driver.findElements(By.cssSelector(fixSele)))) {
                      clickSleepSelector(driver, fixSele, 3000);
                      if (isExistEle(driver, footBnrSelector)
                          && !isExistEle(driver, footBnrNoneSele, false)) {
                        checkOverlay(driver, footBnrSelector);
                        if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
                          clickSleepSelector(driver, selector, 3000);
                        }
                      }
                    }
                  }
                }
                // 調査隊のトップへ戻る
                if (isExistEle(driver, toTop)) {
                  clickSleepSelector(driver, toTop, 4500);
                }
              }
            }
            else {
              j = 6;
            }
          }
          String toParkSele = "a[href='https://www.chosatai-park.net/']";
          if (isExistEle(driver, toParkSele)) {
            clickSleepSelector(driver, toParkSele, 6000);
          }
        }

        //      Utille.url(driver, "https://dietnavi.com/pc/game/price/play.php", logg);
        Utille.sleep(7000);
        String exchangeSele = "span.btn-exchange>a", //
        exchageListSele = "select[name='point']", doExchangeSele = "div.btn-area>button[type='submit']";
        if (isExistEle(driver, exchangeSele)) {
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, "div.foot-bnr[style*='display :none'] a.close>span")) {
            checkOverlay(driver, footBnrSelector);
          }
          clickSleepSelector(driver, exchangeSele, 6000);
          if (isExistEle(driver, exchageListSele)) {
            int size = getSelectorSize(driver, exchageListSele + ">option");
            String value = driver.findElements(By.cssSelector(exchageListSele + ">option"))
                .get(size - 1).getAttribute("value");
            Select selectList = new Select(driver.findElement(By.cssSelector(exchageListSele)));
            selectList.selectByValue(value); // 交換ポイントを選択
            Utille.sleep(3000);
            for (int k = 0; k < 2; k++) {
              if (isExistEle(driver, doExchangeSele)) {
                clickSleepSelector(driver, doExchangeSele, 6000); // i=1 交換する　i=2 本当に
              }
            }
          }
        }
      }
    }
  }

  private void localRoop1(WebDriver driver) {
    selector = "span.icon-arrow";
    String sele2top = "div#popup";
    String sele2none = "[style*='display:none;']";
    String sele2bot = " div.btn.mrg-t5.mrg-b5>a";
    String overlayNone = "div.foot-bnr[style*='display :none'] a.close>span";
    for (int i = 0; i < 6; i++) {
      String finshSele = "div.result-area";
      // otukare!
      if (isExistEle(driver.findElements(By.cssSelector(finshSele)))) {
        if (isExistEle(driver, selector)) {
          clickSleepSelector(driver, selector, 4500);
        }
        break;
      }
      int ran = Utille.getIntRand(2);
      List<WebElement> elems = driver.findElements(By.cssSelector(selector));
      if (isExistEle(elems, ran)) {
        Utille.scrolledPage(driver, elems.get(ran));
        clickSleepSelector(driver, elems, ran, 2000);
        for (int g = 0; g < 10; g++) {
          if (isExistEle(driver, sele2top + sele2bot)
              && !isExistEle(driver, sele2top + sele2none + sele2bot, false)) {
            break;
          }
          Utille.sleep(1000);
          if (g == 9) {
            clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), ran, 2000);
          }
        }
        if (isExistEle(driver, sele2top + sele2bot)) {
          clickSleepSelector(driver, sele2top + sele2bot, 3000);
          checkOverlay(driver, overlaySelector);
          if (isExistEle(driver, footBnrSelector)
              && !isExistEle(driver, overlayNone, false)) {
            checkOverlay(driver, footBnrSelector);
          }
          if (isExistEle(driver, selector)) {
            clickSleepSelector(driver, selector, 4500);
            waitTilReady(driver);
            if (isExistEle(driver, footBnrSelector)
                && !isExistEle(driver, overlayNone, false)) {
              checkOverlay(driver, footBnrSelector);
            }
          }
        }
      }
    }
  }

}
