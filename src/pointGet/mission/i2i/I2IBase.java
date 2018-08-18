/**
 *
 */
package pointGet.mission.i2i;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
public abstract class I2IBase extends Mission {
  /* current site code */
  public final static String sCode = Define.PSITE_CODE_I2I;

  /**
   * @param log
   * @param cProps
   */
  public I2IBase(Logger log, Map<String, String> cProps, String name) {
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
    for (String mission : missions) {
      Mission MisIns = null;
      switch (mission) {
        case Define.strI2ISeiza: // ■星座占い
          MisIns = new I2ISeiza(loggg, cProps);
          break;
        case Define.strI2IColum: // ■コラム
          MisIns = new I2IColum(loggg, cProps);
          break;
        case Define.strI2IMangaVer2: // ■漫画
          MisIns = new I2IMangaVer2(loggg, cProps);
          break;
        case Define.strI2IPhoto: // ■漫画
          MisIns = new I2IPhoto(loggg, cProps);
          break;
        case Define.strI2INews: // ■気になるニュース
          MisIns = new I2INews(loggg, cProps);
          break;
        case Define.strI2IKenkou: // ■サラサラ健康コラム
          MisIns = new I2IKenkou(loggg, cProps);
          break;
        case Define.strI2IMail:
          MailClicker.main(new String[] {sCode});
          break;
        default:
      }
      if (Arrays.asList(new String[] { Define.strI2ISeiza,
          Define.strI2IColum,
          Define.strI2IMangaVer2,
          Define.strI2IPhoto,
          Define.strI2INews,
          Define.strI2IKenkou,
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
    String selector = "td.ad_point>span.limited", point = "";
    Utille.url(driver, "https://point.i2i.jp/account/", logg);
    Utille.sleep(5000);
    if (Utille.isExistEle(driver, selector, logg)) {
      point = driver.findElement(By.cssSelector(selector)).getText();
      point = Utille.getNumber(point);
    }
    Double sTotal = Utille.sumTotal(sCode, point, 0.0);
    return sTotal;
  }
}
