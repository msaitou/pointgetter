package pointGet.mission.cit.old;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;
import pointGet.mission.cit.CITBase;

/**
 * @author saitou
 *
 */
public class CITChirachi extends CITBase {
	final String url = "https://www.chance.com/";

	/**
	 * @param log
	 */
	public CITChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps, "チラシ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, this.url, logg);
		selector = "div.top_attention a[href*='www.chance.com/chirashi/']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移
			closeOtherWindow(driver);
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
