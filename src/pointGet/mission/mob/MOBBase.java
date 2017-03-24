/**
 * 
 */
package pointGet.mission.mob;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
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
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions) {
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
				default:
			}
			if (Arrays.asList(new String[] { Define.strMOBQuiz,
					Define.strMOBNanyoubi,
					Define.strMOBAnzan,
					Define.strMOBEnglishTest,
					Define.strMOBCountTimer,
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
