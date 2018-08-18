package pointGet.mission.mop;

import java.util.Map;

import lombok.val;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pointGet.common.Utille;

/**
 * @author saitou
 *
 */
public class MOPShindan extends MOPBase {
	final String url = "http://pc.moppy.jp/gamecontents/";

	/**
	 * @param logg
	 */
	public MOPShindan(Logger logg, Map<String, String> cProps) {
		super(logg, cProps, "毎日診断");
	}

	@Override
	public void privateMission(WebDriver driver) {
		Utille.url(driver, url, logg);
		selector = "div.game_btn>div.icon>img[alt='毎日診断']";
		if (isExistEle(driver, selector)) {
			clickSleepSelector(driver, selector, 6000); // 遷移
			changeCloseWindow(driver);

			while (true) {
				//				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
				selector = "div[class='thumbnail'] span.button-new"; // NEWだけ実施
				// label label-danger button-new
				//<div class="thumbnail">
				//	<a href="/fanmedia-2/220/start?&amp;uid=MB000029114826815914707WOb3M">
				//	 <div class="entryimg" style="background-image: url(/img/diagnose/25ebdc476e7a641b23422a80d02d60bd.jpg);"></div>
				//	 <span class="label label-danger button-new">NEW!</span>
				//	</a>
				//  <div class="caption">
				//	  <h3 class="entrytitle" style="height: 43px;"><a href="/fanmedia-2/220/start?&amp;uid=MB000029114826815914707WOb3M">あなたは四国地方のどの県？診断</a></h3>
				//	  <a href="/fanmedia-2/220/start?&amp;uid=MB000029114826815914707WOb3M" class="btn btn-primary btn-large btn-block" role="button">　診断　</a>
				//  </div>
				//</div>
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
											if (isExistEle(driver, nextSelector)
													&& isExistEle(driver, endSelector + none, false)) {
												clickSleepSelector(driver, nextSelector, 2000); // 遷移
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
													Utille.url(driver, returnUrl, logg);
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
	// あなたに最も合う武器は？
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
