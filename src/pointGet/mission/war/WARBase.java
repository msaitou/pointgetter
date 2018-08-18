/**
 *
 */
package pointGet.mission.war;

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
public abstract class WARBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_WAR;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public WARBase(Logger log, Map<String, String> cProps, String name) {
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
    String selector = "span.top-UserPoint_Point", point = "";
    Utille.url(driver, "http://www.warau.jp/", loggg);
    if (!Utille.isExistEle(driver, selector, loggg)) {
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strWARDailyCheck: // ■デイリーチェッククリック
          MisIns = new WARDailyCheck(loggg, cProps);
          break;
        case Define.strWARGameParkEnk: // ■Gameparkアンケート
          MisIns = new WARGameParkEnk(loggg, cProps);
          break;
        case Define.strWARPointResearch: // ■ガチョウアンケート
          MisIns = new WARPointResearch(loggg, cProps);
          break;
        case Define.strWARMail:
          MailClicker.main(new String[] {sCode});
          break;

        default:
      }
      if (Arrays.asList(new String[] {
          Define.strWARDailyCheck,
          Define.strWARGameParkEnk,
          Define.strWARPointResearch
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
    String selector = "span.top-UserPoint_Point", point = "";
    Utille.url(driver, "http://www.warau.jp/", logg);
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
