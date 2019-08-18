package pointGet.mission.cms;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.parts.AnswerUranai;

public class CMSUranai extends CMSBase {
  final String url = "http://www.cmsite.co.jp/top/cm/";
  final String url2 = "https://cmsite.cmnw.jp/game/";
  AnswerUranai Uranai = null;

  /**
   * @param logg
   */
  public CMSUranai(Logger logg, Map<String, String> cProps) {
    super(logg, cProps, "星座");
    Uranai = new AnswerUranai(logg);
  }

  @Override
  public void privateMission(WebDriver driver) {
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
    String[] seles = { "p>img[src*='kumakumaseiza_pc']" };
    for (int i = 0; i < seles.length; i++) {
      String currentSele = seles[i];
      if (isExistEle(driver, currentSele)) {
        clickSleepSelector(driver, currentSele, 5000); // 遷移 全体へ
        String wid0 = driver.getWindowHandle();
        changeWindow(driver, wid0);
        Utille.sleep(5000);
        String cUrl0 = driver.getCurrentUrl();

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
}
