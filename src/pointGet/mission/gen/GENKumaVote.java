package pointGet.mission.gen;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerSouSenkyo;

public class GENKumaVote extends GENBase {
  final String url = "http://www.gendama.jp/";
  AnswerSouSenkyo SouSenkyo = null;

  /**
   * @param logg
   */
  public GENKumaVote(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "くま投票");
    SouSenkyo = new AnswerSouSenkyo(logg);
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
    // div#dropmenu01
    driver.get(url);
    selector = "div#dropmenu01";
    if (isExistEle(driver, selector, false)) {
      int size = getSelectorSize(driver, selector);
      for (int i = 0; i < size; i++) {
        WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
        String selector2 = "a[onclick*='CMくじ']";
        if (isExistEle(e, selector2)) {
          if (!isExistEle(e, selector2)) {
            break;
          }
          String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
          driver.get(cmPageUrl); // CMpage
          Utille.sleep(8000);
          String sele0 = "a.start__button" //
          , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
          , preSele = "img[src*='bn_sosenkyo.png']";
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

          if (isExistEle(driver, preSele)) {
            clickSleepSelector(driver, preSele, 5000); // 遷移 全体へ
            changeCloseWindow(driver);
            Utille.sleep(5000);
            if (isExistEle(driver, sele0)) {
              clickSleepSelector(driver, sele0, 5000); // 遷移 全体へ
              while (isExistEle(driver, sele1)) {
                List<WebElement> eleList = driver.findElements(By.cssSelector(sele1));
                int size1 = eleList.size(), zumiCnt = 0, targetIndex = 0;
                if (isExistEle(eleList, targetIndex)) {
                  Utille.scrolledPage(driver, eleList.get(targetIndex));
                  clickSleepSelector(driver, eleList, targetIndex, 4000); // 遷移
                  SouSenkyo.answer(driver, "", null);
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
  }
}
