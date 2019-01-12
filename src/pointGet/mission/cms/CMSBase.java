/**
 *
 */
package pointGet.mission.cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.LoginSite;
import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class CMSBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_CMS;
  boolean finsishFlag = false;
  public static String topUrl = "http://www.cmsite.co.jp/top/home/";

  /**
   *
   * @param log
   * @param cProps
   * @param name
   */
  public CMSBase(Logger log, Map<String, String> cProps, String name) {
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
    String loginSele = "p.menbertxt>span";
    Utille.url(driver, topUrl, loggg);
    if (!Utille.isExistEle(driver, loginSele, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strCMSPointResearch: // ■ポイントアンケート
          MisIns = new CMSPointResearch(loggg, cProps);
          break;
        case Define.strCMSAnk: // ■お楽しみアンケート
          MisIns = new CMSAnk(loggg, cProps);
          break;
        case Define.strCMSGameParkEnk: // ■ゲームパークアンケート
          MisIns = new CMSGameParkEnk(loggg, cProps);
          break;
        case Define.strCMSQuiz: // ■クイズ
          MisIns = new CMSQuiz(loggg, cProps);
          break;
        case Define.strCMSCMother: // ■CMアンケート
          MisIns = new CMSCMother(loggg, cProps);
          break;
        case Define.strCMSUranai: // ■占い
          MisIns = new CMSUranai(loggg, cProps);
          break;
        case Define.strCMSFarmEnk: // ■ファーム
          MisIns = new CMSFarmEnk(loggg, cProps);
          break;
        case Define.strCMSFarmEnkNoCap: // ■ファームnocap
          MisIns = new CMSFarmEnkNoCap(loggg, cProps);
          break;
        case Define.strCMSPochi: // ■ぽちっと調査隊
          MisIns = new CMSPochi(loggg, cProps);
          break;
        case Define.strCMSAnqueate: // ■ぽちっと調査隊
          MisIns = new CMSAnqueate(loggg, cProps);
          break;
        case Define.strCMSNaruhodo: // ■なるほど検定
          MisIns = new CMSNaruhodo(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strCMSAnqueate,
          Define.strCMSFarmEnk, Define.strCMSUranai,
          Define.strCMSCMother,
          Define.strCMSQuiz,
          Define.strCMSGameParkEnk,
          Define.strCMSAnk,
          Define.strCMSPochi,
          Define.strCMSPointResearch,Define.strCMSKumaVote,
          Define.strCMSNaruhodo
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
      loggg.error("["+sCode+"");
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
    String selector = "p.menbertxt>span", point = "";
    Utille.url(driver, topUrl, logg);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
