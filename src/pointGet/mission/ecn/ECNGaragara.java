package pointGet.mission.ecn;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class ECNGaragara extends Mission {
	final String url = "http://ecnavi.jp/game/lottery/garapon/";

	/**
	 * @param logg
	 */
	public ECNGaragara(Logger logg) {
		super(logg);
		this.mName = "■ガラガラ";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "p.bnr>a>img";
		if (super.isExistEle(driver, selector)) {
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			// clipoバナー
			int size = eleList.size();
			for (int i = 0; i < size; i++) {
				// TODO
//				if (super.isExistEle(driver.findElements(By.cssSelector("div.frame>p.tama>img")).get(i))) {
//					logg.warn(mName + " " + i + "]獲得済み");
//					continue;
//				}
				eleList.get(i).click();
				Utille.sleep(2000);
			}
		}
	}
}
