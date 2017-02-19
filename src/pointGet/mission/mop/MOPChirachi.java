package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class MOPChirachi extends Mission {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param log
	 */
	public MOPChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■MOPチラシ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.icon>img[alt='モッピーチラシ']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 4000); // 遷移
			changeCloseWindow(driver);
			selector = "figure.flyer__item__thumbnail>img";
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
					clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 2000);
					break;
				}
			}
		}
	}
}
