package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerUranai;

public class MOPUranai extends MOPBase {
  final String url = "http://pc.moppy.jp/gamecontents/";
  AnswerUranai Uranai = null;
  /**
   * @param logg
   */
  public MOPUranai(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "星座");
    Uranai = new AnswerUranai(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "div.game_btn>div.icon>img[alt='CMくじ']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(9000);
      // ポイントに変換
      String bankSele = "li.icon_h_bank>a", coinSele = "p.h_coin>em", changeSele = "#modal-open.btn",
          changeBtnSele = "#modal-content2[style*='display: block;']>a.btn";
      if (isExistEle(driver, coinSele) ) {
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

      String uranaiSelector = "a>img[src*='bn_seiza.png']";
      if (isExistEle(driver, uranaiSelector)) {
        clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
        String wid = driver.getWindowHandle();
        changeWindow(driver, wid);
        Utille.sleep(15000);
        String selector1 = "section>div>form>input[type=image]";
        Uranai.answer(driver, selector1, wid);
      }
    }
  }
}
