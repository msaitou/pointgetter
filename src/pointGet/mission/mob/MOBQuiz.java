package pointGet.mission.mob;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 *
 * @author saitou
 * 0時、8時、16時開催
 */
public class MOBQuiz extends MOBBase {
	final String url = "http://pc.mtoku.jp/contents/";

	/**
	 * @param logg
	 */
	public MOBQuiz(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "クイズ");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.c-box.game-item>a>p>img[src='https://pc-assets.mtoku.jp/common/img/contents/item_daily_quiz.png']";
//		selector = "div.game_btn>div.icon>img[alt='モッピークイズ']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移

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
							clickSleepSelector(driver, selectId, 4000); // 遷移
							int ranSleep = Utille.getIntRand(9);
							Utille.sleep(ranSleep*1000);
							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
								waitTilReady(driver);
								clickSleepSelector(driver, selector2, 4000); // 遷移
								checkOverlay(driver, "div.overlay-popup a.button-close");
								if (isExistEle(driver, selector2)) {
									clickSleepSelector(driver, selector2, 3000); // 遷移
									checkOverlay(driver, "div.overlay-popup a.button-close");

									//									driver.navigate().refresh();
									//									val alert = driver.switchTo().alert();
									//									alert.accept();
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
			}
			else {
				logg.warn(this.mName + "]獲得済み");
			}
			finsishFlag = true;
		}
	}
}
