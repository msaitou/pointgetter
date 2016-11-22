package pointGet.mission.gmy;

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
public class GMYChirachi extends Mission {
	final String url = "http://dietnavi.com/pc/";

	/**
	 * @param log
	 */
	public GMYChirachi(Logger log) {
		super(log);
		this.mName = "■チラシ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "#chirashi_check";
		if (super.isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click();
			Utille.sleep(2000);
		} else {
			logg.warn(mName + "]獲得済み");
		}
	}
}
