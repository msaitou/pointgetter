package pointGet.mission.pst;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import lombok.val;
import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class PSTShindan extends Mission {
	final String url = "http://www.chance.com/research/";

	/**
	 * @param logg
	 */
	public PSTShindan(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■CIT毎日診断";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "img[src='/img/research/bnr_shindantest.png']";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 5000); // 遷移
			changeWindow(driver);
			while (true) {
				selector = "div.entry";
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size1 = eleList.size();
				WebElement wEle = null;
				for (int i = 0; i < size1; i++) {
					if (isExistEle(eleList, i)) {
						String sumiSelector = "img[src='/images/icons/sumi.png']";
						if (isExistEle(eleList.get(i), sumiSelector)) {
							continue;
						}
						selector = "div.entry";
						wEle = eleList.get(i);
						break;

					}
				}
				if (wEle == null) {
					break;
				}
				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
				if (isExistEle(wEle, selector)) {
					clickSleepSelector(wEle, selector, 4000); // 遷移
					selector = "a.submit-btn";// 次へ
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 5500); // 遷移
						if (isExistEle(driver, selector)) {
							clickSleepSelector(driver, selector, 5000); // 遷移
							this.waitTilReady(driver);

							if (isExistEle(driver, "div[data-qid]")) {
								int qSize = getSelectorSize(driver, "div[data-qid]"); // 選択肢の数を数える
								for (int i = 0; i < qSize; i++) {
									selector = "div[data-qid][class=''] label";
									if (isExistEle(driver, selector)) {
										int size = getSelectorSize(driver, selector); // 選択肢の数を数える
										int ran1 = Utille.getIntRand(size);
										if (isExistEle(driver.findElements(By.cssSelector(selector)).get(ran1))) {
											driver.findElements(By.cssSelector(selector)).get(ran1).click(); // 選択
											Utille.sleep(2000);

											// end-btn が出たら終了
											String none = "[style*='display: none']";
											String nextSelector = "div.actionBar>a.next-btn";
											String endSelector = "div.actionBar>a.end-btn";
											if (isExistEle(driver, nextSelector)
													&& isExistEle(driver, endSelector + none)) {
												clickSleepSelector(driver, nextSelector, 2000); // 遷移
											} else if (isExistEle(driver, endSelector)
													&& isExistEle(driver, nextSelector + none)) {
												this.waitTilReady(driver);
												clickSleepSelector(driver, endSelector, 4000); // 遷移
												// 抜けたら
												// span#end-btn-area>a.end-btn
												// をクリック
												selector = "span#end-btn-area>a.end-btn";
												if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
													clickSleepSelector(driver, selector, 2000); // 遷移
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
				} else {
					logg.warn(mName + "]all済み");
					break;
				}
			}
		}
	}
}
