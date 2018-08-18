package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class MOBClickBanner extends MOBBase {
	final String url = "http://pc.mtoku.jp/";

	/**
	 * @param log
	 */
	public MOBClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリックで貯める");
	}

	@Override
	public void privateMission(WebDriver driver) {
		// TOPバナー
		Utille.url(driver, url, logg);
		selector = "ul.p-daily_click__list>li>a>img";
		Utille.url(driver, "http://pc.mtoku.jp/contents/", logg);
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
