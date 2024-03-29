package pointGet.mission.pst;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

public class PSTUranai extends PSTBase {
  final String url = "http://www.point-stadium.com/wcmpoint.asp";

  /**
   * @param logg
   */
  public PSTUranai(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "星座");
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    selector = "form[name='ItemList']>p>input[name='entry']";
    if (isExistEle(driver, selector)) {
      clickSleepSelector(driver, selector, 6000); // 遷移
      changeCloseWindow(driver);
      Utille.sleep(8000);
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
        changeCloseWindow(driver);
        Utille.sleep(12000);
        selector = "div#parts-slide-button__action a>img"; // 占い始める
        String selector1 = "section>div>form>input[type=image]",
            sele2= "div.start-signs>a>img[alt='さそり座']";
        String selectList[] = { selector, selector1 ,sele2};
        for (int g = 0; g < selectList.length; g++) {
          if (isExistEle(driver, selectList[g])) {
            clickSleepSelector(driver, selectList[g], 3000); // 遷移
            String nextSelector = "div#next-button a>img";
            String symbleSelector = "div#symbols-next-button a>img";
            boolean endFlag = false;
            for (int j = 0; j < 20; j++) { // 20に特に意味なし
              // エンドレスループを避けるため
              // overlayを消して
              if (!isExistEle(driver, "div#inter-ad[style*='display: none'] div#inter-ad-close", false)) {
                Utille.sleep(3000);
                if (isExistEle(driver, "div#inter-ad div#inter-ad-close")) {
                  checkOverlay(driver, "div#inter-ad div#inter-ad-close", false);
                  endFlag = true;
                }
              }

              if (isExistEle(driver, nextSelector)) {
                clickSleepSelector(driver, nextSelector, 3000); // 遷移
              }
              else if (isExistEle(driver, selector)) {
                clickSleepSelector(driver, selector, 3000); // 遷移
              }
              else if (isExistEle(driver, symbleSelector)) {
                clickSleepSelector(driver, symbleSelector, 3000); // 遷移
              }
              else if (isExistEle(driver, selector1)) {
                clickSleepSelector(driver, selector1, 3000); // 遷移
              }
              Utille.sleep(3000);
              if (endFlag) {
                break;
              }
            }
          }
        }
      }
    }
  }
}
