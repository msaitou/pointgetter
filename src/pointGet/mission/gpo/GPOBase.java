/**
 *
 */
package pointGet.mission.gpo;

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
public abstract class GPOBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_GPO;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public GPOBase(Logger log, Map<String, String> cProps, String name) {
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
    String sel = "li.status-point", laySel = "div.layer_board a.btnclose_top.btn_close>img";
    driver.get("http://www.gpoint.co.jp/");
    if (!Utille.isExistEle(driver, sel, false, loggg)) { // ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
      // login!!
      LoginSite.login(sCode, driver, loggg);
    }
    // オーバーレイがあれば消す
    if (!Utille
        .isExistEle(driver, "div.layer_board[style*='display: none'] a.btnclose_top.btn_close>img", false, loggg)
        && Utille.isExistEle(driver, laySel, loggg)) {
      driver.findElement(By.cssSelector(laySel)).click();
      Utille.sleep(3000);
    }

    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strGPOUranai: // ■占い
          MisIns = new GPOUranai(loggg, cProps);
          break;
        case Define.strGPOFarmEnk: // ■ポイントファーム
          MisIns = new GPOFarmEnk(loggg, cProps);
          break;
        case Define.strGPONews: // ■ニュース
          MisIns = new GPONews(loggg, cProps);
          break;
        case Define.strGPOKenkou: // ■健康コラム
          MisIns = new GPOKenkou(loggg, cProps);
          break;
        case Define.strGPOGameParkEnk: // ■GPOファーム
          MisIns = new GPOGameParkEnk(loggg, cProps);
          break;
        case Define.strGPOMangaVer2: // ■漫画
          MisIns = new GPOMangaVer2(loggg, cProps);
          break;
        case Define.strGPOQuiz: // ■クイズ
          MisIns = new GPOQuiz(loggg, cProps);
          break;
        case Define.strGPOAnzan: // ■暗算
          MisIns = new GPOAnzan(loggg, cProps);
          break;
        case Define.strGPOColum: // ■コラム
          MisIns = new GPOColum(loggg, cProps);
          break;
        case Define.strGPOPhoto: // ■写真
          MisIns = new GPOPhoto(loggg, cProps);
          break;
        case Define.strGPOPointResearch: // ■アンケート
          MisIns = new GPOPointResearch(loggg, cProps);
          break;
        case Define.strGPOPointResearch2: // ■アンケート２
          MisIns = new GPOPointResearch2(loggg, cProps);
          break;
        case Define.strGPOKansatu: // ■観察
          MisIns = new GPOKansatu(loggg, cProps);
          break;
        case Define.strGPOHyakkey: // ■百景
          MisIns = new GPOHyakkey(loggg, cProps);
          break;
        case Define.strGPOFarmEnkNoCap: // ■ポイントファーム
          MisIns = new GPOFarmEnkNoCap(loggg, cProps);
          break;
        case Define.strGPODokiShindan: // ■ポイントファーム
          MisIns = new GPODokiShindan(loggg, cProps);
          break;

        case Define.strGPOMail:
          MailClicker.main(new String[] { sCode });
          break;

        default:
      }
      if (Arrays.asList(new String[] {
          Define.strGPOUranai,
          Define.strGPOFarmEnk,
          Define.strGPONews,
          Define.strGPOKenkou,
          Define.strGPOGameParkEnk,
          Define.strGPOMangaVer2,
          Define.strGPOQuiz,
          Define.strGPOColum,
          Define.strGPOPhoto,
          Define.strGPOPointResearch,
          Define.strGPOPointResearch2,
          Define.strGPOAnzan,
          Define.strGPOKansatu,
          Define.strGPOHyakkey,
          Define.strGPOFarmEnkNoCap,
          Define.strGPODokiShindan
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
    String selector = "span#point", sel = "uli.status-point", point = "";
    driver.get("https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do");
    if (!Utille.isExistEle(driver, sel, logg)) {
      // login!!
      LoginSite.login(sCode, driver, logg);
      driver.get("https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do");
    }
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
