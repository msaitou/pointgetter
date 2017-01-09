package pointGet.mission.osa;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou 0時、8時、16時開催
 */
public class OSANanyoubi extends Mission {
	final String url = "http://osaifu.com/contents/coinland/";

	boolean finsishFlag = false;

	/**
	 * @param logg
	 */
	public OSANanyoubi(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■OSA何曜日";
	}

	@Override
	public void roopMission(WebDriver driver) {
		for (int i = 0; i < 5 && !finsishFlag; i++) {
			privateMission(driver);
		}
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "li.long img[alt='この日何曜日？']";
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
			selector = "div.fx-control>input[name='submit']";
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					String selectorDay = "div.ui-item-header>h2.ui-item-title";
					String selectYoubi = "";
					if (isExistEle(driver, selectorDay)) {
						String text = driver.findElement(By.cssSelector(selectorDay)).getText();
						logg.info("testです　" + text);
						String str = text;
						String regex = "今日の(\\d+)日後は何曜日？";
						Pattern p = Pattern.compile(regex);
						Matcher m = p.matcher(str);
						System.out.println("hajimari");
						if (m.find()) {
							String strAfterDayNum = m.group(1);
							selectYoubi = Utille.getNanyoubi(strAfterDayNum);
							System.out.println("なんにちです　[" + selectYoubi + "]");
						}
						System.out.println("owari");
					} else {
						logg.info("not get after days");
						return;
					}
					selector = "ul.ui-item-body.ui-choice"; // 曜日要素全体
					if (isExistEle(driver, selector)) {
						// label.ui-label-radio.ui-circle-button[for='radio-1']
						String selectId = "label.ui-label-radio.ui-circle-button[for='radio-" + selectYoubi + "']";
						String selector2 = "input[name='submit']";
						if (isExistEle(driver, selectId)) {
							clickSleepSelector(driver, selectId, 4000); // 遷移
							int ranSleep = Utille.getIntRand(9);
							Utille.sleep(ranSleep * 1000);
							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
								waitTilReady(driver);
								clickSleepSelector(driver, selector2, 5000); // 遷移
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
