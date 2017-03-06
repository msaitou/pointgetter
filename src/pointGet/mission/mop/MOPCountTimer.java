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
			selector = "form.fx-control>input[name='submit']";
			if (isExistEle(driver, finishSelector)) {
				
			}
			else if (isExistEle(driver, finishSelector)) {
				finsishFlag = true;
				return;
			}
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
						waitTime = Utille.getWaitTimeRan(text);
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
								clickSleepSelector(driver, selectRes, 3000); // 結果
								// クリアなら次のボタンが表示
								String next = "input.ui-button.ui-button-b.ui-button-result.quake";
								String reset = "input.ui-button.ui-button-a.ui-button-reset";
								if (isExistEle(driver, next)) {
									clickSleepSelector(driver, next, 3000); // 次
								}
								else {
									clickSleepSelector(driver, reset, 3000); // 最初から
									i = -1;
								}
							}
						}
					}
				}
				
				if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")) {
					checkOverlay(driver, "div.overlay-popup a.button-close");
				}
				String selectorEnd = "input.ui-button.ui-button-b.ui-button-end.quake";
				if (isExistEle(driver, selectorEnd)) {
					clickSleepSelector(driver, selectorEnd, 3000); // 次
				}
				if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")) {
					checkOverlay(driver, "div.overlay-popup a.button-close");
				}
				// ボーナスチャレンジ
				String bounusGo = "input.ui-button.ui-button-b.ui-button-start";
				selector = "div.fx-control>a.ui-button.ui-button-a.ui-button-close.quake";
				if (isExistEle(driver, bounusGo)) {
					clickSleepSelector(driver, bounusGo, 5000); // 次
					if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")) {
						checkOverlay(driver, "div.overlay-popup a.button-close");
					}
					String selectorExpression = "span.try-subject-time";
					int waitTime = 0;
					if (isExistEle(driver, selectorExpression)) {
						String text = driver.findElement(By.cssSelector(selectorExpression)).getText();
						logg.info("お題　" + text);
						waitTime = Utille.getWaitTime(text);
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
								clickSleepSelector(driver, selectRes, 3000); // 結果
								// クリアなら次のボタンが表示
								String next = "input.ui-button.ui-button-b.ui-button-result.quake";
								if (isExistEle(driver, next)) {
									clickSleepSelector(driver, next, 3000); // 次
								}
								else {
									clickSleepSelector(driver, selector, 3000); // 終了
								}
							}
						}
					}
				}
				if (!isExistEle(driver, "div.overlay-popup[style*='display: none;'] a.button-close")) {
					checkOverlay(driver, "div.overlay-popup a.button-close");
				}
				if (isExistEle(driver, selectorEnd)) {
					clickSleepSelector(driver, selectorEnd, 3000); // 終了
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
