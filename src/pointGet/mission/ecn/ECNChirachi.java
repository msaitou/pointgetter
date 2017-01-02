package pointGet.mission.ecn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class ECNChirachi extends Mission {
	final String url = "http://ecnavi.jp/contents/chirashi/";

	/**
	 * @param log
	 */
	public ECNChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■チラシ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		mName = "■チラシ";
		driver.get(this.url);
		selector = "a.chirashi_link";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
			closeOtherWindow(driver);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
