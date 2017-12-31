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
public class ECNGaragara extends ECNBase {
	final String url = "http://ecnavi.jp/game/lottery/garapon/";

	/**
	 * @param logg
	 */
	public ECNGaragara(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "ガラガラ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "p.bnr>a>img";
		if (isExistEle(driver, selector)) {
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			// clipoバナー
			int size = eleList.size();
			for (int i = 0; i < size; i++) {
				// TODO
//				if (isExistEle(driver.findElements(By.cssSelector("div.frame>p.tama>img")).get(i))) {
//					logg.warn(mName + " " + i + "]獲得済み");
//					continue;
//				}
				if (isExistEle(eleList, i)) {
					clickSleepSelector(driver, eleList, i, 2000);
					closeOtherWindow(driver);
				}
			}
		}
	}
}
