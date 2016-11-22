package pointGet.mission.PEX;

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
public class PEXChirachi extends Mission {
	final String url = "http://pex.jp/chirashi";

	/**
	 * @param log
	 */
	public PEXChirachi(Logger log) {
		super(log);
		this.mName = "■オリチラ";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(this.url);
		selector = "section.list li figure>a";
		if (super.isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click();
			Utille.sleep(2000);
		} else {
			logg.warn(mName + "]獲得済み");
		}
	}

}
