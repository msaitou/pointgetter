/**
 *
 */
package pointGet.mission.sug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
public abstract class SUGBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_SUG;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public SUGBase(Logger log, Map<String, String> cProps, String name) {
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
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strSUGQuiz: // ■SUGクイズ
          MisIns = new SUGQuiz(loggg, cProps);
          break;
        case Define.strSUGQuiz2: // ■SUGクイズ2
          MisIns = new SUGQuiz2(loggg, cProps);
          break;
        case Define.strSUGUranai: // ■占い
          MisIns = new SUGUranai(loggg, cProps);
          break;
        case Define.strSUGManga: // ■漫画
          MisIns = new SUGManga(loggg, cProps);
          break;
        case Define.strSUGColum: // ■コラム
          MisIns = new SUGColum(loggg, cProps);
          break;
        case Define.strSUGPointResearch: // ■ポイントサーチ
          MisIns = new SUGPointResearch(loggg, cProps);
          break;
        case Define.strSUGPointResearch2: // ■アンケート
          MisIns = new SUGPointResearch2(loggg, cProps);
          break;
        case Define.strSUGClickBanner: // ■クリックバナー
          MisIns = new SUGClickBananer(loggg, cProps);
          break;
        case Define.strSUGChyousadan: // ■調査団
          MisIns = new SUGChyousadan(loggg, cProps);
          break;
        case Define.strSUGKumaVote: // ■くま投票
          MisIns = new SUGKumaVote(loggg, cProps);
          break;
        case Define.strSUGGetPark: // ■ゲームパーク
          MisIns = new SUGGetPark(loggg, cProps);
          break;
        case Define.strSUGGetParkNoCap: // ■ゲームパーク
          MisIns = new SUGGetParkNoCap(loggg, cProps);
          break;
        case Define.strSUGCMother: // ■CM
          MisIns = new SUGCMother(loggg, cProps);
          break;
        case Define.strSUGAnkPark: // ■アンケートパーク
          MisIns = new SUGAnkPark(loggg, cProps);
          break;
        case Define.strSUGMail:
          MailClicker.main(new String[] {sCode});
          break;
        default:
      }
      if (Arrays.asList(new String[] {
          "",
          //          Define.strSUGQuiz,
          //          Define.strSUGQuiz2,
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
    String selector = "span#user-mile-status-earn", point = "";
    Utille.url(driver, "http://www.sugutama.jp/passbook", logg);
    Utille.sleep(5000);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
