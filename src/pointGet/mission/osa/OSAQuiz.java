package pointGet.mission.osa;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou 0時、8時、16時開催
 */
public class OSAQuiz extends Mission {
	final String url = "http://osaifu.com/contents/coinland/";

	boolean finsishFlag = false;

	/**
	 * @param logg
	 */
	public OSAQuiz(Logger logg) {
		super(logg);
		this.mName = "■daily quiz";
	}

	@Override
	public void roopMission(WebDriver driver) {
		for (int i = 0; i < 5 && !finsishFlag; i++) {
			privateMission(driver);
		}
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "li.long img[alt='デイリークイズ']";
		driver.get(url);
		Utille.sleep(2000);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移
			String overlaySelector = "div.overlay.overlay-timer>div.overlay-item[style*='display: block'] a.button-close";
			checkOverlay(driver, overlaySelector);
			// finish condition
			String finishSelector = "p.ui-timer";
			if (isExistEle(driver, finishSelector)) {
				finsishFlag = true;
				return;
			}
			selector = "input[name='submit']";
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 8; i++) {
					// driver.navigate().refresh();
					Utille.sleep(4000);
					selector = "ul.ui-item-body";
					if (isExistEle(driver, selector)) {
						int ran = Utille.getIntRand(4);
						String selectId = "label[for='radio-";
						if (ran == 0) {
							selectId += "1']";
						} else if (ran == 1) {
							selectId += "2']";
						} else if (ran == 2) {
							selectId += "3']";
						} else {
							selectId += "4']";
						}
						// 8kai roop
						String selector2 = "input[name='submit']";
						if (isExistEle(driver, selectId)) {
							clickSleepSelector(driver, selectId, 4000); // 選択
							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
								clickSleepSelector(driver, selector2, 4000); // 遷移
								checkOverlay(driver, overlaySelector);
								if (isExistEle(driver, selector2)) {
									clickSleepSelector(driver, selector2, 3000); // 遷移
									checkOverlay(driver, overlaySelector);
								}
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "input[name='submit']";
				if (isExistEle(driver, selector)) {
					checkOverlay(driver, overlaySelector);
					clickSleepSelector(driver, selector, 3000); // 遷移
				}
			}
		} else {
			logg.warn(this.mName + "]獲得済み");
		}
	}
}
