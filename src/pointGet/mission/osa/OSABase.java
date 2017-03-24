/**
 * 
 */
package pointGet.mission.osa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class OSABase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_OSA;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public OSABase(Logger log, Map<String, String> cProps, String name) {
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
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions) {
		WebDriver driver = getWebDriver(cProps);
		String se = "ul.userinfo";
		driver.get("http://osaifu.com/");
		if (!Utille.isExistEle(driver, se, loggg)) {
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strOSAQuiz: // ■daily quiz
					MisIns = new OSAQuiz(loggg, cProps);
					break;
				case Define.strOSANanyoubi: // ■OSA何曜日
					MisIns = new OSANanyoubi(loggg, cProps);
					break;
				case Define.strOSAAnzan: // ■OSA暗算
					MisIns = new OSAAnzan(loggg, cProps);
					break;
				case Define.strOSAEnglishTest: // ■英単語TEST
					MisIns = new OSAEnglishTest(loggg, cProps);
					break;
				case Define.strOSACountTimer: // ■CountTimer
					MisIns = new OSACountTimer(loggg, cProps);
					break;
				case Define.strOSAManga: // ■漫画
					MisIns = new OSAManga(loggg, cProps);
					break;
				case Define.strOSAClickBanner: // ■クリックゴールド
					MisIns = new OSAClickBanner(loggg, cProps);
					break;
				case Define.strOSAShindan: // ■毎日診断
					MisIns = new OSAShindan(loggg, cProps);
					break;
				case Define.strOSAUranai: // ■OSA星座
					MisIns = new OSAUranai(loggg, cProps);
					break;
				case Define.strOSAChirachi: // ■チラシ
					MisIns = new OSAChirachi(loggg, cProps);
					break;
				case Define.strOSAPointResearch: // ■ポイントリサーチ
					MisIns = new OSAPointResearch(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strOSAQuiz,
					Define.strOSANanyoubi,
					Define.strOSAAnzan,
					Define.strOSAEnglishTest,
					Define.strOSACountTimer,
					}).contains(mission)) {
				driver = MisIns.exeRoopMission(driver);
			}
			else {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
