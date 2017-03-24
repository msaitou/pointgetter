/**
 *
 */
package pointGet.mission.mop;

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
public abstract class MOPBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_MOP;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public MOPBase(Logger log, Map<String, String> cProps, String name) {
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
		driver.get("http://pc.moppy.jp/");
		String se = "div#preface>ul.pre__login__inner";
		if (!Utille.isExistEle(driver, se, loggg)) {
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strMOPQuiz: // ■モッピークイズ
					MisIns = new MOPQuiz(loggg, cProps);
					break;
				case Define.strMOPNanyoubi: // ■MOP何曜日
					MisIns = new MOPNanyoubi(loggg, cProps);
					break;
				case Define.strMOPAnzan: // ■MOP暗算
					MisIns = new MOPAnzan(loggg, cProps);
					break;
				case Define.strMOPEnglishTest: // ■英単語TEST
					MisIns = new MOPEnglishTest(loggg, cProps);
					break;
				case Define.strMOPCountTimer: // ■CountTimer
					MisIns = new MOPCountTimer(loggg, cProps);
					break;
				case Define.strMOPManga: // ■漫画
					MisIns = new MOPManga(loggg, cProps);
					break;
				case Define.strMOPPointResearch: // ■ポイントリサーチ
					MisIns = new MOPPointResearch(loggg, cProps);
					break;
				case Define.strMOPClickBanner: // ■クリックで貯める
					MisIns = new MOPClickBanner(loggg, cProps);
					break;
				case Define.strMOPShindan: // ■毎日診断
					MisIns = new MOPShindan(loggg, cProps);
					break;
				case Define.strMOPChyosatai: // ■トキメキ調査隊
					MisIns = new MOPChyosatai(loggg, cProps);
					break;
				case Define.strMOPUranai: // ■MOP星座
					MisIns = new MOPUranai(loggg, cProps);
					break;
				case Define.strMOPChirachi: // ■MOPチラシ
					MisIns = new MOPChirachi(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strMOPQuiz,
					Define.strMOPNanyoubi,
					Define.strMOPAnzan,
					Define.strMOPEnglishTest,
					Define.strMOPCountTimer,
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
