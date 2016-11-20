package pointGet.mission.PEX;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PEXMekutte extends Mission {
	final String url = "http://pex.jp/seal";
	private int mekutteClick = 0;

	/**
	 * @param logg
	 */
	public PEXMekutte(Logger logg) {
		super(logg);
		this.mName = "■めくってシール";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "section#mekutte_seal>a.game_btn img";
		if (isExistEle(driver, selector)) {
			driver.findElement(By.cssSelector(selector)).click();
			Utille.sleep(2000);
			this.mekutteSeal(driver);
		}
		//		img.bounce
	}

	/**
	 * 
	 * @param driver
	 */
	public void mekutteSeal(WebDriver driver) {
		// this mission was finished sign.
		if (isExistEle(driver, "section#end")) {
			logg.warn(this.mName + "]獲得済み");
			return;
		}
		// this mission was finished sign.
		else if (isExistEle(driver, "div#end")) {
			return;
		}

		int ran = Utille.getIntRand(4);
		String selectId = "section#game_play input.card0";
		if (ran == 0) {
			selectId += "1";
		}
		else if (ran == 1) {
			selectId += "2";
		}
		else if (ran == 2) {
			selectId += "3";
		}
		else {
			selectId += "4";
		}
		if (isExistEle(driver, selectId)) {
			driver.findElement(By.cssSelector(selectId)).click();
			Utille.sleep(3000);
			this.mekutteClick++;
		}
	}

	/**
	 * 
	 * @return
	 */
	public int getMekutteClick() {
		return this.mekutteClick;
	}
}
