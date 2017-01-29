package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class MOBClickBanner extends Mission {
	final String url = "http://pc.mtoku.jp/";

	/**
	 * @param log
	 */
	public MOBClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■MOBクリックで貯める";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		// TOPバナー
		driver.get(url);
		selector = "ul.p-daily_click__list>li>a>img";
		driver.get("http://pc.mtoku.jp/contents/");
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
					clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 2000);
					closeOtherWindow(driver);
				}
			}
		}
	}
}
