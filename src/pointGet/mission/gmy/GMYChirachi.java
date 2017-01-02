package pointGet.mission.gmy;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class GMYChirachi extends Mission {
	final String url = "http://dietnavi.com/pc/";

	/**
	 * @param log
	 */
	public GMYChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■チラシ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "#chirashi_check";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移
			closeOtherWindow(driver);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
