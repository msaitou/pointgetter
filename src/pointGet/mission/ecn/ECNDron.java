package pointGet.mission.ecn;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class ECNDron extends Mission {
	final String url = "";

	/**
	 * @param log
	 */
	public ECNDron(Logger log) {
		super(log);
		this.mName = "■ドロンバナークリック2種";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		String[] dronUrlList = { "http://ecnavi.jp/", "http://ecnavi.jp/pointboard/#doron" };
		selector = "div#doron a.item";
		for (int i = 0; i < dronUrlList.length; i++) {
			driver.get(dronUrlList[i]);
			Utille.sleep(1000);
			if (isExistEle(driver, "div.js_anime.got")) {
				logg.warn(mName + i + "]獲得済み");
			}
			else if (isExistEle(driver, selector)) {
				clickSleepSelector(driver, selector, 1000);
			}
		}
	}
}
