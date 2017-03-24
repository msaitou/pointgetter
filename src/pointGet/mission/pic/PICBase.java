/**
 *
 */
package pointGet.mission.pic;

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
public abstract class PICBase extends Mission {
	/* current site code */
	public final static String sCode = Define.PSITE_CODE_PIC;
	boolean finsishFlag = false;

	/**
	 * @param log
	 * @param cProps
	 */
	public PICBase(Logger log, Map<String, String> cProps, String name) {
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
		String sel = "ul.header_wrap.clearfix>li.right>span.name";
		driver.get("http://pointi.jp/"); // http://pointi.jp/
		if (!Utille.isExistEle(driver, sel, loggg)) {	// ログインフラグ持たせて、例外時リトライの際にログインもするようにした方がよさげ TODO
			// login!!
			LoginSite.login(sCode, driver, loggg);
		}
		for (String mission : missions) {
			Mission MisIns = null;
			switch (mission) {
				case Define.strPICClickBanner: // ■PICクリックバナー
					MisIns = new PICClickBanner(loggg, cProps);
					break;
				case Define.strPICUranai: // ■占い
					MisIns = new PICUranai(loggg, cProps);
					break;
				case Define.strPICPriceChyosatai: // ■PICPrice調査隊
					MisIns = new PICPriceChyosatai(loggg, cProps);
					break;
				case Define.strPICShindan: // ■PIC毎日診断
					MisIns = new PICShindan(loggg, cProps);
					break;
				default:
			}
			if (Arrays.asList(new String[] {Define.strPICClickBanner,
					Define.strPICUranai,
					Define.strPICPriceChyosatai,
					Define.strPICShindan,
					}).contains(mission)) {
				driver = MisIns.exeRoopMission(driver);
			}
		}
		driver.quit();
	}
}
