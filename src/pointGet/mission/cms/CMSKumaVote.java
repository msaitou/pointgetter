package pointGet.mission.cms;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerSouSenkyo;

public class CMSKumaVote extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/cm/";
  final String url2 = "https://cmsite.cmnw.jp/game/";
  AnswerSouSenkyo SouSenkyo = null;

  /**
   * @param logg
   */
  public CMSKumaVote(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "くま投票");
    SouSenkyo = new AnswerSouSenkyo(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
    Utille.url(driver, url, logg);
    Utille.url(driver, url2, logg);
    
    selector = "div.menu_list.everdaycheck ul a[href*='https://dietnavi.com/pc/ad_jump.php']";
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
        recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    String sele0 = "a.start__button" //
        , sele1 = "ul.select__list>li>a" // クラスを完全一致にするのは済の場合クラスが追加されるため
        ;
    if (!isExistEle(driver, recoNoneSele, false) && isExistEle(driver, recoSele)) {
      clickSleepSelector(driver, recoSele, 2000); // 遷移
    }
//    if (isExistEle(driver, selector)) {
//      clickSleepSelector(driver, selector, 5000); // 遷移
//      changeCloseWindow(driver);
//      Utille.sleep(8000);
      // ポイントに変換
      String bankSele = "li.icon_h_bank>a",
          coinSele = "p.h_coin>em", changeSele = "#modal-open.btn",
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
            String topSele = "a>img[src*='icon_top_off.png']";
            if (isExistEle(driver, topSele)) {
              clickSleepSelector(driver, topSele, 4000);
            }
          }
        }
      }
      String[] seles = { "p>img[src*='kumakumasenkyo_pc_']",};
      for (int i = 0; i < seles.length; i++) {
        String currentSele = seles[i];
        if (isExistEle(driver, currentSele)) {
          clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
          String wid0 = driver.getWindowHandle();
          changeWindow(driver, wid0);
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
