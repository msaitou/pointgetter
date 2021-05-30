/**
 *
 */
package pointGet.mission.pil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.LoginSite;
import pointGet.MailClicker;
import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;
import pointGet.mission.pil.old.PILChyousadan;
import pointGet.mission.pil.old.PILGameParkEnk;
import pointGet.mission.pil.old.PILKumaVote;

/**
 * @author saitou
 *
 */
public abstract class PILBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_PIL;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public PILBase(Logger log, Map<String, String> cProps, String name) {
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
    // login!!
    LoginSite.login(sCode, driver, loggg);
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strPILClickBanner: // ■PILクリック
          MisIns = new PILClickBanner(loggg, cProps);
          break;
        case Define.strPILQuiz: // ■PILクイズ
          MisIns = new PILQuiz(loggg, cProps);
          break;
        case Define.strPILUranai: // ■PIL占い
          MisIns = new PILUranai(loggg, cProps);
          break;
        case Define.strPILShindanAnk: // ■PIL診断＆アンケート
          MisIns = new PILShindanAnk(loggg, cProps);
          break;
        case Define.strPILManga: // ■まんが
          MisIns = new PILManga(loggg, cProps);
          break;
        case Define.strPILGameParkEnk: // ■GameParkアンケート
          MisIns = new PILGameParkEnk(loggg, cProps);
          break;
        case Define.strPILChyousadan: // ■調査団
          MisIns = new PILChyousadan(loggg, cProps);
          break;
        case Define.strPILKumaVote: // ■くま投票
          MisIns = new PILKumaVote(loggg, cProps);
          break;
        case Define.strPILMail:
          MailClicker.main(new String[] {sCode});
          break;
        case Define.strPILAnqueate: // ■PILアンケート
          MisIns = new PILAnqueate(loggg, cProps);
          break;
        case Define.strPILAnkPark: // ■PILアンケートpark
          MisIns = new PILAnkPark(loggg, cProps);
          break;

        default:
      }
      if (Arrays.asList(new String[] { Define.strPILQuiz,
          Define.strPILShindanAnk
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
    // login!!
    LoginSite.login(sCode, driver, logg);
    String selector = "table.memberinfo tr>td>strong", point = "";
    if (Utille.isExistEle(driver, selector, logg)) {
      List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
      if (Utille.isExistEle(eleList, 1, logg)) {
        point = eleList.get(1).getText();
        point = Utille.getNumber(point);
      }
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
