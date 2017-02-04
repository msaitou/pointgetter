package pointGet.mission.pst;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class PSTQuiz extends Mission {
	final String url = "http://www.point-stadium.com/wcmpoint.asp";
	boolean finsishFlag = false;

	/**
	 * @param logg
	 */
	public PSTQuiz(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■PSTクイズ";
	}

	@Override
	public void roopMission(WebDriver driver) {

		for (int i = 0; i < 5 && !finsishFlag; i++) {
			privateMission(driver);
		}
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "form[name='ItemList']>p>input[name='entry']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 6000); // 遷移
			changeWindow(driver);
			Utille.sleep(3000);
			String uranaiSelector = "a>img[alt='ceres']";
			if (isExistEle(driver, uranaiSelector)) {
				clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
				changeWindow(driver);
				checkOverlay(driver, "div.overlay-popup a.button-close");
				// finish condition
				String finishSelector = "p.ui-timer";
				if (isExistEle(driver, finishSelector)) {
					finsishFlag = true;
					return;
				}
				selector = "form>input[name='submit']";
				Utille.sleep(4000);
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
								clickSleepSelector(driver, selectId, 4000); // 遷移
								int ranSleep = Utille.getIntRand(9);
								Utille.sleep(ranSleep * 1000);
								waitTilReady(driver);
								if (isExistEle(driver, selector2)) {
									waitTilReady(driver);
									clickSleepSelector(driver, selector2, 4000); // 遷移
									checkOverlay(driver, "div.overlay-popup a.button-close");
									if (isExistEle(driver, selector2)) {
										clickSleepSelector(driver, selector2, 3000); // 遷移
										checkOverlay(driver, "div.overlay-popup a.button-close");
									}
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
				} else {
					logg.warn(this.mName + "]獲得済み");
				}
			}
		}
	}
}