/**
 *
 */
package pointGet.mission.sug;

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
public abstract class SUGBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_SUG;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public SUGBase(Logger log, Map<String, String> cProps, String name) {
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
				case Define.strSUGQuiz: // ■SUGクイズ
					MisIns = new SUGQuiz(loggg, cProps);
					break;
				case Define.strSUGQuiz2: // ■SUGクイズ2
					MisIns = new SUGQuiz2(loggg, cProps);
					break;
				case Define.strSUGUranai: // ■占い
					MisIns = new SUGUranai(loggg, cProps);
					break;
				case Define.strSUGManga: // ■漫画
					MisIns = new SUGManga(loggg, cProps);
					break;
				case Define.strSUGColum: // ■コラム
					MisIns = new SUGColum(loggg, cProps);
					break;
				case Define.strSUGPointResearch: // ■コラム
					MisIns = new SUGPointResearch(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strSUGQuiz,
					Define.strSUGQuiz2,
					Define.strSUGPointResearch
			}).contains(mission)) {
				driver = MisIns.exeRoopMission(driver);
			} else {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
