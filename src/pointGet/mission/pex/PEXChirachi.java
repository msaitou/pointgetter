package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class PEXChirachi extends PEXBase {
	final String url = "http://pex.jp/chirashi";

	/**
	 * @param log
	 */
	public PEXChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps, "オリチラ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, this.url, logg);
		selector = "section.list li figure>a";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
			selector = "figure>a>img";
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
					clickSleepSelector(driver, driver.findElements(By.cssSelector(selector)), i, 2000);
					closeOtherWindow(driver);
					break;
				}
			}
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
