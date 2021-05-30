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

import pointGet.LoginSite;
import pointGet.MailClicker;
import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;
import pointGet.mission.pic.old.PICChyousadan;
import pointGet.mission.pic.old.PICFarmEnk;
import pointGet.mission.pic.old.PICFarmEnkNoCap;
import pointGet.mission.pic.old.PICGameRarry;
import pointGet.mission.pic.old.PICKumaVote;
import pointGet.mission.pic.old.PICShindan;

/**
 * @author saitou
 *
 */
public abstract class PICBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_PIC;
  public static String baseUrl = "https://pointi.jp/";
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
    Utille.url(driver, baseUrl, loggg);
    //    if (!LoginSite.isLongin(sCode, driver, loggg)) { // ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
    //      LoginSite.login(sCode, driver, loggg);
    //    }
    LoginSite.login(sCode, driver, loggg);
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
        case Define.strPICInterview: // ■インタビュー
          MisIns = new PICInterview(loggg, cProps);
          break;
        case Define.strPICMedalMool: // ■メダルモール
          MisIns = new PICMedalMool(loggg, cProps);
          break;
        case Define.strPICFarmEnk: // ■ポイントファーム
          MisIns = new PICFarmEnk(loggg, cProps);
          break;
        case Define.strPICFarmEnkNoCap: // ■ポイントファーム
          MisIns = new PICFarmEnkNoCap(loggg, cProps);
          break;
        case Define.strPICMail:
          MailClicker.main(new String[] { sCode });
          break;
        case Define.strPICNaruhodo: // ■なるほど検定
          MisIns = new PICNaruhodo(loggg, cProps);
          break;
        case Define.strPICGameRarry: // ■ゲームラリー
          MisIns = new PICGameRarry(loggg, cProps);
          break;

        default:
      }
      if (Arrays.asList(new String[] { Define.strPICClickBanner,
          Define.strPICUranai,
          Define.strPICPriceChyosatai,
          Define.strPICShindan,
          Define.strPICPointResearch,
          Define.strPICChyousadan,
          Define.strPICKumaVote,
          Define.strPICInterview,
          Define.strPICMedalMool,
          Define.strPICFarmEnk,
          Define.strPICFarmEnkNoCap,
          Define.strPICNaruhodo,
          Define.strPICGameRarry
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
      loggg.error("[" + sCode + "");
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
    String selector = "dl.basis_data_box span.red.bold", point = "";
    Utille.url(driver, "https://pointi.jp/my/my_page.php", logg);
    //    if (!Utille.isExistEle(driver, selector, logg)) {
    //      // login!!
    //      LoginSite.login(sCode, driver, logg);
    //      Utille.url(driver, "https://pointi.jp/my/my_page.php", logg); // http://pointi.jp/
    //    }
    LoginSite.login(sCode, driver, logg);
    Utille.url(driver, "https://pointi.jp/my/my_page.php", logg); // http://pointi.jp/
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
