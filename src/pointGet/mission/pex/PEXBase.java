/**
 * 
 */
package pointGet.mission.pex;

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
public abstract class PEXBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PEX;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PEXBase(Logger log, Map<String, String> cProps, String name) {
		super(log, cProps);
		this.mName = "■" + sCode + name;
	}

	@Override
	public void roopMission(WebDriver driver) {
		for (int i = 0; i < 1 && !finsishFlag; i++) {
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
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions,
			ArrayList<Mission> missionList, String[] wordList, Dbase Dbase) {
		WebDriver driver = getWebDriver(cProps);
		driver.get("http://pex.jp");
		String sel = "dd.user_pt.fw_b>span.fw_b";
		if (!Utille.isExistEle(driver, sel, loggg)) {
			// Login
			LoginSite.login(sCode, driver, loggg);
		}
		//		// 以下1日回
		//		if (!secondFlg && !thirdFlg) {
		//			mName = "■rakutenバナー";
		//			driver.get("http://pex.jp");
		//			String selector = "div>div>div>a>img[src*='image.infoseek.rakuten.co.jp']";
		//			if (isExistEle(driver, selector)) {
		//				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
		//				eleList.get(0).click();
		//				Utille.sleep(3000);
		//			}
		//			else {
		//				logg.warn(mName + "]なかったよ...");
		//			}
		//		}

		// // テスト中
		// PEXMitukete PEXMitukete = new PEXMitukete(logg, commonProps);
		// PEXMitukete.exePrivateMission(driver);
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPEX4quiz: // ■ポイントクイズ
					MisIns = new PEX4quiz(loggg, cProps);
					break;
				case Define.strPEXChirachi: // ■オリチラ
					MisIns = new PEXChirachi(loggg, cProps);
					break;
				case Define.strPEXAnswer: // ■みんなのアンサー
					MisIns = new PEXAnswer(loggg, cProps);
					break;
				case Define.strPEXMekutte: // ■めくってシール
					MisIns = new PEXMekutte(loggg, cProps);
					break;
				case Define.strPEXClickBanner:// ■クリックポイント
					MisIns = new PEXClickBanner(loggg, cProps);
					break;
				case Define.strPEXNews: // ■毎日ニュース
					MisIns = new PEXNews(loggg, cProps);
					break;
				case Define.strPEXPectan: // ■ぺく単
					MisIns = new PEXPectan(loggg, cProps);
					missionList.add(MisIns);
					break;
				case Define.strPEXSearch: // ■ポイント検索
					MisIns = new PEXSearch(loggg, cProps, wordList);
					missionList.add(MisIns);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strPEX4quiz,
					Define.strPEXChirachi,
					Define.strPEXAnswer,
					Define.strPEXMekutte,
					Define.strPEXClickBanner,
					Define.strPEXNews,
					Define.strPEXPectan,
					Define.strPEXSearch,
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
		String selector = "dd.user_pt.fw_b>span.fw_b", point = "";
		driver.get("https://pex.jp/user/point_passbook/all");
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
