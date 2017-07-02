package pointGet.mission.ecn;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class ECNTellmeWhich extends ECNBase {
	final String url = "http://ecnavi.jp/vote/choice/";

	/**
	 * @param log
	 */
	public ECNTellmeWhich(Logger log, Map<String, String> cProps) {
		super(log, cProps, "教えてどっち");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "button";
		// ランダムで1,2を選ぶ
		int ran1 = Utille.getIntRand(2);
		if (ran1 == 1) {
			selector += "#btnA";
		}
		else {
			selector += "#btnB";
		}
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
