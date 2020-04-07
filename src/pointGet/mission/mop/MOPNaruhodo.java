package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerNaruhodo;

public class MOPNaruhodo extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";
  AnswerNaruhodo Naruhodo = null;

  /**
   * @param logg
   */
  public MOPNaruhodo(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "なるほど検定");
    Naruhodo = new AnswerNaruhodo(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    selector = "img[alt='CMくじ']";
    Utille.url(driver, url, logg);

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

      String selePreStart = "a p>img[src*='gotochi']", //
      closeSele = "div#popup-ad button.popup-close", //
      closeSeleNone = "div#popup-ad[style*='display: none;'] button.popup-close", //
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
