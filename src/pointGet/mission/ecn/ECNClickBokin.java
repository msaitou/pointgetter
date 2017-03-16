package pointGet.mission.ecn;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * @author saitou
 *
 */
public class ECNClickBokin extends ECNBase {
	final String url = "http://point.ecnavi.jp/fund/bc/";

	/**
	 * @param log
	 */
	public ECNClickBokin(Logger log, Map<String, String> cProps) {
		super(log, cProps, "クリック募金");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "div.bnr_box";
		if (isExistEle(driver, selector)) {
			List<WebElement> eleList1 = driver.findElements(By.cssSelector(selector));
			for (WebElement ele : eleList1) {
				if (isExistEle(ele, "a")) {
					clickSleepSelector(ele, "a", 3000);
					closeOtherWindow(driver);
				}
			}
		}
	}
}
