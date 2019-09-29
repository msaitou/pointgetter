/**
 *
 */
package pointGet.mission.cit;

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
import pointGet.mission.cit.old.CITShindan;
import pointGet.mission.cit.old.CITToidas;
import pointGet.mission.parts.AnswerColum;
import pointGet.mission.parts.AnswerCooking;
import pointGet.mission.parts.AnswerDotti;
import pointGet.mission.parts.AnswerHirameki;
import pointGet.mission.parts.AnswerHyakkey;
import pointGet.mission.parts.AnswerIjin;
import pointGet.mission.parts.AnswerKansatu;
import pointGet.mission.parts.AnswerKenkou;
import pointGet.mission.parts.AnswerManga;
import pointGet.mission.parts.AnswerMix;
import pointGet.mission.parts.AnswerPhotoEnk;
import pointGet.mission.parts.AnswerPochi;
import pointGet.mission.parts.AnswerZukan;

/**
 * @author saitou
 *
 */
public abstract class CITBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_CIT;
  boolean finsishFlag = false;
  AnswerKenkou Kenkou = null;
  AnswerPhotoEnk PhotoEnk = null;
  AnswerKansatu Kansatu = null;
  AnswerCooking Cooking = null;
  AnswerHyakkey Hyakkey = null;
  AnswerZukan Zukan = null;
  AnswerColum Colum = null;
  AnswerManga Manga = null;
  AnswerHirameki Hirameki = null;
  AnswerMix Mix = null;
  AnswerIjin Ijin = null;
  AnswerDotti Dotti = null;
  AnswerPochi Pochi = null;

  /**
   * @param log
   * @param cProps
   */
  public CITBase(Logger log, Map<String, String> cProps, String name) {
    super(log, cProps);
    this.mName = "■" + sCode + name;
  }

  @Override
  public void roopMission(WebDriver driver) {
    for (int i = 0; i < 5 && !finsishFlag; i++) {
      privateMission(driver);
    }
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
    Utille.url(driver, "https://www.chance.com/", loggg);
    String recoSele = "div#cxOverlayParent>a.recommend_close", // recomend
    recoNoneSele = "#cxOverlayParent[style*='display: none']>a.recommend_close" // disabled recomend
    ;
    if (!Utille.isExistEle(driver, recoNoneSele, false, loggg)
        && Utille.isExistEle(driver, recoSele, loggg)) {
      driver.findElement(By.cssSelector(recoSele)).click();
    }
    String se = "li.user>span.user_pt";
    if (!Utille.isExistEle(driver, se, false, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strCITPriceChyosatai: // ■CITPrice調査隊
          MisIns = new CITPriceChyosatai(loggg, cProps);
          break;
        case Define.strCITShindan: // ■CIT毎日診断
          MisIns = new CITShindan(loggg, cProps);
          break;
        case Define.strCITToidas: // ■CITトイダス
          MisIns = new CITToidas(loggg, cProps);
          break;
        case Define.strCITClickBanner: // ■CITクリックバナー
          MisIns = new CITClickBanner(loggg, cProps);
          break;
        case Define.strCITGameParkEnk: // ■GameParkアンケート
          MisIns = new CITGameParkEnk(loggg, cProps);
          break;
        case Define.strCITKumaVote: // ■くま投票
          MisIns = new CITKumaVote(loggg, cProps);
          break;
        case Define.strCITPointResearch: // ■アンケート
          MisIns = new CITPointResearch(loggg, cProps);
          break;
        case Define.strCITCMother: // ■CM
          MisIns = new CITCMother(loggg, cProps);
          break;
        case Define.strCITMail:
          MailClicker.main(new String[] { "cit" });
          break;
        case Define.strCITKenkou: // ■健康
          MisIns = new CITKenkou(loggg, cProps);
          break;
        case Define.strCITManga: // ■manga
          MisIns = new CITManga(loggg, cProps);
          break;
        case Define.strCITCook: // ■料理
          MisIns = new CITCook(loggg, cProps);
          break;
        case Define.strCITEasyAnk: // ■簡単アンケート
          MisIns = new CITEasyAnk(loggg, cProps);
          break;
        case Define.strCITNaruhodo: // ■なるほど検定
          MisIns = new CITNaruhodo(loggg, cProps);
          break;
        case Define.strCITAnkPark: // ■アンケートパーク
          MisIns = new CITAnkPark(loggg, cProps);
          break;
        case Define.strCITCMPochi: // ■ぽち
          MisIns = new CITCMPochi(loggg, cProps);
          break;
        case Define.strCITGameRarry: // ■ゲームラリー
          MisIns = new CITGameRarry(loggg, cProps);
          break;
          
        case Define.strCITSassokuRead: // ■さっそく読む
          MisIns = new CITSassokuRead(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strCITPriceChyosatai,
          Define.strCITShindan,
          Define.strCITToidas,
          Define.strCITClickBanner,
          Define.strCITGameParkEnk,
          Define.strCITKumaVote,
          Define.strCITPointResearch,
          Define.strCITCMother,
          Define.strCITKenkou,
          Define.strCITManga,
          Define.strCITCook,
          Define.strCITEasyAnk,
          Define.strCITNaruhodo,
          Define.strCITAnkPark,
          Define.strCITCMPochi,
          Define.strCITGameRarry
          ,Define.strCITSassokuRead
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
      //      driver.close();
      driver.quit();
    }
  }

  /**
   *
   * @param driver
   * @param logg
   * @return
   */
  public static Double getSitePoint(WebDriver driver, Logger logg) {
    String selector = "li.user_pt>a", point = "";
    Utille.url(driver, "https://www.chance.com/", logg);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
