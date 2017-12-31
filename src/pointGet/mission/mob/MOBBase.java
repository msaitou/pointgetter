/**
 * 
 */
package pointGet.mission.mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class MOBBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_MOB;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public MOBBase(Logger log, Map<String, String> cProps, String name) {
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
        case Define.strMOBQuiz: // ■モブクイズ
          MisIns = new MOBQuiz(loggg, cProps);
          break;
        case Define.strMOBClickBanner: // ■クリックで貯める
          MisIns = new MOBClickBanner(loggg, cProps);
          break;
        case Define.strMOBNanyoubi: // ■何曜日
          MisIns = new MOBNanyoubi(loggg, cProps);
          break;
        case Define.strMOBAnzan: // ■暗算
          MisIns = new MOBAnzan(loggg, cProps);
          break;
        case Define.strMOBChirachi: // ■チラシ
          MisIns = new MOBChirachi(loggg, cProps);
          break;
        case Define.strMOBEnglishTest: // ■英単語TEST
          MisIns = new MOBEnglishTest(loggg, cProps);
          break;
        case Define.strMOBCountTimer: // ■CountTimer
          MisIns = new MOBCountTimer(loggg, cProps);
          break;
        case Define.strMOBPointResearch: // ■ポイントサーチ
          MisIns = new MOBPointResearch(loggg, cProps);
          break;
        case Define.strMOBTokkuTimer: // ■ポイントサーチ
          MisIns = new MOBTokkuTimer(loggg, cProps);
          break;
        case Define.strMOBKanji: // ■漢字テスト
          MisIns = new MOBKanji(loggg, cProps);
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strMOBQuiz,
          Define.strMOBNanyoubi,
          Define.strMOBAnzan,
          Define.strMOBEnglishTest,
          Define.strMOBCountTimer,
          Define.strMOBKanji,
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
      loggg.error(Utille.truncateBytes(Utille.parseStringFromStackTrace(e), 1000));
    } finally {
      driver.close();
    }
  }

  /**
   * 
   * @param driver
   * @param logg
   * @return
   */
  public static Double getSitePoint(WebDriver driver, Logger logg) {
    String selector = "div.bankbook_panel__point>em", point = "";
    driver.get("http://pc.mtoku.jp/mypage/bankbook/");
    if (Utille.isExistEle(driver, selector, logg)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      if (Utille.isExistEle(eleList, 0, logg)) {
        point = eleList.get(0).getText();
        point = Utille.getNumber(point);
      }
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
