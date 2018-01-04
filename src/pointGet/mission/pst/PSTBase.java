/**
 *
 */
package pointGet.mission.pst;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PSTBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_PST;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public PSTBase(Logger log, Map<String, String> cProps, String name) {
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
        case Define.strPSTQuiz: // ■PSTクイズ
          MisIns = new PSTQuiz(loggg, cProps);
          break;
        case Define.strPSTUranai: // ■占い
          MisIns = new PSTUranai(loggg, cProps);
          break;
        case Define.strPSTShindanAnk: // ■PST診断＆アンケート
          MisIns = new PSTShindanAnk(loggg, cProps);
          break;
        case Define.strPSTManga: // ■PSTまんが　
          MisIns = new PSTManga(loggg, cProps);
          break;
        case Define.strPSTGameParkEnk: // ■GameParkアンケート
          MisIns = new PSTGameParkEnk(loggg, cProps);
          break;
        case Define.strPSTChyousadan: // ■調査団
          MisIns = new PSTChyousadan(loggg, cProps);
          break;
        case Define.strPSTKumaVote: // ■くま投票
          MisIns = new PSTKumaVote(loggg, cProps);
          break;
        case Define.strPSTClickBanner: // ■クリックバナー
          MisIns = new PSTClickBanner(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strPSTQuiz,
          Define.strPSTShindanAnk
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
    String selector = "div.login>p.point>strong", point = "";
    driver.get("http://www.point-stadium.com/");
    Utille.sleep(5000);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
