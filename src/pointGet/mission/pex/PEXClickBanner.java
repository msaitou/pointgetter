package pointGet.mission.pex;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PEXClickBanner extends Mission {
	final String url = "http://pex.jp/point_actions#clickpoint";

	/**
	 * @param log
	 */
	public PEXClickBanner(Logger log) {
		super(log);
		this.mName = "■クリックポイント";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "div.clickpoint_innner li>a>p.title";
		driver.get(this.url);
		if (isExistEle(driver, selector)) {
			if (!isExistEle(driver, "p.get_massage")) { // 獲得済みか
				if (isExistEle(driver, selector)) {
					// clipoバナー
					int size = getSelectorSize(driver, selector);
					for (int i = 0; i < size; i++) {
						if (isExistEle(driver.findElements(By.cssSelector(selector)).get(i))) {
							driver.findElements(By.cssSelector(selector)).get(i).click();
							Utille.sleep(2000);
							driver.get(this.url);
						}
					}
				}
			}
			else {
				logg.warn(mName + "]獲得済み");
			}
		}
	}
}
