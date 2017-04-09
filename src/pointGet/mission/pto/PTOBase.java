/**
 * 
 */
package pointGet.mission.pto;

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
public abstract class PTOBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PTO;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PTOBase(Logger log, Map<String, String> cProps, String name) {
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
		String sel = "li.point>a>strong";
		driver.get("https://www.pointtown.com/ptu/index.do");
		if (!Utille.isExistEle(driver, sel,loggg)) {
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPTOClickCorner: // ■クリックコーナー
					MisIns = new PTOClickCorner(loggg, cProps);
					break;
				case Define.strPTOUranai: // ■占い
					MisIns = new PTOUranai(loggg, cProps);
					break;
				case Define.strPTOKuji: // ■くじ
					MisIns = new PTOKuji(loggg, cProps);
					break;
				case Define.strPTODaily: // ■デイリー
					MisIns = new PTODaily(loggg, cProps);
					break;
				case Define.strPTOManga: // ■漫画
					MisIns = new PTOManga(loggg, cProps);
					break;
				case Define.strPTOPointResearch: // ■アンケート
					MisIns = new PTOPointResearch(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strPTOClickCorner,
					Define.strPTOUranai,
					Define.strPTOKuji,
					Define.strPTODaily,
					Define.strPTOManga,
					Define.strPTOPointResearch,
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
		String selector = "li.point>a>strong", point = "";
		driver.get("https://www.pointtown.com/ptu/index.do");
		if (!Utille.isExistEle(driver, selector, logg)) {
			// login!!
			LoginSite.login(sCode, driver, logg);
		}
		if (Utille.isExistEle(driver, selector, logg)) {
			point = driver.findElement(By.cssSelector(selector)).getText();
			point = Utille.getNumber(point);
		}
		Double sTotal = Utille.sumTotal(sCode, point, 0.0);
		return sTotal;
	}
}
