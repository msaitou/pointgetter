package pointGet.mission.gmy;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class GMYClickBanner extends Mission {
	final String url = "";

	/**
	 * @param log
	 */
	public GMYClickBanner(Logger log) {
		super(log);
		this.mName = "■clipoバナー";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "a span.clickpt";
//		http://dietnavi.com/pc/mypage/point_rule.php
		// alt='クリポ付' TODO
		String[] urls = {
				// ■clipoバナーtop
				"http://dietnavi.com/pc/",
				// clipoバナーページ
				"http://dietnavi.com/pc/daily_click.php" };
		for (int j = 0; j < urls.length; j++) {
			driver.get(urls[j]);
			if (isExistEle(driver, selector)) {
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				// clipoバナー
				int size = eleList.size();
				for (int i = 0; i < size; i++) {
					if (isExistEle(eleList, i)) {
						clickSleepSelector(eleList, i, 2000);
					}
				}
			}
		}
	}
}
