package pointGet.mission.mop;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

public class MOPAnzan extends Mission {
	final String url = "http://pc.moppy.jp/gamecontents/";
	boolean finsishFlag = false;

	/**
	 * @author saitou 0時、12時開催
	 */
	public MOPAnzan(Logger log, Map<String, String> cProps) {
		super(log, cProps);
		this.mName = "■MOP暗算";
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
		selector = "div.game_btn>div.icon>img[alt='ANZANmental arithmetic']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 2000); // 遷移
			changeWindow(driver);
			checkOverlay(driver, "div.overlay-popup a.button-close");
			// finish condition
			String finishSelector = "p.ui-timer";
			if (isExistEle(driver, finishSelector)) {
				finsishFlag = true;
				return;
			}
			selector = "form.fx-control>input[name='submit']";
			while (true) { // 最大２回
				Utille.sleep(4000);
				if (isExistEle(driver, selector)) {
					clickSelector(driver, selector);
					for (int i = 0; i < 10; i++) {
						Utille.sleep(4000);
						String selectorExpression = "div.ui-item-header>h2.ui-item-title";
						String selectAns = "";
						if (isExistEle(driver, selectorExpression)) {
							String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
							logg.info("testです　" + text);
							String regex = "(\\d) ([-×+÷]) (\\d) ([-×+÷]) (\\d)";
							Pattern p = Pattern.compile(regex);
							Matcher m = p.matcher(text);
							if (m.find()) {
								String ans = Utille.calcAnzan(m);
								logg.info("答え [" + ans + "]");
								selectAns = ans;
							}
						} else {
							logg.info("not get after days");
							return;
						}
						if (isExistEle(driver, "ul.ui-item-body")) { // 答え要素全体
							// label.ui-label-radio.ui-circle-button[for='radio-1']
							String selectId = "label.ui-label-radio";
							String selector2 = "input[name='submit']";

							List<WebElement> eleList = driver.findElements(By.cssSelector(selectId));
							for (int j = 0; j < 4; j++) { // 4択から正解を探す
								if (isExistEle(eleList, j)) {
									String choice = eleList.get(j).getText();
									if (Utille.numEqual(selectAns, choice)) {
										// if (selectAns.equals(choice)) {
										clickSleepSelector(eleList, j, 3000);// 選択
										int ranSleep = Utille.getIntRand(9);
										Utille.sleep(ranSleep * 1000);
										waitTilReady(driver);
										if (isExistEle(driver, selector2)) {
											waitTilReady(driver);
											clickSleepSelector(driver, selector2, 5000); // 遷移
											String selector3 = "div.fx-control>input[name='submit']";
											String selector4 = "form.fx-control>input[name='submit']";
											checkOverlay(driver, "div.overlay-popup a.button-close");
											if (isExistEle(driver, selector3)) {
												clickSleepSelector(driver, selector3, 3000); // 遷移
												checkOverlay(driver, "div.overlay-popup a.button-close");
											} else if (isExistEle(driver, selector4)) {
												clickSleepSelector(driver, selector4, 5000); // 遷移
												checkOverlay(driver, "div.overlay-popup a.button-close");
											}
										}
										break;
									}
								}
							}
						}
					}
					logg.info(this.mName + "]kuria?");
				} else {
					String endSelector = "input[name='submit']";
					if (isExistEle(driver, endSelector)) {
						clickSleepSelector(driver, endSelector, 3000);
						waitTilReady(driver);
					}
					logg.warn(this.mName + "]獲得済み");
					break;
				}
			}
		}
	}
}
