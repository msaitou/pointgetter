/**
 * 
 */
package pointGet.mission.rin;

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
public abstract class RINBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_RIN;

	/**
	 * @param log
	 * @param cProps
	 */
	public RINBase(Logger log, Map<String, String> cProps, String name) {
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
		// login!!
		LoginSite.login(sCode, driver, loggg);
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strRINClickBanner: // ■クリックバナー(楽天)
					MisIns = new RINClickBanner(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strRINClickBanner }).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}

}