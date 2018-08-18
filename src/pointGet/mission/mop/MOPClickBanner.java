package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class MOPClickBanner extends MOPBase {
	final String url = "http://pc.moppy.jp/cc/";

	/**
	 * @param log
	 */
	public MOPClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリックで貯める");
	}

	@Override
	public void privateMission(WebDriver driver) {
		// TOPバナー
		Utille.url(driver, "http://pc.moppy.jp/", logg);
		selector = "div#site-jack a img";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
		}

		Utille.url(driver, url, logg);
		selector = "p.click>a.cc-btn";
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
					clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 2000);
					closeOtherWindow(driver);
				}
			}
		}
	}
}
