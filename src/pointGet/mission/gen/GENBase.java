/**
 *
 */
package pointGet.mission.gen;

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
public abstract class GENBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_GEN;

	/**
	 * @param log
	 * @param cProps
	 */
	public GENBase(Logger log, Map<String, String> cProps, String name) {
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
	public static void goToClick(Logger loggg, Map<String, String> cProps, ArrayList<String> missions) {
		WebDriver driver = getWebDriver(cProps);
		// // ■もりもり囲め
		// Mission GENMorimoriKakome = new GENMorimoriKakome(logg, commonProps);
		// GENMorimoriKakome.exePrivateMission(driver);
		// if (testFlag) {
		// return;
		// }
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strGENPointStar: // ■ポイントの森(star)
					MisIns = new GENPointStar(loggg, cProps);
					break;
				case Define.strGENClickBanner: // ■ポイントの森(クリック)
					MisIns = new GENClickBanner(loggg, cProps);
					break;
				case Define.strGENShindan: // ■毎日診断
					MisIns = new GENShindan(loggg, cProps);
					break;
				case Define.strGENUranai: // ■占い
					MisIns = new GENUranai(loggg, cProps);
					break;
				case Define.strGENChirachi: // ■チラシ
					MisIns = new GENChirachi(loggg, cProps);
					break;
				case Define.strGENManga: // ■漫画
					MisIns = new GENManga(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strGENPointStar,
					Define.strGENClickBanner,
					Define.strGENShindan,
					Define.strGENUranai,
					Define.strGENChirachi,
					Define.strGENManga,
			}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
