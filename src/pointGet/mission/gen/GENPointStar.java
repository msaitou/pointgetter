package pointGet.mission.gen;

import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou 
 */
public class GENPointStar extends Mission {
	final String url = "http://www.gendama.jp/forest/";

	/**
	 * @param log
	 */
	public GENPointStar(Logger log) {
		super(log);
		this.mName = "■ポイントの森(star)";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		Utille.sleep(2000);
		selector = "a img[src$='star.gif']";
		if (super.isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click();
		}
		else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
