package pointGet.mission.gpo;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerPochi;

public class GPOCMPochi extends GPOBase {
  final String url = "https://www.gpoint.co.jp/gpark/";
  WebDriver driver = null;
  AnswerPochi Pochi = null;

  /**
   * @param logg
   */
  public GPOCMPochi(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "CMぽちっと");
    Pochi = new AnswerPochi(logg);
  }

  @Override
  public void privateMission(WebDriver driverAtom) {
    driver = driverAtom;
    Utille.url(driver, url, logg);
    selector = "img[alt='CMくじ']";
    String sele0 = "a.ui-btn.ui-btn-a" // アンケート一覧の回答するボタン
        , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
        , preSele = "img[src*='bn_sosenkyo.png']"
    ;
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 5000); // 遷移
      Utille.sleep(5000);
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
      String[] seles = { "p>img[src*='pochitto_pc']"};
      for (int i = 0; i < seles.length; i++) {
        String currentSele = seles[i];
        if (isExistEle(driver, currentSele)) {
          clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
          String wid0 = driver.getWindowHandle();
          changeCloseWindow(driver);
          Utille.sleep(5000);
          String cUrl0 = driver.getCurrentUrl();
          int skip = 0, beforeSize = -1;
          selector = "dl#question>dd>a>p"; // アンケート一覧の回答するボタン
          String seleNextb2 = "form>input[type='image']";
          String sele6 = "div.btn_send>a>p", //
          b;

          while (isExistEle(driver, selector)) {
            if (isExistEle(driver, selector)) {
              List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
              int size = eleList.size(), targetIndex = skip;
              if (beforeSize == size) {
                skip++;
              }
              logg.info("size:" + size + " target:" + targetIndex);
              if (size > targetIndex &&
                  targetIndex >= 0 && isExistEle(eleList, targetIndex)) {
                Utille.scrolledPage(driver, driver.findElements(By.cssSelector(selector)).get(targetIndex));
                clickSleepSelector(driver, eleList, targetIndex, 3000); // アンケートスタートページ

                String wid = driver.getWindowHandle();
                String cUrl = driver.getCurrentUrl();
                logg.info("url[" + cUrl + "]");
                if (
               isExistEle(driver, sele6)) {
                  Pochi.answer(driver, sele6, wid);
                }
                else {
                  break;
                }
              }
            }
            else {
              break;
            }
          }

        }
      }
    }
  }
}
