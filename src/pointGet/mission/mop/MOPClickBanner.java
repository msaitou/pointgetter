package pointGet.mission.mop;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class MOPClickBanner extends Mission {
	final String url = "http://pc.moppy.jp/cc/";

	/**
	 * @param log
	 */
	public MOPClickBanner(Logger log) {
		super(log);
		this.mName = "■クリックで貯める";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "p.click>a.cc-btn";
		if (isExistEle(driver, selector)) {
			int size = driver.findElements(By.cssSelector(selector)).size();
			for (int i = 0; i < size; i++) {
				driver.findElements(By.cssSelector(selector)).get(i).click();
				Utille.sleep(2000);
			}
		}
	}
}
