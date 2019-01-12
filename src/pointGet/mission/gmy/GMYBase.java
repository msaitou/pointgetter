/**
 *
 */
package pointGet.mission.gmy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.LoginSite;
import pointGet.MailClicker;
import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class GMYBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_GMY;

  /**
   * @param log
   * @param cProps
   */
  public GMYBase(Logger log, Map<String, String> cProps, String name) {
    super(log, cProps);
    this.mName = "■" + sCode + name;
  }

  @Override
  public void roopMission(WebDriver driver) {
  }

  @Override
  public void privateMission(WebDriver driver) {
  }

  /**
   *
   * @param loggg
   * @param cProps
   * @param missions
   */
  public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions, Dbase Dbase) {
    WebDriver driver = getWebDriver(cProps);
    Utille.url(driver, "https://dietnavi.com/pc/", loggg);
    //    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    //        recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    //    ;
    //    if (!Utille.isExistEle(driver, recoNoneSele, false, loggg)
    //        && Utille.isExistEle(driver, recoSele, loggg)) {
    //      driver.findElement(By.cssSelector(recoSele)).click();
    //    }
    String se = "li.user_poin>at";
    if (!Utille.isExistEle(driver, se, false, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strGMYShindan: // ■毎日診断
          MisIns = new GMYShindan(loggg, cProps);
          break;
        case Define.strGMYClickBanner: // ■clipoバナー
          MisIns = new GMYClickBanner(loggg, cProps);
          break;
        case Define.strGMYChirachi: // ■チラシ
          MisIns = new GMYChirachi(loggg, cProps);
          break;
        case Define.strGMYPriceChyosatai: // ■プライス調査隊
          MisIns = new GMYPriceChyosatai(loggg, cProps);
          break;
        case Define.strGMYToidas: // ■GMYトイダス
          MisIns = new GMYToidas(loggg, cProps);
          break;
        case Define.strGMYPointResearch: // ■アンケート
          MisIns = new GMYPointResearch(loggg, cProps);
          break;
        case Define.strGMYGameParkEnk: // ■GameParkアンケート
          MisIns = new GMYGameParkEnk(loggg, cProps);
          break;
        case Define.strGMYKumaVote: // ■くま投票
          MisIns = new GMYKumaVote(loggg, cProps);
          break;
        case Define.strGMYCMother: // ■CMその他
          MisIns = new GMYCMother(loggg, cProps);
          break;
        case Define.strGMYMail:
          MailClicker.main(new String[] { sCode });
          break;
        case Define.strGMYColum: // ■健康
          MisIns = new GMYColum(loggg, cProps);
          break;
        case Define.strGMYManga: // ■漫画
          MisIns = new GMYManga(loggg, cProps);
          break;
        case Define.strGMYEasyAnk: // ■簡単アンケート
          MisIns = new GMYEasyAnk(loggg, cProps);
          break;
        case Define.strGMYNaruhodo: // ■なるほど検定
          MisIns = new GMYNaruhodo(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strGMYShindan,
          Define.strGMYClickBanner,
          Define.strGMYChirachi,
          Define.strGMYPriceChyosatai,
          Define.strGMYToidas,
          Define.strGMYPointResearch,
          Define.strGMYGameParkEnk,
          Define.strGMYKumaVote,
          Define.strGMYCMother,
          Define.strGMYColum, Define.strGMYManga,
          Define.strGMYEasyAnk,
          Define.strGMYNaruhodo
      }).contains(mission)) {
        driver = MisIns.exePrivateMission(driver);
      }
    }
    // point状況確認
    try {
      Double p = getSitePoint(driver, loggg);
      PointsCollection PC = new PointsCollection(Dbase);
      Map<String, Double> pMap = new HashMap<String, Double>() {
        /**
        *
        */
        private static final long serialVersionUID = 1L;
        {
          put(sCode, p);
        }
      };
      PC.putPointsData(pMap);
    } catch (Throwable e) {
      loggg.error("[" + sCode + "");
      loggg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
    } finally {
      driver.quit();
      //      driver.close();
    }
  }

  /**
   *
   * @param driver
   * @param logg
   * @return
   */
  public static Double getSitePoint(WebDriver driver, Logger logg) {
    String selector = "li.user_point>a", point = "";
    Utille.url(driver, "https://dietnavi.com/pc", logg);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
