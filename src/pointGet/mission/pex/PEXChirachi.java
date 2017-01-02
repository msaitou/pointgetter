package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PEXChirachi extends Mission {
	final String url = "http://pex.jp/chirashi";

	/**
	 * @param log
	 */
	public PEXChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■オリチラ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "section.list li figure>a";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
			closeOtherWindow(driver);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}

}
