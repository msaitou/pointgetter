/**
 *
 */
package pointGet.mission.gmy;

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
public abstract class GMYBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_GMY;

	/**
	 * @param log
	 * @param cProps
	 */
	public GMYBase(Logger log, Map<String, String> cProps, String name) {
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
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strGMYShindan: // ■毎日診断
					MisIns = new GMYShindan(loggg, cProps);
					break;
				case Define.strGMYClickBanner: // ■clipoバナー
					MisIns = new GMYClickBanner(loggg, cProps);
					break;
				case Define.strGMYChirachi: // ■チラシ
					MisIns = new GMYChirachi(loggg, cProps);
					break;
				case Define.strGMYPriceChyosatai: // ■プライス調査隊
					MisIns = new GMYPriceChyosatai(loggg, cProps);
					break;
				case Define.strGMYToidas: // ■GMYトイダス
					MisIns = new GMYToidas(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strGMYShindan,
					Define.strGMYClickBanner,
					Define.strGMYChirachi,
					Define.strGMYPriceChyosatai,
					Define.strGMYToidas,
			}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
