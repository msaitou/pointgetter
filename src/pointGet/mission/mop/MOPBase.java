/**
 *
 */
package pointGet.mission.mop;

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
import pointGet.mission.mop.old.MOPChirachi;
import pointGet.mission.mop.old.MOPChyosatai;
import pointGet.mission.mop.old.MOPCountTimer;
import pointGet.mission.mop.old.MOPKanji;
import pointGet.mission.mop.old.MOPManga;
import pointGet.mission.mop.old.MOPShindan;
import pointGet.mission.pto.PTOGameRarry;

/**
 * @author saitou
 *
 */
public abstract class MOPBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_MOP;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public MOPBase(Logger log, Map<String, String> cProps, String name) {
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
    Utille.url(driver, "http://pc.moppy.jp/", loggg);
    String se = "div#preface>ul.pre__login__inner";
    if (!Utille.isExistEle(driver, se, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strMOPQuiz: // ■モッピークイズ
          MisIns = new MOPQuiz(loggg, cProps);
          break;
        case Define.strMOPNanyoubi: // ■MOP何曜日
          MisIns = new MOPNanyoubi(loggg, cProps);
          break;
        case Define.strMOPAnzan: // ■MOP暗算
          MisIns = new MOPAnzan(loggg, cProps);
          break;
        case Define.strMOPEnglishTest: // ■英単語TEST
          MisIns = new MOPEnglishTest(loggg, cProps);
          break;
        case Define.strMOPCountTimer: // ■CountTimer
          MisIns = new MOPCountTimer(loggg, cProps);
          break;
        case Define.strMOPManga: // ■漫画
          MisIns = new MOPManga(loggg, cProps);
          break;
        case Define.strMOPPointResearch: // ■ポイントリサーチ
          MisIns = new MOPPointResearch(loggg, cProps);
          break;
        case Define.strMOPClickBanner: // ■クリックで貯める
          MisIns = new MOPClickBanner(loggg, cProps);
          break;
        case Define.strMOPShindan: // ■毎日診断
          MisIns = new MOPShindan(loggg, cProps);
          break;
        case Define.strMOPChyosatai: // ■トキメキ調査隊
          MisIns = new MOPChyosatai(loggg, cProps);
          break;
        case Define.strMOPUranai: // ■MOP星座
          MisIns = new MOPUranai(loggg, cProps);
          break;
        case Define.strMOPChirachi: // ■MOPチラシ
          MisIns = new MOPChirachi(loggg, cProps);
          break;
        case Define.strMOPPointResearch2: // ■ポイントリサーチ２
          MisIns = new MOPPointResearch2(loggg, cProps);
          break;
        case Define.strMOPMiniGameEnk: // ■ポイントリサーチ２
          MisIns = new MOPMiniGameEnk(loggg, cProps);
          break;
        case Define.strMOPMiniGameEnkNoCap: // ■ポイントリサーチ２
          MisIns = new MOPMiniGameEnkNoCap(loggg, cProps);
          break;
        case Define.strMOPChyousadan: // ■調査団
          MisIns = new MOPChyousadan(loggg, cProps);
          break;
        case Define.strMOPKumaVote: // ■くま投票
          MisIns = new MOPKumaVote(loggg, cProps);
          break;
        case Define.strMOPGaingame: // ■リーグジュエル
          MisIns = new PTOGameRarry(loggg, cProps);
          break;
        case Define.strMOPKanji: // ■漢字テスト
          MisIns = new MOPKanji(loggg, cProps);
          break;
        case Define.strMOPCMother: // ■CMサイトその他
          MisIns = new MOPCMother(loggg, cProps);
          break;
        case Define.strMOPAnkPark: // ■アンケートパーク
          MisIns = new MOPAnkPark(loggg, cProps);
          break;
        case Define.strMOPCMPochi: // ■ぽち
          MisIns = new MOPCMPochi(loggg, cProps);
          break;
        case Define.strMOPNaruhodo: // ■なるほど
          MisIns = new MOPNaruhodo(loggg, cProps);
          break;
        case Define.strMOPMail:
          MailClicker.main(new String[] {sCode});
          break;
        default:
      }
      if (Define.strMOPMail.equals(mission)) {
        // skip
      }
      else if (Arrays.asList(new String[] { Define.strMOPQuiz,
          Define.strMOPNanyoubi,
          Define.strMOPAnzan,
          Define.strMOPEnglishTest,
          Define.strMOPCountTimer,
          Define.strMOPKanji,
      }).contains(mission)) {
        driver = MisIns.exeRoopMission(driver);
      }
      else {
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
   * ポイント確認
   * @param driver
   * @param logg
   * @return
   */
  public static Double getSitePoint(WebDriver driver, Logger logg) {
    String selector = "div#preface>ul.pre__login__inner", point = "0", secondPoint = "0";
    Utille.url(driver, "http://pc.moppy.jp/", logg);
    if (!Utille.isExistEle(driver, selector, logg)) {
      // login!!
      LoginSite.login(Define.PSITE_CODE_MOP, driver, logg);
    }
    selector = "div#point_blinking strong";
    Utille.url(driver, "http://pc.moppy.jp/bankbook/", logg);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    selector = "div#point_blinking em";
    if (Utille.isExistEle(driver, selector, logg)) {
      secondPoint = driver.findElement(By.cssSelector(selector)).getText();
      secondPoint = Utille.getNumber(secondPoint);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    sTotal = Utille.sumTotal("secondPoint", secondPoint, sTotal);
    return sTotal;
  }
}
