/**
 *
 */
package pointGet.mission.pic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PICBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_PIC;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public PICBase(Logger log, Map<String, String> cProps, String name) {
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
    String sel = "ul.header_wrap.clearfix>li.right>span.name";
    driver.get("http://pointi.jp/"); // http://pointi.jp/
    if (!Utille.isExistEle(driver, sel, loggg)) { // ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strPICClickBanner: // ■PICクリックバナー
          MisIns = new PICClickBanner(loggg, cProps);
          break;
        case Define.strPICUranai: // ■占い
          MisIns = new PICUranai(loggg, cProps);
          break;
        case Define.strPICPriceChyosatai: // ■PICPrice調査隊
          MisIns = new PICPriceChyosatai(loggg, cProps);
          break;
        case Define.strPICShindan: // ■PIC毎日診断
          MisIns = new PICShindan(loggg, cProps);
          break;
        case Define.strPICPointResearch: // ■ポイントリサーチ
          MisIns = new PICPointResearch(loggg, cProps);
          break;
        case Define.strPICChyousadan: // ■調査団
          MisIns = new PICChyousadan(loggg, cProps);
          break;
        case Define.strPICKumaVote: // ■くま投票
          MisIns = new PICKumaVote(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strPICClickBanner,
          Define.strPICUranai,
          Define.strPICPriceChyosatai,
          Define.strPICShindan,
          Define.strPICPointResearch,
          Define.strPICChyousadan,
          Define.strPICKumaVote
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
    String selector = "p.text.point", point = "";
    driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
    if (!Utille.isExistEle(driver, selector, logg)) {
      // login!!
      LoginSite.login(sCode, driver, logg);
      driver.get("http://pointi.jp/my/my_page.php"); // http://pointi.jp/
    }
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
