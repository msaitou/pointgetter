package pointGet.mission.osa;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 * 1日1回
 */
public class OSAClickBanner extends OSABase {
	final String url = "https://osaifu.com/";

	/**
	 * @param logg
	 */
	public OSAClickBanner(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "クリックゴールド");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "section[data-block-title='クリックで貯める'] div>img";
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);;
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver, selector)) {
					driver.findElements(By.cssSelector(selector)).get(i).click();
					Utille.sleep(2000);
					driver.get(url);
					Utille.sleep(2000);
				}
			}
		}
	}

}
