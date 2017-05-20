package pointGet.mission.osa;

import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class OSAShindan extends OSABase {
	final String url = "http://osaifu.com/contents/coinland/";

	/**
	 * @param logg
	 */
	public OSAShindan(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "毎日診断");
	}
	@Override
	public void privateMission(WebDriver driver) {
		selector = "li>a>img[alt='毎日診断']";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 8000); // 遷移
			changeCloseWindow(driver);
			while (true) {
				Utille.sleep(5000);
//				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
				selector = "div[class='thumbnail'] span.button-new"; // NEWだけ実施
				if (isExistEle(driver, selector)) {
					clickSleepSelector(driver, selector, 4000); // 遷移
					selector = "a.submit-btn";// 次へ
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 5000); // 遷移
						if (isExistEle(driver, selector)) {
							this.waitTilReady(driver);
							clickSleepSelector(driver, selector, 3000); // 遷移

							if (isExistEle(driver, "div[data-qid]")) {
								int qSize = getSelectorSize(driver, "div[data-qid]"); // 選択肢の数を数える
								for (int i = 0; i < qSize; i++) {
									selector = "div[data-qid][class=''] label";
									if (isExistEle(driver, selector)) {
										int size = getSelectorSize(driver, selector); // 選択肢の数を数える
										int ran1 = Utille.getIntRand(size);
										if (isExistEle(driver.findElements(By.cssSelector(selector)).get(ran1))) {
											driver.findElements(By.cssSelector(selector)).get(ran1).click(); // 選択
											Utille.sleep(3000);

											// end-btn が出たら終了
											String none = "[style*='display: none']";
											String nextSelector = "div.actionBar>a.next-btn";
											String endSelector = "div.actionBar>a.end-btn";
											if (isExistEle(driver, nextSelector)
													&& isExistEle(driver, endSelector + none, false)) {
												clickSleepSelector(driver, nextSelector, 2500); // 遷移
											}
											else if (isExistEle(driver, endSelector)
													&& isExistEle(driver, nextSelector + none, false)) {
												Utille.sleep(2000);
												this.waitTilReady(driver);
												clickSleepSelector(driver, endSelector, 5000); // 遷移
												// 抜けたら　span#end-btn-area>a.end-btn　	をクリック
												selector = "span#end-btn-area>a.end-btn";
												if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
													clickSleepSelector(driver, selector, 4000); // 遷移
													// アラートをけして
													val alert = driver.switchTo().alert();
													alert.accept();
													// overlayを消して
													checkOverlay(driver, "div#overlay p#close>i");

													selector = "div.col-xs-12>a.btn-warning";
													if (!isExistEle(driver, selector)) {
														break;
													}
													String returnUrl = driver.findElement(By.cssSelector(selector))
															.getAttribute("href");
													logg.info("returnUrl:" + returnUrl);
													driver.get(returnUrl);
													Utille.sleep(3000);
												}
											}
										}
									}
								}
							}
						}
					}
				}
				else {
					logg.warn(mName + "]all済み");
					break;
				}
			}
		}
	}
}
