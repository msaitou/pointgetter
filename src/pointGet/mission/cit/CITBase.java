/**
 * 
 */
package pointGet.mission.cit;

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
public abstract class CITBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_CIT;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public CITBase(Logger log, Map<String, String> cProps, String name) {
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
				case Define.strCITPriceChyosatai: // ■CITPrice調査隊
					MisIns = new CITPriceChyosatai(loggg, cProps);
					break;
				case Define.strCITShindan: // ■CIT毎日診断
					MisIns = new CITShindan(loggg, cProps);
					break;
				case Define.strCITToidas: // ■CITトイダス
					MisIns = new CITToidas(loggg, cProps);
					break;
				case Define.strCITClickBanner: // ■CITクリックバナー
					MisIns = new CITClickBanner(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strCITPriceChyosatai,
					Define.strCITShindan,
					Define.strCITToidas,
					Define.strCITClickBanner
			}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
