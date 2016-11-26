package pointGet.mission.mop;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * 
 * @author saitou
 * 0時、8時、16時開催
 */
public class MOPQuiz extends Mission {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param logg
	 */
	public MOPQuiz(Logger logg) {
		super(logg);
		this.mName = "■モッピークイズ";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.game_btn>div.icon>img[alt='モッピークイズ']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移

			changeWindow(driver);
			checkOverlay(driver, "div.overlay-popup a.button-close");
			selector = "form>input[name='submit']";
			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					selector = "ul.ui-item-body";
					if (isExistEle(driver, selector)) {
						int ran = Utille.getIntRand(4);
						String selectId = "label[for='radio-";
						if (ran == 0) {
							selectId += "1']";
						}
						else if (ran == 1) {
							selectId += "2']";
						}
						else if (ran == 2) {
							selectId += "3']";
						}
						else {
							selectId += "4']";
						}
						// 8kai roop
						String selector2 = "input[name='submit']";
						if (isExistEle(driver, selectId)) {
							clickSleepSelector(driver, selectId, 2000); // 遷移
							clickSleepSelector(driver, selector2, 4000); // 遷移
							checkOverlay(driver, "div.overlay-popup a.button-close");
							if (isExistEle(driver, selector2)) {
								clickSleepSelector(driver, selector2, 3000); // 遷移
								checkOverlay(driver, "div.overlay-popup a.button-close");
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "input[name='submit']";
				if (isExistEle(driver, selector)) {
					clickSleepSelector(driver, selector, 3000);
					waitTilReady(driver);
				}
			}
			else {
				logg.warn(this.mName + "]獲得済み");
			}
		}
	}
}
