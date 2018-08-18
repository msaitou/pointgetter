package pointGet.mission.pex;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class PEXMekutte extends PEXBase {
	final String url = "http://pex.jp/seal";
	private int mekutteClick = 0;

	/**
	 * @param logg
	 */
	public PEXMekutte(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "めくってシール");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, url, logg);
		selector = "section#mekutte_seal>a.game_btn img";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000);
			this.mekutteSeal(driver);
		}
		//		img.bounce
	}

	/**
	 *
	 * @param driver
	 */
	public void mekutteSeal(WebDriver driver) {
		waitTilReady(driver);
		// this mission was finished sign.
		if (isExistEle(driver, "div.link[style*='display: block'].display-none>section#end", false)) {
			logg.warn(this.mName + "]獲得済み");
			return;
		}
		// this mission was finished sign.
		else if (isExistEle(driver, "div#end")) { // 見つけての終了かも。
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
			clickSleepSelector(driver, selectId, 3000);
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
