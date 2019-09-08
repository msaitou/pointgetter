package pointGet.mission.cit;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerDotti;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerZukan;

public class CITCMother extends CITBase {
  final String url = "https://www.chance.com/game/";
  WebDriver driver = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;
  AnswerDotti Dotti = null;

  /**
   * @param logg
   */
  public CITCMother(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "CMその他");
    Cooking = new AnswerCooking(logg);
    PhotoEnk = new AnswerPhotoEnk(logg);
    Hyakkey = new AnswerHyakkey(logg);
    Kansatu = new AnswerKansatu(logg);
    Zukan = new AnswerZukan(logg);
    Colum = new AnswerColum(logg);
    Dotti = new AnswerDotti(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
        , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
        , preSele = "img[src*='bn_sosenkyo.png']", sele6 = "form>input.next_bt" // コラム用
    ;
    selector = "a[href*='https://www.chance.com/pjump.srv'] img[alt*='CMくじ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(8000);
      // ポイントに変換
      String topSele = "a>img[src*='icon_game_off.png']";
      String bankSele = "li.icon_h_bank>a", coinSele = "p.h_coin>em", changeSele = "#modal-open.btn", changeBtnSele = "#modal-content2[style*='display: block;']>a.btn";
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
          }
        }
      }
      if (isExistEle(driver, topSele)) {
        // 遊んで貯めるに移動
        clickSleepSelector(driver, topSele, 4000);
      }
      String[] seles = { "p>img[src*='dotti2']",//
          "p>img[src*='column_']",//
          "p>img[src*='photo_']",//
          "p>img[src*='observation_']", //
          "p>img[src*='zoo_']", //
          "p>img[src*='japan_']",//
          "p>img[src*='food']" };
      for (int i = 0; i < seles.length; i++) {
        String currentSele = seles[i];
        if (isExistEle(driver, currentSele)) {
          clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
          String wid0 = driver.getWindowHandle();
          changeWindow(driver, wid0);
          Utille.sleep(5000);
          String cUrl0 = driver.getCurrentUrl();
          while (true) {
            if (isExistEle(driver, sele0)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(sele0));
              int size1 = eleList.size(), zumiCnt = 0, targetIndex = size1 - 1;
              if (size1 > targetIndex && isExistEle(eleList, targetIndex)) {
                String wid = driver.getWindowHandle();
                String addUrl = eleList.get(targetIndex).getAttribute("href");
                Utille.exeScript(driver,"window.open()",logg); // 別ウィンドウ開く
                Utille.sleep(2000);
                changeWindow(driver, wid);
                Utille.url(driver, addUrl, logg);
                Utille.sleep(3000);
                String cUrl = driver.getCurrentUrl();

                if ((cUrl.indexOf("cosmelife.com/animal") >= 0
                    || cUrl.indexOf("/animal/") >= 0
                    || cUrl.indexOf("cmsite.fitness-health.work/animal") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Zukan.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("/observation/") >= 0
                      || cUrl.indexOf("cmsite.clutchbag.work/observation") >= 0
                    || cUrl.indexOf("cmsite.fitness-health.work/observation") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Kansatu.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/map") >= 0
                    || cUrl.indexOf("/map/") >= 0
                    || cUrl.indexOf("cmsite.fitness-health.work/map") >= 0
                //            || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Hyakkey.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("cosmelife.com/cooking") >= 0
                    || cUrl.indexOf("/cooking/") >= 0
                    || cUrl.indexOf("cmsite.fitness-health.work/cooking") >= 0
                //                || cUrl.indexOf("eyelashes-fashion.com") >= 0
                )
                    && isExistEle(driver, sele6)) {
                  Cooking.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("photo-enquete") >= 0
                    || cUrl.indexOf("/photo/") >= 0
                        || cUrl.indexOf("cmsite.clutchbag.work/photo") >= 0
                        || cUrl.indexOf("cmsite.fitness-health.work/photo") >= 0
                    || cUrl.indexOf("eyelashes-fashion.com") >= 0
                    || cUrl.indexOf("natural-vegetables.com") >= 0
                    || cUrl.indexOf("cosmeticsstyle.com") >= 0)
                    && isExistEle(driver, sele6)) {
                  PhotoEnk.answer(driver, sele6, wid);
                }
                else if ((cUrl.indexOf("/column/") >= 0
                    || cUrl.indexOf("beautynail-design.com") >= 0
                    || cUrl.indexOf("cmsite.fitness-health.work/column") >= 0
                        || cUrl.indexOf("cmsite.clutchbag.work/column/") >= 0
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
            else if ((cUrl0.indexOf("dotti2") >= 0)) {
              Dotti.answer(driver, "", wid0);
              break;
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
