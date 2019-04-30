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
import pointGet.mission.gpo.old.GPOAnzan;
import pointGet.mission.gpo.old.GPOQuiz;

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
    Utille.url(driver, "http://www.gpoint.co.jp/", loggg);
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
        case Define.strGPOHirameki: // ■ひらめき
          MisIns = new GPOHirameki(loggg, cProps);
          break;
        case Define.strGPOCook: // ■料理
          MisIns = new GPOCook(loggg, cProps);
          break;
        case Define.strGPOHyakkey: // ■百景
          MisIns = new GPOHyakkey(loggg, cProps);
          break;
        case Define.strGPOFarmEnkNoCap: // ■ポイントファーム
          MisIns = new GPOFarmEnkNoCap(loggg, cProps);
          break;
        case Define.strGPODokiShindan: // ■どきどき診断
          MisIns = new GPODokiShindan(loggg, cProps);
          break;
        case Define.strGPOAnqueate: // ■アンケート
          MisIns = new GPOAnqueate(loggg, cProps);
          break;
        case Define.strGPOMail:
          MailClicker.main(new String[] { sCode });
          break;
        case Define.strGPOAnimal: // ■動物アンケート
          MisIns = new GPOAnimal(loggg, cProps);
          break;
        case Define.strGPOIjin: // ■偉人アンケート
          MisIns = new GPOIjin(loggg, cProps);
          break;
        case Define.strGPOMatome: // ■まとめ
          MisIns = new GPOMatome(loggg, cProps);
          break;
        case Define.strGPOWhichCollectCar: // ■どっちが正解車
          MisIns = new GPOWhichCollectCar(loggg, cProps);
          break;
        case Define.strGPOWhichCollectUsedCar: // ■どっちが正解中古車
          MisIns = new GPOWhichCollectUsedCar(loggg, cProps);
          break;
        case Define.strGPOWhichCollectTour: // ■どっちが正解ツアー
          MisIns = new GPOWhichCollectTour(loggg, cProps);
          break;
        case Define.strGPOWhichCollectGurume: // ■どっちが正解グルメ
          MisIns = new GPOWhichCollectGurume(loggg, cProps);
          break;
        case Define.strGPOCMother: // ■CMあざ―
          MisIns = new GPOCMother(loggg, cProps);
          break;
        case Define.strGPOWhichCollectHighLow: // ■High&Low
          MisIns = new GPOWhichCollectHighLow(loggg, cProps);
          break;
        case Define.strGPOKumaVote: // ■クマ熊投票
          MisIns = new GPOKumaVote(loggg, cProps);
          break;
        case Define.strGPOChyousadan: // ■調査団
          MisIns = new GPOChyousadan(loggg, cProps);
          break;
        case Define.strGPOMedalMool: // ■メダルモール
          MisIns = new GPOMedalMool(loggg, cProps);
          break;
        case Define.strGPONaruhodo: // ■なるほど検定
          MisIns = new GPONaruhodo(loggg, cProps);
          break;
        case Define.strGPOQuizKentei: // ■クイズ検定
          MisIns = new GPOQuizKentei(loggg, cProps);
          break;
        case Define.strGPOMotAnk: // ■もっと答えて
          MisIns = new GPOMotAnk(loggg, cProps);
          break;
        case Define.strGPOCMPochi: // ■ぽち
          MisIns = new GPOCMPochi(loggg, cProps);
          break;
        case Define.strGPOWhichCollectPuku: // ■ぽち
          MisIns = new GPOWhichCollectPuku(loggg, cProps);
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
          Define.strGPODokiShindan,
          Define.strGPOAnqueate,
          Define.strGPOHirameki,
          Define.strGPOCook,
          Define.strGPOIjin,
          Define.strGPOAnimal,
          Define.strGPOMatome,
          Define.strGPOWhichCollectCar,
          Define.strGPOWhichCollectUsedCar,
          Define.strGPOWhichCollectTour,
          Define.strGPOWhichCollectHighLow,
          Define.strGPOCMother,
          Define.strGPOKumaVote,
          Define.strGPOChyousadan,
          Define.strGPOMedalMool,
          Define.strGPOWhichCollectGurume,
          Define.strGPONaruhodo,
          Define.strGPOQuizKentei,
          Define.strGPOMotAnk,Define.strGPOCMPochi,Define.strGPOWhichCollectPuku
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
    String selector = "span#point", sel = "ul>li.status-point", point = "";
    Utille.url(driver, "https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do", logg);
    if (!Utille.isExistEle(driver, sel, false, logg)) {
      // login!!
      LoginSite.login(sCode, driver, logg);
      Utille.url(driver, "https://www.gpoint.co.jp/scripts/direct/userinfo/MMMyPage.do", logg);
    }
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
