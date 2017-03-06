package pointGet.mission.sug;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 *
 * @author saitou
 */
public class SUGQuiz2 extends SUGBase {
	final String url = "http://www.sugutama.jp/survey";

	/**
	 * @param logg
	 */
	public SUGQuiz2(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "クイズ2");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "img[src='//static.sugutama.jp/ssp_site/2fb22acc41c35e4d518394cc1988b282.png']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 3000); // 遷移 全体へ
			changeCloseWindow(driver);
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
