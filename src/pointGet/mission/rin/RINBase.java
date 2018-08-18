/**
 * 
 */
package pointGet.mission.rin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.LoginSite;
import pointGet.MailClicker;
import pointGet.common.Define;
import pointGet.common.Utille;
import pointGet.db.Dbase;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class RINBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_RIN;

  /**
   * @param log
   * @param cProps
   */
  public RINBase(Logger log, Map<String, String> cProps, String name) {
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
    // login!!
    LoginSite.login(sCode, driver, loggg);
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strRINClickBanner: // ■クリックバナー(楽天)
          MisIns = new RINClickBanner(loggg, cProps);
          break;
        case Define.strRINMail:
          MailClicker.main(new String[] {sCode});
          MailClicker.main(new String[] {"r01"});
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strRINClickBanner }).contains(mission)) {
        driver = MisIns.exePrivateMission(driver);
      }
    }
    //		// point状況確認
    //		String p = getSitePoint(driver, loggg);
    //		PointsCollection PC = new PointsCollection(Dbase);
    //		Map<String, Double> pMap = new HashMap<String, Double>();
    //		pMap.put(sCode, Double.parseDouble(p));
    //		PC.putPointsData(pMap);
    driver.close();
  }

  public static String getSitePoint(WebDriver driver, Logger logg) {
    String selector = "li.user>a>span.user_pt", point = "";
    Utille.url(driver, "https://www.chance.com/", logg);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
    }
    return point;
  }
}
