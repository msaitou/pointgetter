package pointGet.mission.mop;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

public class MOPCountTimer extends MOPBase {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @author saitou 0時、12時開催
	 */
	public MOPCountTimer(Logger log, Map<String, String> cProps) {
		super(log, cProps, "CountTimer");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.game_btn>div.icon>img[alt='CountTimer']";
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
			selector = "form.fx-control>input[name='submit']";
			//			while (true) { // 最大２回
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 5; i++) {
					Utille.sleep(4000);
					if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")) {
						checkOverlay(driver, "div.overlay-popup a.button-close");
					}
					String selectorExpression = "span.try-subject-time";
					int waitTime = 0;
					if (isExistEle(driver, selectorExpression)) {
						String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
						logg.info("お題　" + text);
						Double dA = Double.parseDouble(text);
						waitTime = dA.intValue() * 1000;
					}
					else {
						logg.info("not get odai");
						return;
					}
					// label.ui-label-radio.ui-circle-button[for='radio-1']
					String selectId = "div.fx-control>input#js-timer_btn_start";
					String selectStop = "div.fx-control>input#js-timer_btn_stop";
					String selectRes = "input#timer_btn_stop";
					String selector2 = "input[name='submit']";
					if (isExistEle(driver, selectId)) {
						clickSleepSelector(driver, selectId, 0); // START!!
						long s = System.currentTimeMillis();
						Utille.sleep(waitTime);
						long e = System.currentTimeMillis();
						System.out.println("sleep " + (s-e));
						if (isExistEle(driver, selectStop)) {
							clickSleepSelector(driver, selectStop, 1000); // END
							if (isExistEle(driver, selectRes)) {
								clickSleepSelector(driver, selectRes, 1000); // 結果
								// クリアなら次のボタンが表示
							}
						}
					}

//					List<WebElement> eleList = driver.findElements(By.cssSelector(selectId));
//					for (int j = 0; j < 4; j++) { // 4択から正解を探す
//						if (isExistEle(eleList, j)) {
//							String choice = eleList.get(j).getText();
//							if (Utille.numEqual(selectAns, choice)) {
//								// if (selectAns.equals(choice)) {
//								clickSleepSelector(eleList, j, 3000);// 選択
//								int ranSleep = Utille.getIntRand(9);
//								Utille.sleep(ranSleep * 1000);
//								waitTilReady(driver);
//								if (isExistEle(driver, selector2)) {
//									waitTilReady(driver);
//									clickSleepSelector(driver, selector2, 5500); // 遷移
//									String selector3 = "div.fx-control>input[name='submit']";
//									String selector4 = "form.fx-control>input[name='submit']";
//									checkOverlay(driver, "div.overlay-popup a.button-close");
//									if (isExistEle(driver, selector3)) {
//										clickSleepSelector(driver, selector3, 3000); // 遷移
//										checkOverlay(driver, "div.overlay-popup a.button-close");
//									}
//									else if (isExistEle(driver, selector4)) {
//										clickSleepSelector(driver, selector4, 5000); // 遷移
//										checkOverlay(driver, "div.overlay-popup a.button-close");
//									}
//								}
//								break;
//							}
//						}
//					}
				}
				logg.info(this.mName + "]kuria?");
				//	checkOverlay(driver, "div.overlay-popup a.button-close");
			}
			else {
				String endSelector = "input[name='submit']";
				if (isExistEle(driver, endSelector)) {
					clickSleepSelector(driver, endSelector, 3000);
					waitTilReady(driver);
				}
				logg.warn(this.mName + "]獲得済み");
				//					break;
			}
			//			}
		}
	}
}
