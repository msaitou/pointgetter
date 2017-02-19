package pointGet.mission.gen;

import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;
import pointGet.mission.Mission;

public class GENUranai extends Mission {
	final String url = "http://www.gendama.jp/";

	/**
	 * @param logg
	 */
	public GENUranai(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■GEN星座";
	}

	@Override
	public void roopMission(WebDriver driver) {
	}

	@Override
	public void privateMission(WebDriver driver) {
		// div#dropmenu01
		driver.get(url);
		selector = "div#dropmenu01";
		if (isExistEle(driver, selector)) {
			int size = getSelectorSize(driver, selector);
			for (int i = 0; i < size; i++) {
				WebElement e = driver.findElements(By.cssSelector(selector)).get(i);
				String selector2 = "a[onclick*='CMくじ']";
				if (isExistEle(e, selector2)) {
					if (!isExistEle(e, selector2)) {
						break;
					}
					String cmPageUrl = e.findElement(By.cssSelector(selector2)).getAttribute("href");
					driver.get(cmPageUrl); // CMpage
					Utille.sleep(3000);
					String uranaiSelector = "a>img[alt='uranai']";
					if (!isExistEle(driver, uranaiSelector)) {
						break;
					}
					clickSleepSelector(driver, uranaiSelector, 3000); // 遷移 全体へ
					changeCloseWindow(driver);
					// // アラートをけして
					// val alert = driver.switchTo().alert();
					// alert.accept();
					Utille.sleep(4000);
					// changeCloseWindow(driver);

					selector = "div#parts-slide-button__action a>img"; // 占い始める
																		// 全体へ
					String selector1 = "section>div>form>input[type=image]";
					String selectList[] = { selector, selector1 };
					for (int g = 0; g < selectList.length; g++) {
						if (isExistEle(driver, selectList[g])) {
							clickSleepSelector(driver, selectList[g], 3000); // 遷移
																				// 全体へ

							String nextSelector = "div#next-button a>img";
							String symbleSelector = "div#symbols-next-button a>img";
							boolean endFlag = false;
							for (int j = 0; j < 20; j++) { // 20に特に意味なし
															// エンドレスループを避けるため
								// overlayを消して
								if (!isExistEle(driver, "div#inter-ad[style*='display: none'] div#inter-ad-close")) {
									Utille.sleep(3000);
									if (isExistEle(driver, "div#inter-ad div#inter-ad-close")) {
										checkOverlay(driver, "div#inter-ad div#inter-ad-close", false);
										endFlag = true;
									}
								}

								if (isExistEle(driver, nextSelector)) {
									clickSleepSelector(driver, nextSelector, 3000); // 遷移
								} else if (isExistEle(driver, selector)) {
									clickSleepSelector(driver, selector, 3000); // 遷移
								} else if (isExistEle(driver, symbleSelector)) {
									clickSleepSelector(driver, symbleSelector, 3000); // 遷移
								} else if (isExistEle(driver, selector1)) {
									clickSleepSelector(driver, selector1, 3000); // 遷移
								}
								Utille.sleep(3000);
								if (endFlag) {
									break;
								}
							}
						}
					}
					break; // 星座はこれで終了
				}
			}
		}
	}
}
