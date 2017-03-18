/**
 * 
 */
package pointGet.mission.hap;

import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class HAPBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_HAP;

	/**
	 * @param log
	 * @param cProps
	 */
	public HAPBase(Logger log, Map<String, String> cProps, String name) {
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
	 * @param driver
	 */
	private static void goToClickHAP(Logger log, Map<String, String> cProps, ArrayList<String> missions) {
		for (String mission : missions) {// driverは各mission実体化時にスーパークラス側で用意して
			switch (mission) {
				case Define.strHAPManga: // ■漫画
					Mission HAPManga = new HAPManga(log, commonProps);
//					HAPManga.exePrivateMission(driver);
					break;
				default:
			}
		}
	}
}
