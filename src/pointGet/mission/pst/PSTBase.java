/**
 *
 */
package pointGet.mission.pst;

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
public abstract class PSTBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PST;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PSTBase(Logger log, Map<String, String> cProps, String name) {
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
				case Define.strPSTQuiz: // ■PSTクイズ
					MisIns = new PSTQuiz(loggg, cProps);
					break;
				case Define.strPSTUranai: // ■占い
					MisIns = new PSTUranai(loggg, cProps);
					break;
				case Define.strPSTShindanAnk: // ■PST診断＆アンケート
					MisIns = new PSTShindanAnk(loggg, cProps);
					break;
				case Define.strPSTManga: // ■PSTまんが　
					MisIns = new PSTManga(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strPSTQuiz,
					Define.strPSTShindanAnk
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
