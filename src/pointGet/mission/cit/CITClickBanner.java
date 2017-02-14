package pointGet.mission.cit;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class CITClickBanner extends Mission {
	final String url = "http://www.chance.com/";
//　途中TODO
	/**
	 * @param log
	 */
	public CITClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■CITクリックバナー";
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
