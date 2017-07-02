package pointGet.mission.pex;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class PEXClickBanner extends PEXBase {
	final String url = "http://pex.jp/point_actions#clickpoint";

	/**
	 * @param log
	 */
	public PEXClickBanner(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリックポイント");
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "div.clickpoint_innner li>a>p.title";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			// clipoバナー
			for (int i = 0; i < size; i++) {
				if (isExistEle(driver, "p.get_massage")) { // 獲得済みか
					logg.warn(mName + "]獲得済み");
					break;
				}
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				if (isExistEle(eleList, i)) {
					clickSleepSelector(eleList, i, 3000);// 選択
					driver.get(url);
					Utille.sleep(1000);
				}
			}
		}
	}
}
