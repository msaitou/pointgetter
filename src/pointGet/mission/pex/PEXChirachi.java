package pointGet.mission.pex;

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
	public PEXChirachi(Logger log) {
		super(log);
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
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}

}
