/**
 * 
 */
package pointGet.mission.pto;

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
public abstract class PTOBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PTO;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PTOBase(Logger log, Map<String, String> cProps, String name) {
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
		String sel = "li.point>a>strong";
		driver.get("https://www.pointtown.com/ptu/index.do");
		if (!Utille.isExistEle(driver, sel,loggg)) {
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPTOClickCorner: // ■クリックコーナー
					MisIns = new PTOClickCorner(loggg, cProps);
					break;
				case Define.strPTOUranai: // ■占い
					MisIns = new PTOUranai(loggg, cProps);
					break;
				case Define.strPTOKuji: // ■くじ
					MisIns = new PTOKuji(loggg, cProps);
					break;
				case Define.strPTODaily: // ■デイリー
					MisIns = new PTODaily(loggg, cProps);
					break;
				case Define.strPTOManga: // ■漫画
					MisIns = new PTOManga(loggg, cProps);
					break;
				case Define.strPTOPointResearch: // ■アンケート
					MisIns = new PTOPointResearch(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] { Define.strPTOClickCorner,
					Define.strPTOUranai,
					Define.strPTOKuji,
					Define.strPTODaily,
					Define.strPTOManga,
					Define.strPTOPointResearch,
					}).contains(mission)) {
				driver = MisIns.exePrivateMission(driver);
			}
		}
		driver.quit();
	}
}
