package pointGet.mission.osa;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 *
 * @author saitou 0時、12時開催
 */
public class OSAEnglishTest extends OSABase {
	final String url = "http://osaifu.com/contents/coinland/";

	/**
	 * @param logg
	 */
	public OSAEnglishTest(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "英単語Test");
	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "li.long img[alt='英単語TEST']";
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
			selector = "div.ui-control>form>input[name='submit']";
			Utille.sleep(4000);
			if (isExistEle(driver, selector)) {
				clickSelector(driver, selector);
				for (int i = 0; i < 8; i++) {
					Utille.sleep(4000);
					String selectYoubi = "";
					//					if (isExistEle(driver, selectorDay)) {
					//						String text = driver.findElement(By.cssSelector(selectorDay)).getText();
					//						logg.info("testです　" + text);
					//						String str = text;
					//						String regex = "今日の(\\d+)日後は何曜日？";
					//						Pattern p = Pattern.compile(regex);
					//						Matcher m = p.matcher(str);
					//						System.out.println("hajimari");
					//						if (m.find()) {
					//							String strAfterDayNum = m.group(1);
					//							selectYoubi = Utille.getNanyoubi(strAfterDayNum);
					//							System.out.println("なんにちです　[" + selectYoubi + "]");
					//						}
					//						System.out.println("owari");
					//					} else {
					//						logg.info("not get after days");
					//						return;
					//					}
					selectYoubi = String.valueOf(Utille.getIntRand(4) + 1);
					selector = "ul.ui-item-body"; // 曜日要素全体
					if (isExistEle(driver, selector)) {
						// label.ui-label-radio.ui-circle-button[for='radio-1']
						String selectId = "label.ui-label-radio[for='radio-" + selectYoubi + "']";
						String selector2 = "input[name='submit'].ui-button.ui-button-b.ui-button-answer.quake";
						if (isExistEle(driver, selectId)) {
							clickSleepSelector(driver, selectId, 4000); // 遷移
							int ranSleep = Utille.getIntRand(9);
							Utille.sleep(ranSleep * 1000);
//							waitTilReady(driver);
							if (isExistEle(driver, selector2)) {
//								waitTilReady(driver);
								clickSleepSelector(driver, selector2, 4000); // 遷移
								checkOverlay(driver, "div.overlay-popup a.button-close");
								String selector3 = "input[name='submit'].ui-button.ui-button-b.ui-button-result.quake";
								String selector4 = "input[name='submit'].ui-button.ui-button-b.ui-button-end.quake";
								if (isExistEle(driver, selector3)) {
									clickSleepSelector(driver, selector3, 5000); // 遷移
									checkOverlay(driver, "div.overlay-popup a.button-close");
									if (isExistEle(driver, selector4)) {
										clickSleepSelector(driver, selector4, 5000); // 遷移
										checkOverlay(driver, "div.overlay-popup a.button-close");
									}
								}
							}
						}
					}
				}
				logg.info(this.mName + "]kuria?");
				selector = "div.fx-control>a.ui-button.ui-button-a.ui-button-close.quake";
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
