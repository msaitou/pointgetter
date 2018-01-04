/**
 * 
 */
package pointGet.mission.cri;

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
public abstract class CRIBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_CRI;

  /**
   * 
   * @param log
   * @param cProps
   * @param name
   */
  public CRIBase(Logger log, Map<String, String> cProps, String name) {
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
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strCRIAnk: // ■アンケート
          MisIns = new CRIAnk(loggg, cProps);
          break;
        case Define.strCRIManga: // ■漫画
          MisIns = new CRIManga(loggg, cProps);
          break;
        case Define.strCRIClickBananer: // ■クリックバナー
          MisIns = new CRIClickBananer(loggg, cProps);
          break;
        case Define.strCRIPointResearch: // ■クリックバナー
          MisIns = new CRIPointResearch(loggg, cProps);
          break;
        case Define.strCRIChyousadan: // ■調査団
          MisIns = new CRIChyousadan(loggg, cProps);
          break;
        case Define.strCRIKumaVote: // ■くま投票
          MisIns = new CRIKumaVote(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strCRIAnk,
          Define.strCRIManga, Define.strCRIClickBananer,
          Define.strCRIPointResearch,
          Define.strCRIChyousadan,
          Define.strCRIKumaVote
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
    String selector = "li.p_menu.point>a", point = "";
    driver.get("http://www.chobirich.com/");
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
