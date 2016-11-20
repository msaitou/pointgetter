package pointGet.mission.osa;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 * 1日1回
 */
public class OSAClickBanner extends Mission {
	final String url = "http://osaifu.com/";

	/**
	 * @param logg
	 */
	public OSAClickBanner(Logger logg) {
		super(logg);
		this.mName = "■クリックゴールド";
	}

	@Override
	public void roopMission(WebDriver driver) {
		
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "section.recommend-area.item-block img.item-name";
		if (super.isExistEle(driver, selector)) {
			int size = driver.findElements(By.cssSelector(selector)).size();
			for (int i = 0; i < size; i++) {
				if (super.isExistEle(driver, selector)) {
					driver.findElements(By.cssSelector(selector)).get(i).click();
					Utille.sleep(2000);
					driver.get(url);
					Utille.sleep(2000);
				}
			}
		}
	}

}
