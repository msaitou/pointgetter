package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerNaruhodo;

/**
 * @author saitou
 *
 */
public class GENNaruhodo extends GENBase {
  final String url = "http://www.gendama.jp/";
  AnswerNaruhodo Naruhodo = null;

  /**
   * @param logg
   */
  public GENNaruhodo(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "なるほど検定");
    Naruhodo = new AnswerNaruhodo(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div#dropmenu02";
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
        , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
        , preSele = "img[src*='bn_sosenkyo.png']", sele6 = "form>input.next_bt" // コラム用
    ;
    Utille.sleep(3000);

    if (isExistEle(driver, selector, false)) {
      int size0 = getSelectorSize(driver, selector);
      for (int ii = 0; ii < size0; ii++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(ii);
        String selector2 = "a[onclick*='CMくじ']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
          Utille.url(driver, cmPageUrl, logg); // CMpage
          Utille.sleep(8000);
          // ポイントに変換
          String topSele = "a>img[src*='icon_game_off.png']";
          String bankSele = "li.icon_h_bank>a", coinSele = "p.h_coin>em", changeSele = "#modal-open.btn",
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
              }
            }
          }
          if (isExistEle(driver, topSele)) {
            // 遊んで貯めるに移動
            clickSleepSelector(driver, topSele, 4000);
          }

          String selePreStart = "a p>img[src*='gotochi']", //
              startSele = "div#bottom-button img", //
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
  }
}
