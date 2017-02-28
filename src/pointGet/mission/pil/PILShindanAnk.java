package pointGet.mission.pil;

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
public class PILShindanAnk extends Mission {
		final String url = "http://www.point-island.com/wap_enq.asp";
//	final String url = "http://www.point-island.com/wap_enq.asp?qt=1&p=2";

	/**
	 * @param logg
	 */
	public PILShindanAnk(Logger logg, Map<String, String> cProps) {
		super(logg, cProps);
		this.mName = "■PIL診断＆アンケート";
	}

	@Override
	public void roopMission(WebDriver driver) {

	}

	@Override
	public void privateMission(WebDriver driver) {
		selector = "table.table6>tbody>tr>td>input";
		driver.get(url);
		if (isExistEle(driver, selector)) {
			List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			int size1 = eleList.size();
			for (int j = 0; j < size1; j++) {
				if (isExistEle(eleList, j)) {
					clickSleepSelector(eleList, j, 3500);

					String wid = driver.getWindowHandle();
					changeWindow(driver, wid);

					// 表示される結果によって処理を分ける
					selector = "div#shindan";
					// 診断系
					if (isExistEle(driver, selector)) {
						logg.info(mName + " " + ".診断だ! " + j + " click!!");
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
												Utille.sleep(4000);

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
						selector = "img[src='../images/bt_next2.png']";
						String footBannerSele = "div#meerkat-wrap a.close-meerkat";
						if (isExistEle(driver, selector)) {
							if (isExistEle(driver, footBannerSele)) {
								checkOverlay(driver, footBannerSele);
							}
							clickSleepSelector(driver, selector, 3500);
							String ｃid = driver.getWindowHandle();
							// １つ目に開いたウィンドウを閉じる
							driver.close();
							// 別ウィンドウがさらに開く
							changeWindow(driver, ｃid);
							// 問、選択肢、選択結果をメモリにためて、終了後にまとめてログ出力
							String dialogCloseSele = "div#popmodal img[src='../images/close.png']";
							String dialogCloseSeleNone = "div#popmodal[style*='display: none'] img[src='../images/close.png']";
							// 以下セレクタのヒット数で選択数を判断
							String choiceSele = "input.selq";
							String nextSele = "img[src='../images/bt_next.png']";
							String endSele = "img[src='../images/bt_end.png']";
							for (int k = 1; k <= 11; k++) {
								if (isExistEle(driver, footBannerSele)) {
									checkOverlay(driver, footBannerSele);
								}
								if (!isExistEle(driver, dialogCloseSeleNone)
										&& !isExistEle(driver, "section.timemodal.modal1>div#popmodal img[src='../images/close.png']")
										&& isExistEle(driver, dialogCloseSele)) {
									checkOverlay(driver, dialogCloseSele);
								}
								if (isExistEle(driver, choiceSele)) {
									int choiceies = getSelectorSize(driver, choiceSele);
									int choiceNum = 0;
									switch (k) {
										case 1:
										case 2:
											// 1問目は1：はい
											// 2問目は1：男
											break;
										case 3:
											// 3問目は3：30代
											if (choiceies > 2) {// 一応選択可能な範囲かをチェック
												choiceNum = 2;
											}
											break;
										default:
											choiceNum = Utille.getIntRand(choiceies);
									}
									List<WebElement> eleList2 = driver.findElements(By.cssSelector(choiceSele));
									if (isExistEle(eleList2, choiceNum)) {
										// 選択
										clickSleepSelector(eleList2.get(choiceNum), 2500);
										if (isExistEle(driver, nextSele)) {
											// 次へ
											clickSleepSelector(driver, nextSele, 4000);
										}
									}
								}
							}
							if (isExistEle(driver, endSele)) {
								// 終了
								clickSleepSelector(driver, endSele, 2500);
							}
						}
					}
					driver.close();
					// 元のウインドウIDにスイッチ
					driver.switchTo().window(wid);
				}
			}

			//			clickSleepSelector(driver, selector, 5000); // 遷移
			//			changeCloseWindow(driver);
			//			while (true) {
			//				selector = "div.entry";
			//				List<WebElement> eleList = driver.findElements(By.cssSelector(selector));
			//				int size1 = eleList.size();
			//				WebElement wEle = null;
			//				for (int i = 0; i < size1; i++) {
			//					if (isExistEle(eleList, i)) {
			//						String sumiSelector = "img[src='/images/icons/sumi.png']";
			//						if (isExistEle(eleList.get(i), sumiSelector)) {
			//							continue;
			//						}
			//						selector = "div.entry";
			//						wEle = eleList.get(i);
			//						break;
			//
			//					}
			//				}
			//				if (wEle == null) {
			//					break;
			//				}
			//				selector = "div[class='thumbnail'] h3.entrytitle>a"; // クラスを完全一致にするのは済の場合クラスが追加されるため
			//				if (isExistEle(wEle, selector)) {
			//					clickSleepSelector(wEle, selector, 4000); // 遷移
			//
			//				} else {
			//					logg.warn(mName + "]all済み");
			//					break;
			//				}
			//			}
		}
	}
}
