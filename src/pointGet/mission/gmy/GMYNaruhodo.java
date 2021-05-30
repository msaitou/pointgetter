package pointGet.mission.gmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerNaruhodo;

/**
 * @author saitou
 *
 */
public class GMYNaruhodo extends GMYBase {
  final String url = "http://dietnavi.com/pc/";
  AnswerNaruhodo Naruhodo = null;

  /**
   * @param logg
   */
  public GMYNaruhodo(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "なるほど検定");
    Naruhodo = new AnswerNaruhodo(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.menu_list.everdaycheck ul a[href*='https://dietnavi.com/pc/ad_jump.php']";
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(8000);
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

      String selePreStart = "a p>img[src*='gotochi']", //
      startSele = "div#bottom-button img",//
      a = "";

      if (isExistEle(driver, selePreStart)) {
        String wid = driver.getWindowHandle();
        clickSleepSelector(driver, selePreStart, 5000); // 遷移 全体へ
        changeCloseWindow(driver);
        Utille.sleep(5000);
        if (isExistEle(driver, startSele)) {
          Naruhodo.answer(driver, startSele, wid);
        }
      }
    }
  }
}
