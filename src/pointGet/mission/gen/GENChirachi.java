package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class GENChirachi extends GENBase {
	final String url = "http://www.gendama.jp/";

	/**
	 * @param log
	 */
	public GENChirachi(Logger log, Map<String, String> cProps) {
		super(log, cProps, "チラシ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div#dropmenu01";
		if (isExistEle(driver, selector)) {
			int size0 = getSelectorSize(driver, selector);
			for (int ii = 0; ii < size0; ii++) {
				WebElement e = driver.findElements(By.cssSelector(selector)).get(ii);
				String selector2 = "a[onclick*='チラシで貯める']";
				if (isExistEle(e, selector2)) {
					if (!isExistEle(e, selector2)) {
						break;
					}
					String chirashiUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
					driver.get(chirashiUrl);
					Utille.sleep(4000);
					selector = "li>a>img";
					int size = getSelectorSize(driver, selector);
					for (int i = 2; i < size; i++) {
						if (isExistEle(driver.findElements(By.cssSelector(selector)), i)) {
							clickSleepSelector(driver.findElements(By.cssSelector(selector)), i, 4000);
							closeOtherWindow(driver);
							break;
						}
					}
				}
			}
		}
	}
}
