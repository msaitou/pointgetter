package pointGet.mission.mop;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.Utille;
import pointGet.mission.Mission;

/**
 * @author saitou
 *
 */
public class MOPShindan extends Mission {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param logg
	 */
	public MOPShindan(Logger logg) {
		super(logg);
		this.mName = "■毎日診断";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		driver.get(url);
		selector = "div.game_btn>div.icon>img[alt='毎日診断']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 4000); // 遷移
			changeWindow(driver);

			while (true) {
				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
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
											Utille.sleep(2000);

											// end-btn が出たら終了
											String none = "[style*='display: none']";
											String nextSelector = "div.actionBar>a.next-btn";
											String endSelector = "div.actionBar>a.end-btn";
											if (isExistEle(driver.findElements(By.cssSelector(nextSelector)))
													&& isExistEle(driver.findElements(By.cssSelector(endSelector
															+ none)))) {
												clickSleepSelector(driver, nextSelector, 2000); // 遷移
											}
											else if (isExistEle(driver.findElements(By.cssSelector(endSelector)))
													&& isExistEle(driver.findElements(By.cssSelector(nextSelector
															+ none)))) {
												this.waitTilReady(driver);
												clickSleepSelector(driver, endSelector, 2000); // 遷移
												// 抜けたら　span#end-btn-area>a.end-btn　	をクリック
												selector = "span#end-btn-area>a.end-btn";
												if (isExistEle(driver.findElements(By.cssSelector(selector)))) {
													clickSleepSelector(driver, endSelector, 2000); // 遷移
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
	// 済み
	//	ピュア度診断
	//	ネコ科動物診断
	//	少女漫画であなたはどのキャラクター？
	//	あなたの前世は何人？前世国籍診断
	//	あなたにおすすめのダイエット診断！
	//	あなたが身に付けると良い特技診断！
	//	あなたの本質は「データ型」？「直感型？」診断！
	//	あなたの最適ストレスケア方法は？ストレスケア診断！
	//	あなたの笑顔診断！
	//	あなたをバイクに例えると？バイク占い
}
