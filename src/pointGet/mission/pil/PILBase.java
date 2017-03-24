/**
 *
 */
package pointGet.mission.pil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PILBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PIL;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PILBase(Logger log, Map<String, String> cProps, String name) {
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
		// login!!
		LoginSite.login(sCode, driver, loggg);
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPILClickBanner: // ■PILクリック
					MisIns = new PILClickBanner(loggg, cProps);
					break;
				case Define.strPILQuiz: // ■PILクイズ
					MisIns = new PILQuiz(loggg, cProps);
					break;
				case Define.strPILUranai: // ■PIL占い
					MisIns = new PILUranai(loggg, cProps);
					break;
				case Define.strPILShindanAnk: // ■PIL診断＆アンケート
					MisIns = new PILShindanAnk(loggg, cProps);
					break;
				case Define.strPILManga: // ■まんが
					MisIns = new PILManga(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] {Define.strPILQuiz,
					Define.strPILShindanAnk
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
