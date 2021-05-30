package pointGet.mission.cms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerDotti;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerZukan;

public class CMSCMother extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/cm/";
  final String url2 = "https://cmsite.cmnw.jp/game/";

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
  public CMSCMother(Logger logg, Map<String, String> cProps) {
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
    Utille.url(driver, url2, logg);

    selector = "div.menu_list.everdaycheck ul a[href*='https://dietnavi.com/pc/ad_jump.php']";
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
    , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
    , preSele = "img[src*='bn_sosenkyo.png']", sele6 = "form>input.next_bt" // コラム用
    ;
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
    // オーバーレイアンケート
    String seleOverAnk = "div#modal-content[style*='display: block']";
    if (isExistEle(driver, seleOverAnk)) {
      WebElement seleOverEle = driver.findElement(By.cssSelector(seleOverAnk));
      String sexSele = "label[for='radio01']", ageSele = "select[name='age']", //
          prefSele = "select[name='pref']", marrigeSele = "label[for='radio04']", 
          childSele = "label[for='radio06']", ansSele = "button[type='submit']",//
      // 
      a;
      clickSleepSelector(driver, seleOverEle, sexSele, 1000); // 性別：男性
      Select selectList = new Select(driver.findElement(By.cssSelector(ageSele)));
      selectList.selectByValue("36"); // 年齢：36歳
      selectList = new Select(driver.findElement(By.cssSelector(prefSele)));
      selectList.selectByValue("13"); // 都道府県：東京と
      clickSleepSelector(driver, seleOverEle, marrigeSele, 1000); // 結婚：無
      clickSleepSelector(driver, seleOverEle, childSele, 1000); // 子供：無

      if (isExistEle(driver, ansSele)) {
        // 次へ
        clickSleepSelector(driver, seleOverEle, ansSele, 4000);
      }
    }
    
    
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
//              driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL +"t");
//              
//              Utille.scrolledPage(driver, eleList.get(targetIndex));
//              Utille.sleep(1000);
//              Actions actions = new Actions(driver);
//              actions.keyDown(Keys.CONTROL);
//              actions.click(eleList.get(targetIndex));
//              actions.perform();

              
//              String listPageUrl = driver.getCurrentUrl();
//              listPageUrl = listPageUrl.substring(0, listPageUrl.lastIndexOf("/") +1);
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
          // test
          //            driver.close();
          //            driver.switchTo().window(wid0);
          //            break;
        }
      }
    }

    //    }
  }
}
