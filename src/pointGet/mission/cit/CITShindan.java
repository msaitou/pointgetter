package pointGet.mission.cit;

import java.util.List;
import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import pointGet.Utille;

/**
 * @author saitou
 *
 */
public class CITShindan extends CITBase {
	final String url = "http://www.chance.com/game/";

	/**
	 * @param logg
	 */
	public CITShindan(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "毎日診断");
	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "a[href='http://www.chance.com/research/shindan/play.jsp']>img[alt='診断テスト']";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 5000); // 遷移
			changeCloseWindow(driver);
			while (true) {
				int zumiCnt = 0;
				selector = "div.entry";
				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
				int size1 = eleList.size();
				WebElement wEle = null;
				for (int i = 0; i < size1; i++) {
					if (isExistEle(eleList, i)) {
						String sumiSelector = "img[src='/images/icons/sumi.png']";
						if (isExistEle(eleList.get(i), sumiSelector)) {
							if (++zumiCnt > 3) {	// 新規ミッション追加時はコメント
								break;
							}
							continue;
						}
						selector = "div.entry";
						wEle = eleList.get(i);
						break;

					}
				}
				if (zumiCnt > 3) {	// 新規ミッション追加時はコメント
					break;
				}
				if (wEle == null) {
					logg.warn(mName + "]all済み");
					break;
				}
				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
				if (isExistEle(wEle, selector)) {
					clickSleepSelector(wEle, selector, 4000); // 遷移
					selector = "a.submit-btn";// 次へ
					if (isExistEle(driver, selector)) {
						clickSleepSelector(driver, selector, 5500); // 遷移
						if (isExistEle(driver, selector)) {
							clickSleepSelector(driver, selector, 20000); // 遷移

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
											}
											else if (isExistEle(driver, endSelector)
													&& isExistEle(driver, nextSelector + none)) {
												Utille.sleep(3000);
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
				}
				else {
					break;
				}
			}
		}
	}
}
