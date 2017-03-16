package pointGet.mission.ecn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * @author saitou
 *
 */
public class ECNChirachi extends ECNBase {
	final String url = "http://ecnavi.jp/contents/chirashi/";

	/**
	 * @param log
	 */
	public ECNChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps, "チラシ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
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
