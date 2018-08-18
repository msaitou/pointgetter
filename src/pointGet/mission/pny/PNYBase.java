/**
 *
 */
package pointGet.mission.pny;

import java.util.ArrayList;
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
public abstract class PNYBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_PNY;
  boolean finsishFlag = false;

  /**
   * @param log
   * @param cProps
   */
  public PNYBase(Logger log, Map<String, String> cProps, String name) {
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
    // point状況確認
    String p = getSitePoint(driver, loggg);
    PointsCollection PC = new PointsCollection(Dbase);
    Map<String, Double> pMap = new HashMap<String, Double>();
    pMap.put(sCode, Double.parseDouble(p));
    PC.putPointsData(pMap);
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
