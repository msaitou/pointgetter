/**
 * 
 */
package pointGet.mission.cri;

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
public abstract class CRIBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_CRI;

	/**
	 * 
	 * @param log
	 * @param cProps
	 * @param name
	 */
	public CRIBase(Logger log, Map<String, String> cProps, String name) {
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
				case Define.strCRIChirachi: // ■アンケート
					MisIns = new CRIAnk(loggg, cProps);
					break;
				case Define.strCRIManga: // ■漫画
					MisIns = new CRIManga(loggg, cProps);
					break;
				case Define.strCRIClickBananer: // ■クリックバナー
					MisIns = new CRIClickBananer(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strCRIChirachi,
					Define.strCRIManga, Define.strCRIClickBananer
			}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
