/**
 * 
 */
package pointGet.mission.pmo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Define;
import pointGet.LoginSite;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public abstract class PMOBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PMO;

	/**
	 * @param log
	 * @param cProps
	 */
	public PMOBase(Logger log, Map<String, String> cProps, String name) {
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
		String sel = "span#ygps>span.pointEmphasis";
		driver.get("http://poimon.jp/");
		if (!Utille.isExistEle(driver, sel, loggg)) {	// ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPMOChyosatai: // ■PMOPrice調査隊
					MisIns = new PMOChyosatai(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strPMOChyosatai }).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
