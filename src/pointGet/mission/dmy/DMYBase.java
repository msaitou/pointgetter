/**
 *
 */
package pointGet.mission.dmy;

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
public abstract class DMYBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_DMY;

  /**
   * @param log
   * @param cProps
   */
  public DMYBase(Logger log, Map<String, String> cProps, String name) {
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
    String sel = "div.p-dotmoney_meta";
    driver.get("https://www.ameba.jp/");
    if (!Utille.isExistEle(driver, sel, loggg)) { // ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strDMYChirachi: // ■チラシ
          MisIns = new DMYChirachi(loggg, cProps);
          break;
        case Define.strDMYPriceChyosatai: // ■price調査隊
          MisIns = new DMYPriceChyosatai(loggg, cProps);
          break;
        case Define.strDMYPointResearch: // ■アンケート
          MisIns = new DMYPointResearch(loggg, cProps);
          break;
        case Define.strDMYScrachi: // ■スクラッチ
          MisIns = new DMYScrachi(loggg, cProps);
          break;
//        case Define.strDMYChyousadan: // ■調査団
//        MisIns = new DMYChyousadan(loggg, cProps);
//        break;
//        case Define.strDMYKumaVote: // ■総選挙
//        MisIns = new DMYKumaVote(loggg, cProps);
//        break;
        default:
      }

      if (Arrays.asList(new String[] {
          Define.strDMYChirachi,
          Define.strDMYPriceChyosatai,
          Define.strDMYPointResearch,
          Define.strDMYScrachi,
//          Define.DMYScrachi,
//        Define.strDMYChyousadan,
//        Define.strDMYKumaVote,

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
    String selector = "div.p-history-list__list-item-first span.u-ff-dmoney", point = "";
    driver.get("https://d-money.jp/history");
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
