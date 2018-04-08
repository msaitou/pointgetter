package pointGet.mission.pto;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerZukan;

public class PTOCMother extends PTOBase {
  final String url = "http://www.pointtown.com/";
  WebDriver driver = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;

  /**
   * @param logg
   */
  public PTOCMother(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "CMその他");
    Cooking = new AnswerCooking(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Colum = new AnswerColum(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "section.ptpc-panel.ptpc-panel--cmkuji>a>img";
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
        , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
        , preSele = "img[src*='bn_chosa.png']", sele6 = "form>input.next_bt" // コラム用
    ;
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(8000);
      // ポイントに変換
      String bankSele = "li.icon_h_bank>a",
          coinSele = "p.h_coin>em", changeSele = "#modal-open.btn",
          changeBtnSele = "#modal-content2[style*='display: block;']>a.btn";
      if (isExistEle(driver, coinSele)) {
        String coins = driver.findElement(By.cssSelector(coinSele)).getText();
        int icoins = Integer.parseInt(Utille.getNumber(coins));
        if (icoins >= 100) {
          if (isExistEle(driver, bankSele)) {
            clickSleepSelector(driver, bankSele, 5000); // 遷移
            if (isExistEle(driver, changeSele)) {
              clickSleepSelector(driver, changeSele, 5000);
              if (isExistEle(driver, changeBtnSele)) {
                clickSleepSelector(driver, changeBtnSele, 5000);
              }
            }
            String topSele = "a>img[src*='icon_top_off.png']";
            if (isExistEle(driver, topSele)) {
              clickSleepSelector(driver, topSele, 4000);
            }
          }
        }
      }
      String[] seles = { "a>img[src*='column_bn.png']", "a>img[src*='photo_bn.png']",
          "a>img[src*='observation_bn.png']", "a>img[src*='zoo_bn.png']", "a>img[src*='japan_bn.png']",
          "a>img[src*='food_bn.png']" };
      for (int i = 0; i < seles.length; i++) {
        String currentSele = seles[i];
        if (isExistEle(driver, currentSele)) {
          clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
          String wid0 = driver.getWindowHandle();
          changeWindow(driver, wid0);
          Utille.sleep(5000);
          while (true) {
            if (isExistEle(driver, sele0)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(sele0));
              int size1 = eleList.size(), zumiCnt = 0, targetIndex = size1 - 1;
              if (size1 > targetIndex && isExistEle(eleList, targetIndex)) {
                String wid = driver.getWindowHandle();
                Utille.scrolledPage(driver, eleList.get(targetIndex));
                Utille.sleep(1000);
                Actions actions = new Actions(driver);
                actions.keyDown(Keys.CONTROL);
                actions.click(eleList.get(targetIndex));
                actions.perform();
                Utille.sleep(5000);
                //                clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ
                changeWindow(driver, wid);
                Utille.sleep(3000);
                String cUrl = driver.getCurrentUrl();

                if ((cUrl.indexOf("cosmelife.com/animal") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Zukan.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/observation") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Kansatu.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/map") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Hyakkey.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/cooking") >= 0
                //                || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Cooking.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("photo-enquete") >= 0
                    || cUrl.indexOf("cosmelife.com/photo") >= 0
                    || cUrl.indexOf("eyelashes-fashion.com") >= 0
                    || cUrl.indexOf("natural-vegetables.com") >= 0
                    || cUrl.indexOf("cosmeticsstyle.com") >= 0)
                    && isExistEle(driver, sele6)) {
                  PhotoEnk.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/column") >= 0
                    || cUrl.indexOf("beautynail-design.com") >= 0
                    || cUrl.indexOf("style-cutehair.com") >= 0
                    || cUrl.indexOf("eyelashes-fashion.com") >= 0
                    || cUrl.indexOf("fashion-cosmelife.com") >= 0)
                    && isExistEle(driver, sele6)) {
                  Colum.answer(driver, sele6, wid);
                }
                else {
                  //                  skip++;
                  driver.close();
                  driver.switchTo().window(wid);
                }
                Utille.refresh(driver, logg);
                Utille.sleep(7000);
              }
            }
            else {
              driver.close();
              driver.switchTo().window(wid0);
              break;
            }
          }
        }
      }

    }
  }
}
