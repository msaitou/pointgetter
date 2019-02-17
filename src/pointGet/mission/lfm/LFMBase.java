/**
 *
 */
package pointGet.mission.lfm;

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
public abstract class LFMBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_LFM;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public LFMBase(Logger log, Map<String, String> cProps, String name) {
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
    String sel = "div.point span.F5";
    Utille.url(driver, "http://lifemedia.jp/", loggg);
    String sele = "em#mempoint";
    if (!Utille.isExistEle(driver, sele, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strLFMChyosatai: // ■調査隊
          MisIns = new LFMChyosatai(loggg, cProps);
          break;
        case Define.strLFMGameParkEnk: // ■GameParkアンケート
          MisIns = new LFMGameParkEnk(loggg, cProps);
          break;
        case Define.strLFMPriceChyosatai: // ■プライス調査隊
          MisIns = new LFMPriceChyosatai(loggg, cProps);
          break;
        case Define.strLFMQuiz: // ■クイズ
          MisIns = new LFMQuiz(loggg, cProps);
          break;
        case Define.strLFMQuiz2: // ■クイズ2
          MisIns = new LFMQuiz2(loggg, cProps);
          break;
        case Define.strLFMUranai: // ■占い
          MisIns = new LFMUranai(loggg, cProps);
          break;
//        case Define.strLFMPointResearch: // ■アンケート
//          MisIns = new LFMPointResearch(loggg, cProps);
//          break;
//        case Define.strLFMPointResearch2: // ■アンケート2
//          MisIns = new LFMPointResearch2(loggg, cProps);
//          break;
        case Define.strLFMFarmEnk: // ■ファームアンケ―と
          MisIns = new LFMFarmEnk(loggg, cProps);
          break;
        case Define.strLFMFarmEnkNoCap: // ■ファームアンケ―と
          MisIns = new LFMFarmEnkNoCap(loggg, cProps);
          break;
        case Define.strLFMChyousadan: // ■調査団
          MisIns = new LFMChyousadan(loggg, cProps);
          break;
        case Define.strLFMKumaVote: // ■総選挙
          MisIns = new LFMKumaVote(loggg, cProps);
          break;
        case Define.strLFMKenkou: // ■サラサラ健康コラム
          MisIns = new LFMKenkou(loggg, cProps);
          break;
        case Define.strLFMMail:
          MailClicker.main(new String[] {sCode});
          break;
        case Define.strLFMNaruhodo: // ■なるほど検定
          MisIns = new LFMNaruhodo(loggg, cProps);
          break;
        case Define.strLFMCMother: // ■その他アンケート等
          MisIns = new LFMCMother(loggg, cProps);
          break;
        case Define.strLFMQuizKentei: // ■クイズ検定
          MisIns = new LFMQuizKentei(loggg, cProps);
          break;
          
        default:
      }
      if (Arrays.asList(new String[] {
          Define.strLFMChyosatai,
          Define.strLFMGameParkEnk
//          , Define.strLFMQuiz
//          , Define.strLFMQuiz2
          , Define.strLFMUranai
          , Define.strLFMPointResearch
          , Define.strLFMPointResearch2
          , Define.strLFMFarmEnk
          , Define.strLFMChyousadan
          , Define.strLFMKumaVote
          , Define.strLFMGameParkEnk
          , Define.strLFMKenkou
          ,Define.strLFMFarmEnkNoCap
          ,Define.strLFMPriceChyosatai
          ,Define.strLFMNaruhodo
          ,Define.strLFMCMother
          ,Define.strLFMQuizKentei
      }).contains(mission)) {
        driver = MisIns.exePrivateMission(driver);
      }
      else {
        driver = MisIns.exeRoopMission(driver);
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
    String selector = "em#mempoint", point = "";
    Utille.url(driver, "http://lifemedia.jp/", logg);
    if (!Utille.isExistEle(driver, selector, logg)) {
      // login!!
      LoginSite.login(sCode, driver, logg);
    }
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
