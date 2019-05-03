package pointGet.mission.lfm;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerDotti;

public class LFMCMother extends LFMBase {
  final String url = "http://lifemedia.jp/game/";
  WebDriver driver = null;
  AnswerDotti Dotti = null;

  /**
   * @param logg
   */
  public LFMCMother(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "CMそのた");
    Dotti = new AnswerDotti(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "img[alt='CMじゃんけん']";
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
    , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
    ;
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
      //          "p>img[src*='column_']",//
          //          "p>img[src*='photo_']",//
          //          "p>img[src*='observation_']", //
          //          "p>img[src*='zoo_']", //
          //          "p>img[src*='japan_']",//
          //          "p>img[src*='food']" 
      };
      for (int i = 0; i < seles.length; i++) {
        String currentSele = seles[i];
        if (isExistEle(driver, currentSele)) {
          clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
          String wid0 = driver.getWindowHandle();
          changeWindow(driver, wid0);
          Utille.sleep(5000);
          String cUrl0 = driver.getCurrentUrl();
          while (true) {
            if ((cUrl0.indexOf("dotti2") >= 0)) {
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
