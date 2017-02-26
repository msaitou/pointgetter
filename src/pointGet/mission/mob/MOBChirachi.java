package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * @author saitou
 *
 */
public class MOBChirachi extends MOBBase {
	final String url = "http://pc.mtoku.jp/campaign/";

	/**
	 * @param log
	 */
	public MOBChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps, "チラシ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "img[alt='モバトクチラシ']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 4000); // 遷移
			changeCloseWindow(driver);
			selector = "figure.flyer__item__thumbnail>img";
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
					clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 4000);
					break;
				}
			}
		}
	}
}
