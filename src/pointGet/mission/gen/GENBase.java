/**
 *
 */
package pointGet.mission.gen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class GENBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_GEN;

  /**
   * @param log
   * @param cProps
   */
  public GENBase(Logger log, Map<String, String> cProps, String name) {
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
    // // ■もりもり囲め
    // Mission GENMorimoriKakome = new GENMorimoriKakome(logg, commonProps);
    // GENMorimoriKakome.exePrivateMission(driver);
    // if (testFlag) {
    // return;
    // }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strGENPointStar: // ■ポイントの森(star)
          MisIns = new GENPointStar(loggg, cProps);
          break;
        case Define.strGENClickBanner: // ■ポイントの森(クリック)
          MisIns = new GENClickBanner(loggg, cProps);
          break;
        case Define.strGENShindan: // ■毎日診断
          MisIns = new GENShindan(loggg, cProps);
          break;
        case Define.strGENUranai: // ■占い
          MisIns = new GENUranai(loggg, cProps);
          break;
        case Define.strGENChirachi: // ■チラシ
          MisIns = new GENChirachi(loggg, cProps);
          break;
        case Define.strGENManga: // ■漫画
          MisIns = new GENManga(loggg, cProps);
          break;
        case Define.strGENPointResearch: // ■ポイントアンケート
          MisIns = new GENPointResearch(loggg, cProps);
          break;
        case Define.strGENPointResearch2: // ■ポイントアンケート2
          MisIns = new GENPointResearch2(loggg, cProps);
          break;

        default:
      }
      if (Arrays.asList(new String[] { Define.strGENPointStar,
          Define.strGENClickBanner,
          Define.strGENShindan,
          Define.strGENUranai,
          Define.strGENChirachi,
          Define.strGENManga,
          Define.strGENPointResearch,
          Define.strGENPointResearch2
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
      loggg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
    } finally {
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
    String selector = "li#user_point01>a>span", point = "";
    driver.get("http://www.gendama.jp/");
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
