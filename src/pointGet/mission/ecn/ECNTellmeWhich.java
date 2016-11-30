package pointGet.mission.ecn;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class ECNTellmeWhich extends Mission {
	final String url = "http://ecnavi.jp/vote/choice/";

	/**
	 * @param log
	 */
	public ECNTellmeWhich(Logger log) {
		super(log);
		this.mName = "■教えてどっち";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		// ランダムで1,2を選ぶ
		int ran1 = Utille.getIntRand(2);
		selector = "button";
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
