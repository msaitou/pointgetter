/**
 * 
 */
package pointGet.mission.hap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.Utille;
import pointGet.db.Dbase;
import pointGet.db.PointsCollection;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class HAPBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_HAP;

	/**
	 * @param log
	 * @param cProps
	 */
	public HAPBase(Logger log, Map<String, String> cProps, String name) {
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
				case Define.strHAPManga: // ■漫画
					MisIns = new HAPManga(loggg, cProps);
					break;
        case Define.strHAPPointResearch: // ■アンケート
          MisIns = new HAPPointResearch(loggg, cProps);
          break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strHAPManga,
			    Define.strHAPPointResearch}).contains(mission)) {
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
		String selector = "a.usernavi-point", point = "";
		driver.get("http://hapitas.jp/");
		if (Utille.isExistEle(driver, selector, logg)) {
			point = driver.findElement(By.cssSelector(selector)).getText();
			point = Utille.getNumber(point);
		}
		Double sTotal = Utille.sumTotal(sCode, point, 0.0);
		return sTotal;
	}
}
